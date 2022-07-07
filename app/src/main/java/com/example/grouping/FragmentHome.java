package com.example.grouping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class FragmentHome extends Fragment {

    private static final String TAG = "Main_Activity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VPAdapter adapter;
//    HomeTabFragmentAll homeTabFragmentAll;
//    HomeTabFragmentCoding homeTabFragmentCoding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout=view.findViewById(R.id.hometablayout);
        viewPager=view.findViewById(R.id.viewpager);

        adapter=new VPAdapter(requireActivity().getSupportFragmentManager(),1);

        //VPAdapter에 컬렉션 담기
        adapter.addFragment(new HomeTabFragmentAll());
        adapter.addFragment(new HomeTabFragmentCoding());

        //ViewPager Fragment 연결
        viewPager.setAdapter(adapter);

        //ViewPager과 TabLayout 연결
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("전체");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("코딩/IT");

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            //[탭] 버튼이 선택되면 자동으로 실행
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                Log.d("MainActivity", "선택된 탭 : " + position);
//
//                Fragment selected = null;
//                if (position == 0) {
//                    selected = homeTabFragmentAll;
//                } else if (position == 1) {
//                    selected = homeTabFragmentCoding;
//                }
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.hometablayout, selected).commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });


        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}