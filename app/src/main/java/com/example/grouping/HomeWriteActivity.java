package com.example.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grouping.post.PostSuggest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeWriteActivity extends AppCompatActivity {

    private static final String URL = "http://172.10.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;

    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_write);

        final TextView textView = findViewById(R.id.homefieldspinnertext);
        EditText title = findViewById(R.id.homeedittitle);
        content = findViewById(R.id.homeeditcontent);
        Spinner spinner = findViewById(R.id.homefieldspinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button addBtn=findViewById(R.id.homeaddwritingbtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().toString().length()==0){
                    Toast.makeText(v.getContext(),"스터디 글 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(content.getText().toString().length()==0){
                    Toast.makeText(v.getContext(), "스터디 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "생성한 스터디가 성공적으로 업로딩 되었습니다.", Toast.LENGTH_SHORT).show();
                    createPost();
                    finish();

                }

            }
        });
    }
    private void createPost() {
        PostSuggest post = new PostSuggest(5, 7, 2, content.getText().toString(),5);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        Call<PostSuggest> call = service.postSuggest(post);
        call.enqueue(new Callback<PostSuggest>() {
            @Override
            public void onResponse(Call<PostSuggest> call, Response<PostSuggest> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }

                PostSuggest postResponse = response.body();

                String content = "";
                content += "Code : " + response.code() + "\n";
//                content += "Id: " + ((String) postResponse.getStartTime()) + "\n";
//                content += "User Id: " + postResponse.getEndTIME() + "\n";
//                content += "Title: " + postResponse.getCreated_by() + "\n";
//                content += "Text: " + postResponse.getHobby_id() + "\n";
//                content += "Text: " + postResponse.getContent() + "\n";

                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<PostSuggest> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}