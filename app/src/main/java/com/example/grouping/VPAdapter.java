package com.example.grouping;

//FragmentPagerAdapter -> 모든 프래그먼트를 메모리에 미리 로딩 시키고 소멸X
//FragmentStatePagerAdapter -> default 3. 자기를 포함한 양 옆까지 메모리에 로딩

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VPAdapter extends FragmentPagerAdapter {

    private List<Fragment> homefragmentList = new ArrayList<>();

    public VPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment){
        homefragmentList.add(fragment);

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return homefragmentList.get(position);
    }

    @Override
    public int getCount() {
        return homefragmentList.size();
    }
}