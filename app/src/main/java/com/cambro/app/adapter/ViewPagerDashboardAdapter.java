package com.cambro.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cambro.app.fragment.CamlidsFragment;
import com.cambro.app.fragment.CamrackFragment;
import com.cambro.app.fragment.DashboardFragment;
import com.cambro.app.fragment.EditAccountFragment;
import com.cambro.app.fragment.FoodSafetyFragment;
import com.cambro.app.fragment.fitfactory.FitFactoryFragment;
import com.cambro.app.fragment.ForgotpassFragment;
import com.cambro.app.fragment.FreshnessDetailFragment;
import com.cambro.app.fragment.FreshnessFragment;
import com.cambro.app.fragment.HealthcareFragment;
import com.cambro.app.fragment.LoginFragment;
import com.cambro.app.fragment.MenuFragment;
import com.cambro.app.fragment.NonskidFragment;
import com.cambro.app.fragment.RegisterFragment;
import com.cambro.app.fragment.SigninFragment;
import com.cambro.app.fragment.SocialFragment;
import com.cambro.app.fragment.TriviaFragment;
import com.cambro.app.fragment.TrivloginPrizeFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDashboardAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT =3;
    private String titles[] ;
    String keyword;
    List<Fragment> lstFrgs = new ArrayList<Fragment>();

    public ViewPagerDashboardAdapter(String kw, FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
        this.keyword = kw;
        //PAGE_COUNT = titles2.length;
        PAGE_COUNT = 13;

        //Login, Signup
//        lstFrgs.add(LoginFragment.newInstance());//0
//        lstFrgs.add(SigninFragment.newInstance());//1
//        lstFrgs.add(RegisterFragment.newInstance());//2
//        lstFrgs.add(ForgotpassFragment.newInstance());//3
//
//        //Dashboard Items
//        lstFrgs.add(DashboardFragment.newInstance());//4
//        lstFrgs.add(HealthcareFragment.newInstance());//5
//        lstFrgs.add(TriviaFragment.newInstance());//6
//        lstFrgs.add(EditAccountFragment.newInstance());//7
//        lstFrgs.add(SocialFragment.newInstance());//8
//        lstFrgs.add(CamrackFragment.newInstance());//9
//
//        //Menu
//        lstFrgs.add(MenuFragment.newInstance());//10
//
//        //Healthcare items
//        lstFrgs.add(CamlidsFragment.newInstance());//11
//        lstFrgs.add(NonskidFragment.newInstance());//12
//        lstFrgs.add(TrivloginPrizeFragment.newInstance());//13
//        lstFrgs.add(FreshnessFragment.newInstance());//14
//        lstFrgs.add(FreshnessDetailFragment.newInstance());//15
//
//        //Food Safety
//        lstFrgs.add(FoodSafetyFragment.newInstance());//16


    }

    @Override
    public Fragment getItem(int position) {

//        return lstFrgs.get(position);
        if (keyword.equals("dash"))
        {
            switch (position) {
                // Open FragmentTab1.java
                case 0:
                    return LoginFragment.newInstance();
                case 1:
                    return SigninFragment.newInstance();
                case 2:
                    return RegisterFragment.newInstance();
                case 3:
                    return ForgotpassFragment.newInstance();
                case 4:
                    return DashboardFragment.newInstance();
                case 5:
                    return HealthcareFragment.newInstance();
                case 6:
                    return TriviaFragment.newInstance();
                case 7:
                    return EditAccountFragment.newInstance();
                case 8:
                    return SocialFragment.newInstance();
                case 9:
                    return CamrackFragment.newInstance();
                case 10:
                    return MenuFragment.newInstance();
                case 11:
                    return CamlidsFragment.newInstance();
                case 12:
                    return NonskidFragment.newInstance();
                case 13:
                    return TrivloginPrizeFragment.newInstance();
                case 14:
                    return FreshnessFragment.newInstance();
                case 15:
                    return FreshnessDetailFragment.newInstance();
                case 16:
                    return FoodSafetyFragment.newInstance();
            }
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