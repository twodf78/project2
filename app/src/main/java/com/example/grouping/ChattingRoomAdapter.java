package com.example.grouping;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomAdapter.ViewHolder> {
    private final ArrayList<String> messageList;
    private final ArrayList<JSONObject> userList;
    private final Context context;
    private static final String URL = "http://192.249.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;
    private String userName;
    public ChattingRoomAdapter(Context context) {
        this.context = context;
        messageList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChattingRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.chatting_left_item_list,
                        parent,
                        false
                );
        return new ChattingRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingRoomAdapter.ViewHolder holder, int position) {
        String messageData = messageList.get(position);
        JSONObject userData = userList.get(position);
        try {
            holder.message.setText(messageData);
            holder.name.setText(userData.getString("name"));
//            holder.image.setText(userData.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public int getUserItemCount() {
        return userList.size();
    }

    public void setMessageList(String string) {
        messageList.add(string);
    }
    public void setUserList(JSONObject jsonData) {
        userList.add(jsonData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView message, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.chattingImage);
            message = itemView.findViewById(R.id.chattingMessage);
            name = itemView.findViewById(R.id.chattingName);
        }
    }


}