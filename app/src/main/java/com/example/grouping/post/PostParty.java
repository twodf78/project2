package com.example.grouping.post;

import java.math.BigInteger;

public class PostParty {

    private String user_id;
    private int suggest_1_id;
    private int suggest_2_id;
    private int suggest_3_id;
    private int suggest_4_id;
    private int suggest_5_id;

    public PostParty(String user_id, int suggest_1_id,int suggest_2_id,int suggest_3_id,int suggest_4_id,int suggest_5_id) {
        this.user_id = user_id;
        this.suggest_1_id = suggest_1_id;
        this.suggest_2_id = suggest_2_id;
        this.suggest_3_id = suggest_3_id;
        this.suggest_4_id = suggest_4_id;
        this.suggest_5_id = suggest_5_id;
    }

    public String getUser_id() {
        return user_id;
    }
    public int getSuggest_1_id() {
        return suggest_1_id;
    }
    public int getSuggest_2_id() {
        return suggest_2_id;
    }
    public int getSuggest_3_id() {
        return suggest_3_id;
    }
    public int getSuggest_4_id() {
        return suggest_4_id;
    }
    public int getSuggest_5_id() {
        return suggest_5_id;
    }

}