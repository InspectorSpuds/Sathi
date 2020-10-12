package com;

import com.Message.AnnouncementManager;
import com.Message.Message;
import com.Message.UserManager;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.ViewState;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.Message.MessageCollection.SCHEDULER_VIEW;

public class StateController {
    private boolean isActive = false;
    private static AnnouncementManager messages;
    private static UserManager users;
    public void start() throws Exception {
        if(!isActive){ //will only start once, you don't want multitple of the same server instance running
            BasicConfigurator.configure(); //log4j is what catches server messages, apparently the vanilla tutorial program doesn't have it set up
            var app = new App(); // `new App()` does the same
            appCommandAssigns(app);
            appCatchCommands(app);
            SlackAppServer server = new SlackAppServer(app);
            isActive = true;
            server.start(); // http://localhost:3000/slack/events
        }
    }



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
            String token = System.getenv("SLACK_BOT_TOKEN");
            echoAnnouncement(ctx.getChannelId(), "");
            try {
                ctx.respond("");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ctx.ack();
        });

        app.command("/delete", (req, ctx) -> {
            return null;
        });

        app.blockAction("Hour",(req, ctx)->{
            String value = req.getPayload().getActions().get(0).getValue();
            ctx.respond(""+value);
            return ctx.ack();
        });



    }

    static void appCatchCommands(App app){
        app.viewSubmission("Scheduler", (req, ctx) -> {
            return ctx.ack();
        });

        app.viewClosed("Scheduler", (req, ctx) -> {
            echoAnnouncement("C01BJFMLX40", "T_T");
            return ctx.ack();
        });

        app.viewSubmission("meeting-arrangement", (req, ctx) -> {
            String privateMetadata = req.getPayload().getView().getPrivateMetadata();
            Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
            String agenda = stateValues.get("agenda-block").get("agenda-action").getValue();
            Map<String, String> errors = new HashMap<>();
            if (agenda.length() <= 10) {
                errors.put("agenda-block", "Agenda needs to be longer than 10 characters.");
            }
            if (!errors.isEmpty()) {
                return ctx.ack(r -> r.responseAction("errors").errors(errors));
            } else {
                // TODO: may store the stateValues and privateMetadata
                // Responding with an empty body means closing the modal now.
                // If your app has next steps, respond with other response_action and a modal view.
                return ctx.ack();
            }
        });
    }












    //the String ID can be either a
    public static void echoAnnouncement(String id, String text) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("my-awesome-slack-app");
        try {
            //Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                    .token(System.getenv("SLACK_BOT_TOKEN")).channel(id).text(text)
            );
            // Print result, which includes information about the message (like TS)
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }

    static void scheduleAnnouncement(String[] recipIDs, Message message) {
        var client = Slack.getInstance().methods();
    }


    static void echoCurrAnnouncements() {


    }
}
