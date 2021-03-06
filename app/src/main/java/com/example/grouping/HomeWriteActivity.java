package com.example.grouping;

import static com.example.grouping.MainActivity.current_user_id;

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

    private static final String URL = "http://192.249.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;

    EditText content;
    EditText title;
    Spinner location;
    Spinner hobby;
    Spinner people;
    TextView startDate, startTime, endDate, endTime;
    String fixedStartTime, fixedEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_write);

        title = findViewById(R.id.homeedittitle);
        content = findViewById(R.id.homeeditcontent);
        hobby = findViewById(R.id.homeHobbyspinner);
        location = findViewById(R.id.homeLocationSpinner);
        people =findViewById(R.id.homePeopleSpinner);
        startDate = findViewById(R.id.homeStartDateTextView);
        startTime = findViewById(R.id.homeStartTimeTextView);
        endDate = findViewById(R.id.homeFinishDateTextView);
        endTime =findViewById(R.id.homeFinishTimeTextView);

        hobby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Toast.makeText(v.getContext(),"????????? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
                else if(content.getText().toString().length()==0){
                    Toast.makeText(v.getContext(), "????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "????????? ???????????? ??????????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    //?????? ?????? ?????????
                    setTime();
                    //????????? ?????????
                    postSuggest();
                    Intent intent = new Intent(getApplicationContext(), MypageMyratingActivity.class);
                    startActivity(intent);

                }

            }
        });
    }

    public void onBtnClicked3(View v) {
        HomeDatePickerFragment newFragment = new HomeDatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }

    public void onBtnClicked4(View v) {
        HomeDatePickerFragment2 newFragment = new HomeDatePickerFragment2();
        newFragment.show(getFragmentManager(),"datePicker");
    }

    public void onBtnClicked(View v){
        HomeTimePickerFragment newAlertFragment = new HomeTimePickerFragment();
        newAlertFragment.show(getFragmentManager(), "TimePicker");
    }

    public void onBtnClicked2(View v){
        HomeTimePickerFragment2 newAlertFragment2 = new HomeTimePickerFragment2();
        newAlertFragment2.show(getFragmentManager(), "TimePicker");
    }

    private void postSuggest() {
        PostSuggest post = new PostSuggest(fixedStartTime, fixedEndTime, current_user_id, title.getText().toString(), content.getText().toString()
                ,location.getSelectedItem().toString(), Integer.parseInt(people.getSelectedItem().toString()),1, 5);
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
                String user_id = postResponse.getCreated_by();
                Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<PostSuggest> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setTime(){
        fixedStartTime = startDate.getText().toString() + "" + startTime.getText().toString()+"00";
        fixedEndTime = endDate.getText().toString() + "" + endTime.getText().toString()+"00";
        fixedStartTime = fixedStartTime.replace("??? ", "-");
        fixedStartTime = fixedStartTime.replace("??? ", "-");
        fixedStartTime = fixedStartTime.replace("??? ", " ");
        fixedStartTime = fixedStartTime.replace("\n", "");
        fixedStartTime = fixedStartTime.replace("??? ", ":");
        fixedStartTime = fixedStartTime.replace("???", ":");
        fixedStartTime = fixedStartTime.replace("\n", "");
        fixedEndTime = fixedEndTime.replace("??? ", "-");
        fixedEndTime = fixedEndTime.replace("??? ", "-");
        fixedEndTime = fixedEndTime.replace("??? ", " ");
        fixedEndTime = fixedEndTime.replace("\n ", "");
        fixedEndTime = fixedEndTime.replace("??? ", ":");
        fixedEndTime = fixedEndTime.replace("???", ":");
        fixedEndTime = fixedEndTime.replace("\n", "");

    }

}