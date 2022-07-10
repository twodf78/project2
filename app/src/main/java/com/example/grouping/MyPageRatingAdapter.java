package com.example.grouping;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPageRatingAdapter extends RecyclerView.Adapter<MyPageRatingAdapter.ViewHolder> {
    private final ArrayList<JSONObject> arrayList ;
    private final Context context;

    public MyPageRatingAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyPageRatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.mypage_itemlist_myrating,
                        parent,
                        false
                );
        return new MyPageRatingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageRatingAdapter.ViewHolder holder, int position) {
        JSONObject jsonData = arrayList.get(position);
        try {
//            holder.image.setText(jsonData.getString("image"));
            holder.name.setText(jsonData.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.bad.setOnClickListener(new View.OnClickListener() {
            //attractive 10점 빼기

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

        holder.good.setOnClickListener(new View.OnClickListener() {
            //attractive 10점 추가

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

        holder.best.setOnClickListener(new View.OnClickListener() {
            //attractive 20점 추가
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
        ImageView image;
        TextView name;
        LinearLayout bad, good, best;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.ratingImage);
            name = itemView.findViewById(R.id.ratingName);
            bad=itemView.findViewById(R.id.ratingBad);
            good=itemView.findViewById(R.id.ratingGood);
            best=itemView.findViewById(R.id.ratingBest);
        }
    }
}
