package com.example.grouping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grouping.post.PostParty;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class HomeSeeWriting extends AppCompatActivity {

    TextView oneSuggestArea;
    TextView oneSuggestLocation;
    TextView oneSuggestContent;
    TextView oneSuggestName;

    ImageView oneUserImage;
    TextView oneUserAttract;
    TextView oneUserTitle;

    TextView oneSuggestJoinBtn;
    JSONArray arrSuggest;
    JSONArray arrUser;

    private static final String URL = "http://172.10.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getIntent = getIntent();
        String currentSuggestId = getIntent.getStringExtra("suggest_id");
        String createdUserId = getIntent.getStringExtra("user_id");

        viewInit();

        oneSuggestJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPostParty(createdUserId, currentSuggestId, "안녕안녕");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        requestSuggest(currentSuggestId);
        requestUser(createdUserId);



//        new AlertDialog.Builder(HomeSeeWriting.this)
//                .setView(linear)
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        EditText applycontent = (EditText) linear.findViewById(R.id.homeStudyApplyDialogEdittext);
//                        String value = applycontent.getText().toString(); //GET suggest에 어떤 거..? 간단 지원 내용이 DB에도 들어가야할 듯
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
    }

    private void requestPostParty(String createdUserId, String currentSuggestId, String comment) {
        PostParty post =new PostParty(Integer.parseInt(createdUserId), Integer.parseInt(currentSuggestId), comment);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        Call<PostParty> call_post = service.postParty(post);
        call_post.enqueue(new Callback<PostParty>() {
            @Override
            public void onResponse(Call<PostParty> call, Response<PostParty> response) {
                if (response.isSuccessful()) {
                    String result = response.toString();
                    Log.v(TAG, "result = " + result);
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostParty> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void requestSuggest(String suggest_id) {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        Call<ResponseBody> call_get = service.getSuggestById(suggest_id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            arrSuggest = new JSONArray(result);
                            setSuggestView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void requestUser(String user_id) {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        Call<ResponseBody> call_get = service.getUser(user_id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            arrUser = new JSONArray(result);
                            setUserView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewInit() {
        setContentView(R.layout.activity_home_see_writing);

        oneUserImage = findViewById(R.id.oneSuggestImage);
        oneUserTitle = findViewById(R.id.oneSuggestTitle);
        oneUserAttract = findViewById(R.id.oneSuggestAttract);

        oneSuggestArea = findViewById(R.id.oneSuggestArea);
        oneSuggestLocation = findViewById(R.id.oneSuggestLocation);
        oneSuggestContent = findViewById(R.id.oneSuggestContent);
        oneSuggestName = findViewById(R.id.oneSuggestName);

        oneSuggestJoinBtn = findViewById(R.id.oneSuggestJoinBtn);
    }
    private void setSuggestView() {
        if (arrSuggest != null) {
            try {
                oneSuggestArea.setText(arrSuggest.getJSONObject(0).getString("startTime"));
                oneSuggestLocation.setText(arrSuggest.getJSONObject(0).getString("endTIME"));
                oneSuggestName.setText(arrSuggest.getJSONObject(0).getString("created_by"));
                oneSuggestContent.setText(arrSuggest.getJSONObject(0).getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUserView(){

        if(arrUser!=null){
            try {
//                oneUserImage.setText(arrSuggest.getJSONObject(0).getString("startTime"));
                oneUserAttract.setText(arrUser.getJSONObject(0).getString("attractive"));
                oneUserTitle.setText(arrUser.getJSONObject(0).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}