package com.example.grouping.post;

import com.google.gson.annotations.SerializedName;

public class PostUser {

    private String id;
    private String name;
    private String image;
    private int hobby_id;
    private int attractive;

    public PostUser(String id, String name, String image, int hobby_id, int attractive) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.hobby_id = hobby_id;
        this.attractive = attractive;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public int getHobby_id() {
        return hobby_id;
    }
    public int getAttractive() {
        return attractive;
    }
}
