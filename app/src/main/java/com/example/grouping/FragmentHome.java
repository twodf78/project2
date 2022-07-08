package com.example.grouping;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class FragmentHome extends Fragment {

    HomeRecyclerAdapterAll homeadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = view.getContext();

        homeadapter = new HomeRecyclerAdapterAll(context);
        RecyclerView homeRecView = view.findViewById(R.id.homerecyclerview);
        homeRecView.setAdapter(homeadapter);
        homeRecView.setLayoutManager(new LinearLayoutManager(context));
    }
}

