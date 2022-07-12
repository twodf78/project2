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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPageWritingAdapter extends RecyclerView.Adapter<MyPageWritingAdapter.ViewHolder> {
    private final ArrayList<JSONObject> arrayList ;
    private final Context context;

    public MyPageWritingAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyPageWritingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.mypage_itemlist_mywriting,
                        parent,
                        false
                );
        return new MyPageWritingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageWritingAdapter.ViewHolder holder, int position) {
        JSONObject jsonData = arrayList.get(position);
        try {
            holder.textContent.setText(jsonData.getString("content"));
            holder.textTitle.setText(jsonData.getString("title"));
            holder.location.setText(jsonData.getString("location"));
            holder.startTime.setText(jsonData.getString("startTime").replace("T"," ").replace(".000Z",""));
            holder.endTime.setText(jsonData.getString("endTime").replace("T"," ").replace(".000Z",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.oneSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeSeeWriting.class);
                try {
                    intent.putExtra("suggest_id",jsonData.getString("id"));
                    intent.putExtra("user_id",jsonData.getString("created_by"));
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
        TextView textContent;
        Button location,startTime,endTime;
        LinearLayout oneSuggest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent= itemView.findViewById(R.id.myWritingContent);
            textTitle=itemView.findViewById(R.id.myWritingTitle);
            location=itemView.findViewById(R.id.myWritingLocation);
            startTime=itemView.findViewById(R.id.myWritingStartTime);
            endTime=itemView.findViewById(R.id.myWritingEndTime);
            oneSuggest = itemView.findViewById(R.id.oneSuggest);
        }
    }
}
