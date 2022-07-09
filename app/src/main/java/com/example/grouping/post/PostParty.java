package com.example.grouping.post;

public class PostParty {

    private int user_id;
    private int suggest_id;
    private String chatting;

    public PostParty(int user_id, int suggest_id, String chatting) {
        this.user_id = user_id;
        this.suggest_id = suggest_id;
        this.chatting =chatting;
    }

    public int getUser_id() {
        return user_id;
    }
    public int getSuggest_id() {
        return suggest_id;
    }
    public String getChatting() {
        return chatting;
    }

}