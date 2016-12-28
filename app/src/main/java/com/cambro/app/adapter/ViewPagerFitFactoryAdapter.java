package com.cambro.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cambro.app.fragment.fitfactory.FitFactoryFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryHealthcareFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryLidFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryPanDetailFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryPansFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryQuoteFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryResultFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryStorageDetailFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryStorageFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryThankyouFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryTumblersFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFitFactoryAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT =3;
    private String titles[] ;
    String keyword;
    List<Fragment> lstFrgs = new ArrayList<Fragment>();

    public ViewPagerFitFactoryAdapter(String kw, FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
        this.keyword = kw;
    }

    @Override
    public Fragment getItem(int position) {

            switch (position) {
                // Open FragmentTab1.java
                case 0:
                    return FitFactoryFragment.newInstance();
                case 1:
                    return FitFactoryTumblersFragment.newInstance();
                case 2:
                    return FitFactoryPansFragment.newInstance();
                case 3:
                    return FitFactoryStorageFragment.newInstance();
                case 4:
                    return FitFactoryLidFragment.newInstance();
                case 5:
                    return FitFactoryResultFragment.newInstance();
                case 6:
                    return FitFactoryQuoteFragment.newInstance();
                case 7:
                    return FitFactoryHealthcareFragment.newInstance();
                case 8:
                    return FitFactoryPanDetailFragment.newInstance();
                case 9:
                    return FitFactoryStorageDetailFragment.newInstance();
                case 10:
                    return FitFactoryThankyouFragment.newInstance();
            }

        return null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}