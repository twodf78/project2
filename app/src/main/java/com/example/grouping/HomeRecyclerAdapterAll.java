package com.example.grouping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        HomeData homeData = homeDataArrayList.get(position);

        holder.txttitle.setText(homeData.getHometitle());
    }

    @Override
    public int getItemCount() {
        return homeDataArrayList.size();
    }

    void addItem(HomeData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        this.homeDataArrayList.add(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView homecardview;
        private final TextView txttitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homecardview= itemView.findViewById(R.id.homeparent);
            txttitle=itemView.findViewById(R.id.homecardviewtitle);
        }
    }
}
