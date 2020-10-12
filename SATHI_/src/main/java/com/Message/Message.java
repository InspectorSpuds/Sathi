package com.Message;

import com.slack.api.model.view.View;

public class Message {
    private String messageID;
    private String postDate;
    public View view;

    public void Message(String postDate,String messageID, View view) {
        this.messageID = messageID;
        this.view = view;
    }
}
