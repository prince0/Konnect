package com.example.rohan.helloworld;

/**
 * Created by rohan on 2/3/2018.
 */

public class userMessage
{
    private String userName;
    private String chatMessage;
    private String time;

    public userMessage(String userName, String chatMessage, String time)
    {
        this.userName = userName;
        this.chatMessage = chatMessage;
        this.time = time;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getChatMessage()
    {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage)
    {
        this.chatMessage = chatMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
