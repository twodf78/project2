package com.example.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MypageSelectHobby extends AppCompatActivity implements View.OnClickListener {

    private View header;
    private Button mypageHobbyBtn;

    //버튼에 대한 배열 선언
    private Button[] mButton = new Button[11];

    //각 버튼의 text를 넣어놓을 list
    private ArrayList<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_select_hobby);

        mDataList=new ArrayList<String>();

        mButton[0]=(Button) findViewById(R.id.mypageSelectbtn1);
        mButton[1]=(Button) findViewById(R.id.mypageSelectbtn2);
        mButton[2]=(Button) findViewById(R.id.mypageSelectbtn3);
        mButton[3]=(Button) findViewById(R.id.mypageSelectbtn4);
        mButton[4]=(Button) findViewById(R.id.mypageSelectbtn5);
        mButton[5]=(Button) findViewById(R.id.mypageSelectbtn6);
        mButton[6]=(Button) findViewById(R.id.mypageSelectbtn7);
        mButton[7]=(Button) findViewById(R.id.mypageSelectbtn8);
        mButton[8]=(Button) findViewById(R.id.mypageSelectbtn9);
        mButton[9]=(Button) findViewById(R.id.mypageSelectbtn10);
        mButton[10]=(Button) findViewById(R.id.mypageSelectbtn11);

        //각 버튼들에 대한 클릭리스너 등록 및 각 버튼이 클릭되었을 때 출력될 메시지 생성(리스트)
        for(int i=0; i<11; i++){
            mButton[i].setTag(i);
            mButton[i].setOnClickListener(this);
            mDataList.add(mButton[i].getText().toString());
        }


    }

    @Override
    public void onClick(View v) {
        Button newButton = (Button) v;

        for(Button tempButton : mButton){
            if(tempButton==newButton){
                int position=(Integer)v.getTag();
                Toast.makeText(this, "관심사가 성공적으로 반영되었습니다.\n마이페이지를 확인해주세요.", Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v=inflater.inflate(R.layout.fragment_mypage, null);
//                header=getLayoutInflater().inflate(R.layout.fragment_mypage, null, false);
                mypageHobbyBtn=(Button)v.findViewById(R.id.myPageHobby);

                mypageHobbyBtn.setText(mDataList.get(position));

                Intent intent = new Intent(MypageSelectHobby.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}