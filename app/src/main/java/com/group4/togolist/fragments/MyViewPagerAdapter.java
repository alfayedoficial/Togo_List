package com.group4.togolist.fragments;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments;

    public MyViewPagerAdapter(@NonNull FragmentManager fm, int behavior,Fragment[] fragments) {
        super(fm, behavior);
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
