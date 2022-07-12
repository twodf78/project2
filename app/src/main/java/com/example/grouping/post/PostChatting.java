package com.example.grouping.post;

public class PostChatting {

    private String suggest_id;
    private String user_id;
    private String message;

    public PostChatting(String suggest_id,String user_id,String message) {
        this.suggest_id = suggest_id;
        this.user_id = user_id;
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }
    public String getSuggest_id() {
        return suggest_id;
    }
    public String getMessage() {
        return message;
    }

}