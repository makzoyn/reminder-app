package com.example.myreminder;

import java.util.UUID;

public class Task {
    private String id;
    private String text;
    private String date;
    private String time;

    //private Date alarmDate;

    public Task(String text, String date, String time)
    {
        id = UUID.randomUUID().toString();
        this.text = text;
        //todo сделать разые поля под время, дату
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
