package com.example.grouping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMypage extends Fragment {

    CardView mypageWritingCardview;
    CardView mypageRatingCardview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mypageWritingCardview=view.findViewById(R.id.mypageseemywriting);
        mypageRatingCardview=view.findViewById(R.id.mypageseemyrating);
        mypageWritingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMywritingActivity.class);
                startActivity(intent);
            }
        });

        mypageRatingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMyratingActivity.class);
                startActivity(intent);
            }
        });
    }

}