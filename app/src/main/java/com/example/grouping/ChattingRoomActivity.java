package com.example.grouping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouping.post.PostChatting;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChattingRoomActivity extends AppCompatActivity {
    ImageButton backBtn;
    RecyclerView recyclerView;
    ChattingRoomAdapter adapter;
    String suggest_id;
    private static final String URL = "http://192.249.19.184:443/";
    private final String TAG = "request log";
    private final ArrayList<String> userList = new ArrayList<>();

    private Retrofit retrofit;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        Intent intent = getIntent();
        suggest_id = intent.getStringExtra("suggest_id");

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);


        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ChattingRoomAdapter(getApplicationContext());
        backBtn =findViewById(R.id.chattingRoomBackbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChattingRoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        requestChatting(suggest_id);

        for(int i = 0; i<userList.size();i++){
            populateTable(userList.get(i));
        }
    }
    private void populateTable(String user_id) {

        new Thread() {
            @Override
            public void run() {
                try {
                    requestName(user_id);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();
    }

    private void requestChatting(String suggest_id) {
        Call<List<PostChatting>> call_get = RetrofitClient.getAPIService().getChatting(suggest_id);
        try {
            List<PostChatting> result = call_get.clone().execute().body();
            for(int i = 0; i<result.size();i++){
                adapter.setMessageList(result.get(i).getMessage());
                userList.add(result.get(i).getUser_id());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void requestName(String user_id) {

        Call<ResponseBody> call_get = service.getUser(user_id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray userArr = new JSONArray(result);
                            adapter.setUserList(userArr.getJSONObject(0));
                            if(adapter.getUserItemCount() ==userList.size()){
                                recyclerView.setAdapter(adapter);
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
}
