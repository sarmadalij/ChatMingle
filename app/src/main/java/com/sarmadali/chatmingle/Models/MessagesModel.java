package com.sarmadali.chatmingle.Models;

public class MessagesModel {

    String messageUserId, messageText, msgId;
    Long timeStamp;

    public MessagesModel(String messageUserId, String messageText, Long timeStamp) {
        this.messageUserId = messageUserId;
        this.messageText = messageText;
        this.timeStamp = timeStamp;
    }

    public MessagesModel(String messageUserId, String messageText) {
        this.messageUserId = messageUserId;
        this.messageText = messageText;
    }

    //empty constructor
    public MessagesModel() {
    }

    //setter and getter

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
