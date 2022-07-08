package com.example.grouping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerAdapterAll extends RecyclerView.Adapter<HomeRecyclerAdapterAll.ViewHolder> {
    private final ArrayList<HomeData> homeDataArrayList = new ArrayList<>();
    private final Context context;

    public HomeRecyclerAdapterAll(Context context) {
        this.context = context;
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
        ((ViewHolder)holder).onBind(homeDataArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return homeDataArrayList.size();
    }

    void addItem(HomeData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        homeDataArrayList.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button homevhbtn1;
        Button homevhbtn2;
        Button homevhbtn3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homevhbtn1=itemView.findViewById(R.id.homeshortinfobtn1);
            homevhbtn2=itemView.findViewById(R.id.homeshortinfobtn2);
            homevhbtn3=itemView.findViewById(R.id.homeshortinfobtn3);
        }

        public void onBind(HomeData data){
            //버튼 타입 문제 생길 수도
            homevhbtn1.setText((CharSequence) data.getHomebtn1());
            homevhbtn2.setText((CharSequence) data.getHomebtn2());
            homevhbtn3.setText((CharSequence) data.getHomebtn3());
        }
    }
}
