package com.example.restservice;

public class Events {

    private final String content;
    private final int year;

    public Events(String content, int year) {
        this.content = content;
        this.year = year;
    }

    public String getContent() {

        return content;
    }

    public int getYear() {
        return year;
    }
}