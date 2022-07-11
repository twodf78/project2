package com.example.grouping.post;

import android.icu.util.ULocale;

public class PostSuggest {

    private String startTime;
    private String endTime;
    private String created_by;
    private String title;
    private String content;
    private String location;
    private int capacity;
    private int current;
    private int hobby_id;

    public PostSuggest(String startTime, String endTime, String created_by,  String title, String content,String location,int capacity,int current,int hobby_id) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.created_by =created_by;
        this.title =title;
        this.content =content;
        this.location = location;
        this.capacity = capacity;
        this.current = current;
        this.hobby_id =hobby_id;
    }

    public String getStartTime() {
        return startTime;
    }
    public String getEndTIME() {
        return endTime;
    }
    public String getCreated_by() {
        return created_by;
    }
    public String getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }
    public String getLocation() {
        return location;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getCurrent() {
        return current;
    }
    public int getHobby_id() {
        return hobby_id;
    }

}