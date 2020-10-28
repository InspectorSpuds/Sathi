package com.Message;

/*
    @Author Ishan Parikh
    Purpose: This is a class for making and sending custom messages

*/
public class CustomMessage {
    public String eventType;
    public String messageID;
    public String postDate;
    public String messageBody;

    public  CustomMessage(String postDate, String message, String eventType) {
        this.messageBody  = message;
        this.postDate = postDate;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Upcoming "+eventType+"\n"+
               "On " + postDate + "\n" +
               "What's happening: "+ "\n" +
                messageBody;
    }
}
