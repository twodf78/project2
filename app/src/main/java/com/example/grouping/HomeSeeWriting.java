package com.example.grouping;

import static com.example.grouping.MainActivity.current_user_id;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.grouping.post.PostSuggest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

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
    TextView oneSuggestTitle;
    TextView oneSuggestStartTime, oneSuggestFinishTime, oneSuggestCurrent, oneSuggestCapacity;

    ImageView oneUserImage;
    TextView oneUserAttract;
    TextView oneUserTitle;
    TextView oneUserName;
    ImageView oneUserAttractImage;

    TextView oneSuggestJoinBtn;
    JSONArray arrSuggest;
    JSONArray arrUser;
    JSONArray arrTitle;

    private static final String URL = "http://192.249.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;
    ArrayList<Integer> suggest_id_of_party;
    String currentSuggestId;
    Boolean ableToJoin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        Intent getIntent = getIntent();
        currentSuggestId = getIntent.getStringExtra("suggest_id");
        String createdUserId = getIntent.getStringExtra("user_id");

        viewInit();

        oneUserAttractImage=findViewById(R.id.oneUserAttractImage);

        //참여하기 누르면

        requestSuggest(currentSuggestId);
        requestUser(createdUserId);

        oneSuggestJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ableToJoin){
                    //일단 party를 가져오고 어떤 suggest id 에 넣을 지 확인
                    requestGetParty();
                }else{
                    Toast.makeText(getApplicationContext(),"수용인원이 꽉 찼습니다." , Toast.LENGTH_SHORT );
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }



    private void requestSuggest(String suggest_id) {
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
                            requestTitle(arrUser.getJSONObject(0).getString("attractive"));

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
    private void requestTitle(String attractive) {
        Call<ResponseBody> call_get = service.getTitle(attractive);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            arrTitle = new JSONArray(result);
                            oneUserTitle.setText(arrTitle.getJSONObject(0).getString("title_name"));
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
    private void requestGetParty() {

        Call<ResponseBody> call_get = service.getParty(current_user_id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        int index = 0;
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray arr = new JSONArray(result);
                            suggest_id_of_party=new ArrayList<Integer>();
                            Boolean alreadyAdded = false;
                            for(int i= 1;i<=5;i++){
                                String temp = arr.getJSONObject(0).getString("suggest_" + String.valueOf(i)+"_id");
                                if(Integer.parseInt(temp) >0){
                                    suggest_id_of_party.add(Integer.parseInt(temp));
                                }
                                else if(Integer.parseInt(temp) == 0 && !alreadyAdded){
                                    suggest_id_of_party.add(Integer.parseInt(currentSuggestId));
                                    alreadyAdded = true;
                                    index = i-1;
                                }
                                else{
                                    suggest_id_of_party.add(0);
                                }
                            }
                            if(!alreadyAdded){
                              Toast.makeText(getApplicationContext(), "최대 5개까지의 party 밖에 생성을 하지 못 합니다." , Toast.LENGTH_SHORT);
                            } else{
                                //무조건 추가 가능 한 부분, party 도 가능하고, 수용인원도 가능하고
                                requestPutParty();
                                requestPutSuggest(String.valueOf(suggest_id_of_party.get(index)));
                            }
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
    private void requestPutParty() {
        PostParty post =new PostParty(current_user_id,suggest_id_of_party.get(0),suggest_id_of_party.get(1),suggest_id_of_party.get(2),suggest_id_of_party.get(3),suggest_id_of_party.get(4) );
        Call<PostParty> call_post = service.putParty(current_user_id, post);
        call_post.enqueue(new Callback<PostParty>() {
            @Override
            public void onResponse(Call<PostParty> call, Response<PostParty> response) {
                if (response.isSuccessful()) {
                    String result = response.toString();
                    Log.v(TAG, "result = " + result);
                    Toast.makeText(getApplicationContext(), "party 추가 완료" , Toast.LENGTH_SHORT);
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
    private void requestPutSuggest(String suggest_id) throws JSONException {
        JSONObject temp = arrSuggest.getJSONObject(0);
        PostSuggest post =new PostSuggest(temp.getString("startTime"),temp.getString("endTime"), temp.getString("created_by"),temp.getString("title"),temp.getString("content"),
                temp.getString("location"),Integer.parseInt(temp.getString("capacity")),Integer.parseInt(temp.getString("current")),Integer.parseInt(temp.getString("hobby_id")));
        Call<PostSuggest> call_post = service.putSuggest(suggest_id, post);
        call_post.enqueue(new Callback<PostSuggest>() {
            @Override
            public void onResponse(Call<PostSuggest> call, Response<PostSuggest> response) {
                if (response.isSuccessful()) {
                    String result = response.toString();
                    Log.v(TAG, "result = " + result);
                    Toast.makeText(getApplicationContext(), "suggest 변경 완료" , Toast.LENGTH_SHORT);
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PostSuggest> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void viewInit() {
        setContentView(R.layout.activity_home_see_writing);

        oneUserImage = findViewById(R.id.oneUserImage);
        oneUserTitle = findViewById(R.id.oneUserTitle);
        oneUserAttract = findViewById(R.id.oneUserAttract);
        oneUserName = findViewById(R.id.oneUserName);

        oneSuggestStartTime = findViewById(R.id.oneSuggestStartTime);
        oneSuggestFinishTime = findViewById(R.id.oneSuggestFinishTime);
        oneSuggestCurrent = findViewById(R.id.oneSuggestPeopleNow);
        oneSuggestCapacity = findViewById(R.id.oneSuggestPeopleAll);
        oneSuggestArea = findViewById(R.id.oneSuggestArea);
        oneSuggestLocation = findViewById(R.id.oneSuggestLocation);
        oneSuggestContent = findViewById(R.id.oneSuggestContent);
        oneSuggestTitle = findViewById(R.id.oneSuggestTitle);


        oneSuggestJoinBtn = findViewById(R.id.oneSuggestJoinBtn);
    }
    private void setSuggestView() {
        if (arrSuggest != null) {
            try {
                oneSuggestArea.setText(arrSuggest.getJSONObject(0).getString("hobby_id"));
                oneSuggestLocation.setText(arrSuggest.getJSONObject(0).getString("location"));
                oneSuggestTitle.setText(arrSuggest.getJSONObject(0).getString("title"));
                oneSuggestContent.setText(arrSuggest.getJSONObject(0).getString("content"));
                oneSuggestCurrent.setText(arrSuggest.getJSONObject(0).getString("current"));
                oneSuggestCapacity.setText(arrSuggest.getJSONObject(0).getString("capacity"));
                oneSuggestStartTime.setText(arrSuggest.getJSONObject(0).getString("startTime").replace("T"," ").replace(".000Z",""));
                oneSuggestFinishTime.setText(arrSuggest.getJSONObject(0).getString("endTime").replace("T"," ").replace(".000Z",""));

                if(arrSuggest.getJSONObject(0).getString("capacity").equals(arrSuggest.getJSONObject(0).getString("current"))){
                    ableToJoin = false;
                }else{
                    ableToJoin = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUserView(){

        if(arrUser!=null){
            try {
                //                oneUserImage.setText(arrSuggest.getJSONObject(0).getString("image"));
                oneUserAttract.setText(arrUser.getJSONObject(0).getString("attractive"));
                oneUserName.setText(arrUser.getJSONObject(0).getString("name"));

                String attracttext=oneUserAttract.getText().toString();
                int attractNum = Integer.parseInt(attracttext);
                oneUserAttractImage.post(new Runnable() {
                    @Override
                    public void run() {
                        Resources resources = getResources();
                        if (attractNum >= 60 && attractNum < 80){
                            oneUserAttractImage.setImageResource(R.drawable.heartbar4);
                        }
                        else if(attractNum >= 80){
                            oneUserAttractImage.setImageResource(R.drawable.heartbar5);
                        }
                        else if (attractNum<=40 && attractNum >20){
                            oneUserAttractImage.setImageResource(R.drawable.heartbar2);
                        }
                        else if (attractNum<=20){
                            oneUserAttractImage.setImageResource(R.drawable.heartbar1);
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}