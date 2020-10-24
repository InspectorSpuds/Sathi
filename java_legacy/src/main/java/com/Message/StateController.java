package com.Message;

import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.ViewState;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.Message.MessageCollection.*;

/*
    @Author: Ishan Parikh, Bhagyashree Aras, Alex Wallen
    Purpose: Does the main heavy lifting for validating and then returning
    signals via HTTP POST requests with the slack web client.
    Can:
        -Validate slash commands
    Some helpful dev commands that could be used in the program(self-reference):
        -req.getPayload().getChannelID(): get the channel ID from a slash command
        -ctx.getChannelID(): get the channel ID from a slash command (preferred method)

 */
public class StateController {
    private boolean isActive = false;
    private static AnnouncementManager manager = new AnnouncementManager();

    //Temp storage for storing information from MODALS for conversion to a CustomMessage
    //<String userID, tentative message>
    private static HashMap<String, modalTemp_ANNOUNCE> modalTempStorage = new HashMap<String, modalTemp_ANNOUNCE>();
    public void start() throws Exception {
        if(!isActive){ //will only start once, you don't want multitple of the same server instance running
            BasicConfigurator.configure(); //applies basic configuration to log4j network logging software
            var app = new App();
            appCommandAssigns(app);
            appCatchCases(app);
            var server = new SlackAppServer(app);
            isActive = true;
            server.start(); // http://localhost:3000/slack/events but if tunneled through ngrok: https://(your-ngrok-link)/slack/events
        }
    }

    /*
        appCommandAssigns()
     */
    static void appCommandAssigns(App app) {
        app.command("/schedule", (req, ctx) -> {
            ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(r -> r
                    .triggerId(ctx.getTriggerId())
                    .view(SCHEDULER_VIEW));
            if (viewsOpenRes.isOk())
                return ctx.ack();
            else System.out.println("error");
            return ctx.ack();
        });

        //Used to List currently scheduled announcements
        app.command("/current", (req, ctx) -> {
            return ctx.ack();
        });

        app.command("/delete", (req, ctx) -> {
            return null;
        });
    }

    /*
        appCatchCases(App app)

        Purpose: Used to catch user input from ui inputs in vanilla messages
        or modal styled forms
        Process: Essentially, just updates HTTPS polling handlers for the running
        server
     */
    static void appCatchCases(App app){
        //sample of catch repetition:
        app.blockAction("Event-Type",(req, ctx)->{
            //get recently altered/inputted value =>
            var value = req.getPayload().getActions().get(0).getSelectedOption().getValue();
            //if the temporary storage object doesn't exist:
            if(!modalTempStorage.containsKey(ctx.getRequestUserId())) modalTempStorage.put(ctx.getRequestUserId(), new modalTemp_ANNOUNCE());
            modalTempStorage.get(ctx.getRequestUserId()).eventType = value; //get recently caught value

            return ctx.ack();
        });

        app.blockAction("Hour",(req, ctx)->{
            var value = req.getPayload().getActions().get(0).getSelectedOption().getValue();
            if(!modalTempStorage.containsKey(ctx.getRequestUserId())) modalTempStorage.put(ctx.getRequestUserId(), new modalTemp_ANNOUNCE());
            modalTempStorage.get(ctx.getRequestUserId()).eventHour = value;

            return ctx.ack();
        });
        app.blockAction("Minute",(req, ctx)->{
            var value = req.getPayload().getActions().get(0).getSelectedOption().getValue();
            if(!modalTempStorage.containsKey(ctx.getRequestUserId())) modalTempStorage.put(ctx.getRequestUserId(), new modalTemp_ANNOUNCE());
            modalTempStorage.get(ctx.getRequestUserId()).eventMinute = value;

            return ctx.ack();
        });
        app.blockAction("datepicker",(req, ctx)->{
            var value = req.getPayload().getActions().get(0).getSelectedDate();
            if(!modalTempStorage.containsKey(ctx.getRequestUserId())) modalTempStorage.put(ctx.getRequestUserId(), new modalTemp_ANNOUNCE());
            modalTempStorage.get(ctx.getRequestUserId()).eventDate = value;

            return ctx.ack();
        });
        app.viewSubmission("Scheduler", (req, ctx) -> {
            Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
            var agenda = stateValues.get("Agenda_Block").get("Agenda").getValue();
            if(agenda.length() == 0) {
                return ctx.ack(r->r.responseAction("Please don't spam empty announcements to people! Thank you!"));
            } else {
                if(modalTempStorage.get(ctx.getRequestUserId()).eventDate==null) {
                    Date myDate = new Date();
                    SimpleDateFormat datePickerFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timePickerFormat = new SimpleDateFormat("HH:mm");
                    modalTempStorage.get(ctx.getRequestUserId()).eventDate = datePickerFormat.format(myDate);
                }
                modalTempStorage.get(ctx.getRequestUserId()).netMessage = agenda;
                manager.scheduleMessage(modalTempStorage.remove(ctx.getRequestUserId()), ctx.getRequestUserId());
                return ctx.ack();
            }
        });
    }

    public static void echoAnnouncement(String id, String text) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("Sathi");
        try {
            //Call the chat.postMessage method using HTTPS post command
            var result = client.chatPostMessage(r -> r
                    .token(System.getenv("SLACK_BOT_TOKEN")).channel(id).text(text)
            );
            // Print result, which includes information about the message (like TS)
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }
}