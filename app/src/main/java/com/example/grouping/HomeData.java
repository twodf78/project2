package com.example.grouping;

import android.widget.Button;

public class HomeData {

    private Button homebtn1;
    private Button homebtn2;
    private Button homebtn3;
    private String hometitle;

    public HomeData(Button homebtn1, Button homebtn2, Button homebtn3, String hometitle) {
        this.homebtn1 = homebtn1;
        this.homebtn2 = homebtn2;
        this.homebtn3 = homebtn3;
        this.hometitle = hometitle;
    }

    public Button getHomebtn1() {
        return homebtn1;
    }

    public void setHomebtn1(Button homebtn1) {
        this.homebtn1 = homebtn1;
    }

    public Button getHomebtn2() {
        return homebtn2;
    }

    public void setHomebtn2(Button homebtn2) {
        this.homebtn2 = homebtn2;
    }

    public Button getHomebtn3() {
        return homebtn3;
    }

    public void setHomebtn3(Button homebtn3) {
        this.homebtn3 = homebtn3;
    }

    public String getHometitle() {
        return hometitle;
    }

    public void setHometitle(String hometitle) {
        this.hometitle = hometitle;
    }
}
