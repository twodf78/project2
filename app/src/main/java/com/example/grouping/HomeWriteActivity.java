package com.example.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HomeWriteActivity extends AppCompatActivity {

    private Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_write);

        final TextView textView = findViewById(R.id.homefieldspinnertext);
        EditText title = findViewById(R.id.homeedittitle);
        EditText content = findViewById(R.id.homeeditcontent);
        Spinner spinner = findViewById(R.id.homefieldspinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        addbtn=findViewById(R.id.homeaddwritingbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().toString().length()==0){
                    Toast.makeText(v.getContext(),"스터디 글 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(content.getText().toString().length()==0){
                    Toast.makeText(v.getContext(), "스터디 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(title.getText().toString().length()!=0&&content.getText().toString().length()!=0){
                    Toast.makeText(v.getContext(), "생성한 스터디가 성공적으로 업로딩 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeWriteActivity.this, MainActivity.class);
//                    intent.putExtra("title", title.getText().toString());
                    //보낼 데이터가 있다면 여기서 보내면 됨.
                    //!!!!!!!!!!!!!!!!확인 suggest GET 제목, 지역, 분야
                    startActivity(intent);
                }

            }
        });





    }
}