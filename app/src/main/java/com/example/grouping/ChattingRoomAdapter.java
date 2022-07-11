package com.example.grouping;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomAdapter.ViewHolder> {
    private final ArrayList<JSONObject> arrayList;
    private final Context context;

    public ChattingRoomAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChattingRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.chatting_partylist,
                        parent,
                        false
                );
        return new ChattingRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingRoomAdapter.ViewHolder holder, int position) {
        JSONObject jsonData = arrayList.get(position);
        try {
            holder.textTitle.setText(jsonData.getString("title"));
            holder.location.setText(jsonData.getString("location"));
            holder.startTime.setText(jsonData.getString("startTime"));
            holder.endTime.setText(jsonData.getString("endTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.oneParty.setOnClickListener(new View.OnClickListener() {
            //attractive 10점 빼기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChattingRoomActivity.class);
                try {
                    intent.putExtra("suggest_id", jsonData.getString("id"));
                    Toast.makeText(context.getApplicationContext(), jsonData.getString("id") + "suggest ~번의 채팅방으로 진입", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayData(JSONObject jsonData) {
        arrayList.add(jsonData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        LinearLayout oneParty;
        Button location, startTime, endTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.partyTitle);
            location = itemView.findViewById(R.id.partyLocation);
            startTime = itemView.findViewById(R.id.partyStartTime);
            endTime = itemView.findViewById(R.id.partyEndTime);
            oneParty = itemView.findViewById(R.id.oneParty);
        }
    }
}