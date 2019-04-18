package com.example.fyp.Adapter;

public class MessageAdapterList {
    private String msg, pic, date, full_name, username;

    public MessageAdapterList(String msg, String full_name, String pic, String date, String username) {
        this.msg = msg;
        this.pic = pic;
        this.date = date;
        this.full_name = full_name;
        this.username = username;
    }

    public String getMsg(){
        return msg;
    }
    public String getImg(){
        return pic;
    }
    public String getDate(){
        return date;
    }
    public String getUsername() { return username;}
    public String getFullName(){
        return full_name;
    }
}
