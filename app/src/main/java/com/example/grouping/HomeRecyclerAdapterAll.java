package com.example.grouping;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeRecyclerAdapterAll extends RecyclerView.Adapter<HomeRecyclerAdapterAll.ViewHolder> {
    private final ArrayList<JSONObject> arrayList ;
    private final Context context;

    public HomeRecyclerAdapterAll(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeRecyclerAdapterAll.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.home_itemlist_all,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapterAll.ViewHolder holder, int position) {
        JSONObject jsonData = arrayList.get(position);
        try {
            holder.textContent.setText(jsonData.getString("content"));
            holder.textTitle.setText(jsonData.getString("hobby_id"));
            holder.btn1.setText(jsonData.getString("created_by"));
            holder.btn2.setText(jsonData.getString("startTime"));
            holder.btn3.setText(jsonData.getString("endTIME"));
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
        Button btn1,btn2,btn3;
        LinearLayout oneSuggest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent= itemView.findViewById(R.id.homecardviewcontent);
            textTitle=itemView.findViewById(R.id.homecardviewtitle);
            btn1=itemView.findViewById(R.id.homeshortinfobtn1);
            btn2=itemView.findViewById(R.id.homeshortinfobtn2);
            btn3=itemView.findViewById(R.id.homeshortinfobtn3);
            oneSuggest = itemView.findViewById(R.id.oneSuggest);
        }
    }
}
