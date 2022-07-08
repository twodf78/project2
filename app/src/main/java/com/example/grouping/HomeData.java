package com.example.grouping;

import android.widget.Button;
import android.widget.EditText;

public class HomeData {

    private String hometitle;

    public HomeData(String hometitle) {
        this.hometitle = hometitle;
    }

    public String getHometitle() {
        return hometitle;
    }

    public void setHometitle(String hometitle) {
        this.hometitle = hometitle;
    }
}
