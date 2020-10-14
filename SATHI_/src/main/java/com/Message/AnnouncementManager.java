package com.Message;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
/*
    @Author Ishan Parikh
    Purpose: This is a message manager for the
*/

public class AnnouncementManager {
    //stores Message ID's as well as the associated custom message
    private HashMap<String, CustomMessage> announcements= new HashMap<String, CustomMessage>(); //stored as: STRING id, Message message

    //stores user ID and associated scheduled message ID's, it keeps track of the IDS and allows for proper management
    //stored as: String userID, HashSet<String>
    private HashMap<String, HashSet<String>> userIDList= new HashMap<>();


    public void scheduleMessage(modalTemp_ANNOUNCE a, String userID) throws IOException, SlackApiException {
        //adds the user id if it isn't already added
        if(!userIDList.containsKey(userID)) userIDList.put(userID, new HashSet<String>());
        CustomMessage nu = new CustomMessage(a.eventDate,a.netMessage,a.eventType);
        var client = Slack.getInstance().methods();
        client.chatScheduleMessage(r -> r
                // The token you used to initialize your app
                .token(System.getenv("SLACK_BOT_TOKEN"))
                .channel(userID)
                .text(nu.toString())
                 //Time to post message, in Unix Epoch timestamp format
                //(int)tomorrow.toInstant().getEpochSecond()
                .postAt((int)(convertToEpochTime(deepCopy(nu.postDate),Integer.parseInt(a.eventHour), Integer.parseInt(a.eventMinute))/1000))

        );
    }

    public void cancelMessage() {


    }

    //UPDATE THIS CODE ON JAN 19 2038, EPOCH TIME WILL FAIL ON THAT DATE
    private long convertToEpochTime(String date, int hours, int minutes) {
        Date myDate = new Date();
        SimpleDateFormat datePickerFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            myDate = datePickerFormat.parse(date+" "+hours+":"+minutes);
        } catch(Exception e) {

            return 404;
        }
        System.out.println("REEEEEEEEEEEEEEEEEEEEEEE    "+myDate.getTime()/60);
        long epoch = myDate.getTime();
        return epoch;
    }

    private String deepCopy(String original) {
        String nu = "";
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(original);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            nu = (String)(in.readObject());
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return nu;
    }
    private void schedule(){


    }
    public AnnouncementManager() {

    }

    final static class Messenger {
        /*
        echoAnnouncement(String id, String text)
        Purpose: Just posts a public text message via vanilla Slack HTTP
        POST relay, no use of slash commands. Use it for debugging
        purposes, no need to use it in current code paradigm
        Requirements:
            User, group or channel id (refer to commands in StateController documentation)
            & a String body of text to post, can be anything, keep it appropriate please
     */
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
}
