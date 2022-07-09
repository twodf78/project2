package com.example.grouping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ViewHolder> {
    private final ArrayList<JSONObject> arrayList ;
    private final Context context;

    public ChattingAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChattingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.chatting_partylist,
                        parent,
                        false
                );
        return new ChattingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingAdapter.ViewHolder holder, int position) {
        JSONObject jsonData = arrayList.get(position);
        try {
            holder.textTitle.setText(jsonData.getString("user_id"));
            holder.btn1.setText(jsonData.getString("suggest_id"));
            holder.btn2.setText(jsonData.getString("chatting"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        Button btn1,btn2,btn3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.partyOwner);
            btn1=itemView.findViewById(R.id.chattingBtn1);
            btn2=itemView.findViewById(R.id.chattingBtn2);
            btn3=itemView.findViewById(R.id.chattingBtn3);
        }
    }
}