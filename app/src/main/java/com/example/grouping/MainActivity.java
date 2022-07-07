package com.example.grouping;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.framemainlayout, HomeFragment.class, null)
                    .commit();
        }

        /* ==== set navigation bar ==== */

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabchatting)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, ChattingFragment.class, null)
                        .commit();
            else if (itemId == R.id.tabhome)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, HomeFragment.class, null)
                        .commit();
            else if (itemId == R.id.tabmypage)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, MypageFragment.class, null)
                        .commit();
            else
                return false;
            return true;
        });
        navigation.setOnItemReselectedListener(null);

//        //ViewPager 부분
//        ViewPager vp = findViewById(R.id.viewpager);
//        VPAdapter vpadapter = new VPAdapter(getSupportFragmentManager());
//        vp.setAdapter(vpadapter);hj
    }
}