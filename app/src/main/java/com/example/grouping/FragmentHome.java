package com.example.grouping;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class FragmentHome extends Fragment {

    HomeRecyclerAdapterAll homeadapter;

    public FragmentHome() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = view.getContext();

        //recyclerview 만드는 부분
        homeadapter = new HomeRecyclerAdapterAll(context);
        RecyclerView homeRecView = view.findViewById(R.id.homerecyclerview);
        homeRecView.setAdapter(homeadapter);
        homeRecView.setLayoutManager(new LinearLayoutManager(context));

        //새 글을 만드는 부분
        EditText edittitle = view.findViewById(R.id.homeedittitle);
//        Button homewritingaddbtn = view.findViewById(R.id.homeaddwritingbtn);

//        String title = edittitle.getText().toString();

//        homeadapter.addItem(new HomeData(title));

//        homewritingaddbtn.setOnClickListener(v -> {
//            String title = edittitle.getText().toString();
//
//            homeadapter.addItem(new HomeData(title));
//        });
    }
}

