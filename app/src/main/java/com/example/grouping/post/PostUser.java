package com.example.grouping.post;

import com.google.gson.annotations.SerializedName;

public class PostUser {

    private String name;
    private String image;
    private int hobby_id;
    private String title;
    private int friends;
    private int attractive;

    public PostUser(String name, String image, int hobby_id, String title,
                    int friends, int attractive) {
        this.name = name;
        this.image = image;
        this.hobby_id = hobby_id;
        this.title = title;
        this.friends = friends;
        this.attractive = attractive;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public int getHobby_id() {
        return hobby_id;
    }
    public int getFriends() {
        return friends;
    }
    public int getAttractive() {
        return attractive;
    }


}
