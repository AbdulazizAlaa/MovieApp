package com.abdulaziz.moviesapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by abdulaziz on 3/3/17.
 */

public class PagerAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragments;

    int pos = 0;

    public PagerAdapter(FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<Fragment>();
    }

    public Fragment getCurrentFragment(){
        return fragments.get(pos);
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        pos = position;
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
