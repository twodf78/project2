package com.example.grouping;

public class ChattingData {
    //내용
    private String content;
    //이름
    private String name;
    //뷰타임(왼쪽, 오른쪽, 가운데)
    private int viewType;
    //사진은 타입 어떤걸로? ImageView?


    public ChattingData(String content, String name, int viewType) {
        this.content = content;
        this.name = name;
        this.viewType = viewType;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public int getViewType() {
        return viewType;
    }
}
