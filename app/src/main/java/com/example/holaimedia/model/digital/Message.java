package com.example.holaimedia.model.digital;

public class Message {
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Message() {
    }

    public Message(String userEmail, String message, String dateTime) {
        this.userEmail = userEmail;
        this.message = message;
        this.dateTime = dateTime;
    }

    private String message;
    private String dateTime;
}
