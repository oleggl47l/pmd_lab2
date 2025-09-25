package com.example.polytech_lab2.model;

import java.util.Date;

public class TodoItem {
    private final String title;
    private boolean isDone;
    private Date reminder;

    public TodoItem(String title) {
        this.title = title;
        this.isDone = false;
        this.reminder = null;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }
}
