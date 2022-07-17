package com.example.register;

public class Message {
    private String messageName;
    private String messagePrice;
    private int messageIcon;

    public Message() {
    }

    public Message(String messageName, String messagePrice, int messageIcon) {
        this.messageName = messageName;
        this.messagePrice = messagePrice;
        this.messageIcon = messageIcon;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessagePrice() {
        return messagePrice;
    }

    public void setMessagePrice(String messagePrice) {
        this.messagePrice = messagePrice;
    }

    public int getMessageIcon() {
        return messageIcon;
    }

    public void setMessageIcon(int messageIcon) {
        this.messageIcon = messageIcon;
    }
}
