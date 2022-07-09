package com.example.grouping.post;

public class PostSuggest {


    private int startTime;
    private int endTIME;
    private int created_by;
    private String content;
    private int hobby_id;

    public PostSuggest(int startTime, int endTIME, int created_by, String content, int hobby_id) {
        this.startTime = startTime;
        this.endTIME = endTIME;
        this.created_by =created_by;
        this.content =content;
        this.hobby_id =hobby_id;
    }

    public int getStartTime() {
        return startTime;
    }
    public int getEndTIME() {
        return endTIME;
    }
    public int getCreated_by() {
        return created_by;
    }
    public int getHobby_id() {
        return hobby_id;
    }
    public String getContent() {
        return content;
    }

}