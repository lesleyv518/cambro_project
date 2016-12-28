package com.cambro.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cambro.app.fragment.persnalize.ColorPickerFragment;
import com.cambro.app.fragment.persnalize.PersonalizeLastFragment;
import com.cambro.app.fragment.persnalize.PersonalizeProductDetailFragment;
import com.cambro.app.fragment.persnalize.PersonalizeProductGetPricingFragment;
import com.cambro.app.fragment.persnalize.PersonalizeThankyouFragment;
import com.cambro.app.fragment.persnalize.PersonalizeToolFragment;
import com.cambro.app.fragment.persnalize.PersonalizeTrayDetailFragment;
import com.cambro.app.fragment.persnalize.PersonalizeCategoryFragment;
import com.cambro.app.fragment.persnalize.PersonalizeTrayGetPriceFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerPersnalizeAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT =3;
    private String titles[] ;
    String keyword;
    List<Fragment> lstFrgs = new ArrayList<Fragment>();

    public ViewPagerPersnalizeAdapter(String kw, FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
        this.keyword = kw;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
//                return PersonalizeCameraFragment.newInstance();
            return PersonalizeToolFragment.newInstance();
            case 1:
                return PersonalizeCategoryFragment.newInstance();
            case 2:
                return new Fragment();
            case 3:
                return PersonalizeTrayDetailFragment.newInstance();
            case 4:
                return PersonalizeProductDetailFragment.newInstance();
            case 5:
                return new ColorPickerFragment();
            case 6:
                return PersonalizeProductGetPricingFragment.newInstance();
            case 7:
                return PersonalizeThankyouFragment.newInstance();
            case 8:
                return PersonalizeTrayGetPriceFragment.newInstance();
            case 9:
                return PersonalizeLastFragment.newInstance();
        }
        return  null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}