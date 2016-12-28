package com.cambro.app;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;

import com.cambro.app.interfce.SocialLoginService;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.adapter.ViewPagerDashboardAdapter;
import com.facebook.FacebookSdk;
import com.parse.ParseFacebookUtils;

import java.security.MessageDigest;

import android.content.pm.Signature;

public class DashboardActivity extends FragmentActivity implements OnFragmentInteractionListener {

    ViewPager pager;
    private String titles[];
    public ViewPagerDashboardAdapter adp;
    public static String whereFromLogin = "";
    //    public static CallbackManager callbackManager = CallbackManager.Factory.create();
    public static float displayDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.cambro",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash::::", ":::::::::" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            Log.d("KeyHash::::", ":::::::::");
        }
        FacebookSdk.sdkInitialize(getApplicationContext());

        titles = new String[]{"a", "b", "c"};
        adp = new ViewPagerDashboardAdapter("dash", getSupportFragmentManager(), titles);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.fragment_container, adp.getItem(0)).commit();


    }

    public void facebookLogin() {

        SocialLoginService fbservice = SocialLoginService.getInstance(this);
        fbservice.facebookLogin(this, null);
    }

    public void twitterLogin() {

        SocialLoginService twService = SocialLoginService.getInstance(this);
        twService.twitterLogin(this, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteraction(String index, boolean blFront) {
        int i = Integer.parseInt(index);
        displayDensity = getResources().getDisplayMetrics().densityDpi;
        if (i == 10) {
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            if (blFront) {
                tr.setCustomAnimations(R.anim.push_bottom_in, R.anim.push_bottom_out);
                tr.replace(R.id.fragment_container, adp.getItem(i));
            } else {
                if (whereFromLogin.equals(getString(R.string.mnu))) {
                    if (blFront) tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                    else tr.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
                    tr.replace(R.id.fragment_container, adp.getItem(10));
                } else {
                    tr.setCustomAnimations(R.anim.push_top_in, R.anim.push_top_out);
                    tr.replace(R.id.fragment_container, adp.getItem(4));
                }
            }
            tr.addToBackStack(null);
            tr.commit();
            whereFromLogin = "";
            return;
        } else if (i == 100) {
            facebookLogin();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else if (i == 200) {
            twitterLogin();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            if (blFront) tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
            else tr.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
            tr.replace(R.id.fragment_container, adp.getItem(i));
            tr.addToBackStack(null);
            tr.commit();
        }

    }


}
