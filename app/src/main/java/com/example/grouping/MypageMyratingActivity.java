package com.example.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MypageMyratingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyPageRatingAdapter adapter;
    private static final String URL = "http://172.10.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;

    public static ArrayList<JSONObject> jsonRatingArray =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_myrating);


        Intent getIntent = getIntent();
        String user_id =getIntent.getStringExtra("user_id");

        recyclerView = (RecyclerView)findViewById(R.id.ratingRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter =new MyPageRatingAdapter( getApplicationContext());
        requestFriend(user_id);


    }
    //그냥 thread 돌리기
    private void populateTable(String user_id) {
        ProgressDialog mProgressDialog = ProgressDialog.show(getApplicationContext(),
                "Please wait",
                "Long operation starts...",
                true);
        new Thread() {
            @Override
            public void run() {
                try {
                    //user_id로 친구들 id 불러오기
                    requestFriend(user_id);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                        }
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();
    }

    private void requestFriend(String user_id) {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        Call<ResponseBody> call_get = service.getFriend(user_id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray friendIdArray = new JSONArray(result);
                            for (int i=1; i<=5;i++){
                                //array 자체의 길이는 1임.
                                String friend_id = friendIdArray.getJSONObject(0).getString("friend_"+String.valueOf(i) + "_id");
                                Log.v(TAG, "result = " + friend_id);
                                if(Integer.parseInt(friend_id)>0) {
                                    requestUser(friend_id);
                                }
                            }

                            recyclerView.setAdapter(adapter);
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
    //여기서 id 하나하나가 다 String
    private void requestUser(String id) {


        Call<ResponseBody> call_get = service.getUser(id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray arr = new JSONArray(result);
                            adapter.setArrayData(arr.getJSONObject(0));
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
}