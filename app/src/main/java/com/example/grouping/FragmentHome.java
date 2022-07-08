package com.example.grouping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class FragmentHome extends Fragment {

//    Toolbar toolbar;

    HomeTabFragmentAll homeTabFragmentAll;
    HomeTabFragmentCoding homeTabFragmentCoding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        homeTabFragmentAll = new HomeTabFragmentAll();
        homeTabFragmentCoding = new HomeTabFragmentCoding();

        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeframelayout, homeTabFragmentAll).commit();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout tabs = view.findViewById(R.id.hometablayout);
        tabs.addTab(tabs.newTab().setText("통화기록"));
        tabs.addTab(tabs.newTab().setText("스팸기록"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if (position == 0) {
                    selected = homeTabFragmentAll;
                } else if (position == 1) {
                    selected = homeTabFragmentCoding;
                }
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeframelayout, Objects.requireNonNull(selected)).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

