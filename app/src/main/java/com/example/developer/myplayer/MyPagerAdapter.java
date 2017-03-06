package com.example.developer.myplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.developer.myplayer.LocalVideo.LocalVideoFragment;

/**
 * Created by Developer on 2017/3/1.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new LocalVideoFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

}
