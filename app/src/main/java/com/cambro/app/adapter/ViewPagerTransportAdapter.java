package com.cambro.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cambro.app.fragment.transport.TransportBeveragesFragment;
import com.cambro.app.fragment.transport.TransportElectricFragment;
import com.cambro.app.fragment.transport.TransportDeepFragment;
import com.cambro.app.fragment.transport.TransportProductFragment;
import com.cambro.app.fragment.transport.TransportFragment;
import com.cambro.app.fragment.transport.TransportPanFragment;
import com.cambro.app.fragment.transport.TransportQuoteFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTransportAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT =3;
    private String titles[] ;
    String keyword;
    List<Fragment> lstFrgs = new ArrayList<Fragment>();

    public ViewPagerTransportAdapter(String kw, FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
        this.keyword = kw;
    }

    @Override
    public Fragment getItem(int position) {

        //return lstFrgs.get(position);
            switch (position) {
                // Open FragmentTab1.java
                case 0:
                    return TransportFragment.newInstance();
                case 1:
                    return TransportElectricFragment.newInstance();
                case 2:
                    return TransportPanFragment.newInstance();
                case 3:
                    return TransportDeepFragment.newInstance();
                case 4:
                    return TransportProductFragment.newInstance();
                case 5:
                    return TransportBeveragesFragment.newInstance();
                case 6:
                    return TransportQuoteFragment.newInstance();
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