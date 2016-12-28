package com.cambro.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cambro.app.fragment.dashboard.DashOneFragment;
import com.cambro.app.fragment.dashboard.DashThreeFragment;
import com.cambro.app.fragment.dashboard.DashTwoFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT =3;
    private String titles[] ;
    String keyword;
    List<Fragment> lstFrgs = new ArrayList<Fragment>();

    public ViewPagerHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        //return lstFrgs.get(position);
            switch (position) {
                // Open FragmentTab1.java
                case 0:
                    return DashOneFragment.newInstance();
                case 1:
                    return DashTwoFragment.newInstance();
                case 2:
                    return DashThreeFragment.newInstance();
            }

        return null;
    }

    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}