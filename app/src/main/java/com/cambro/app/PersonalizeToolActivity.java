package com.cambro.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.cambro.app.adapter.ViewPagerFitFactoryAdapter;
import com.cambro.app.adapter.ViewPagerPersnalizeAdapter;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.FresFit;
import com.cambro.app.model.PersonalizeCategory;
import com.parse.ParseFacebookUtils;

import java.util.List;


public class PersonalizeToolActivity extends FragmentActivity implements OnFragmentInteractionListener {

    private String titles[];
    ViewPagerPersnalizeAdapter adp;
    PersonalizeCategory psCategory;
    private String selectedColor;
    private String productCode;
    private String dimension;
    private String size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnalize);

        titles = new String[]{"a", "b", "c"};
        adp = new ViewPagerPersnalizeAdapter("dash", getSupportFragmentManager(), titles);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.fragment_container_persnalize, adp.getItem(0)).commit();
    }

    @Override
    public void onFragmentInteraction(String uri, boolean blFront) {
        if (uri.equals("finish")) {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            int i = Integer.parseInt(uri);

            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            if (blFront) tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
            else tr.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
            tr.replace(R.id.fragment_container_persnalize, adp.getItem(i));
            tr.addToBackStack(null);
            tr.commit();

        }
    }

    public PersonalizeCategory getPsCategory() {
        return psCategory;
    }

    public void setPsCategory(PersonalizeCategory psCategory) {
        this.psCategory = psCategory;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        DashboardActivity.callbackManager.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Common.captureRequestCode && resultCode == Activity.RESULT_OK) {
            //some code
//            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
//            tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
//            if(Reg.ptKind.equals("tray"))
//                tr.replace(R.id.fragment_container_persnalize, adp.getItem(7));
//            else
//            {
//                if(data.getStringExtra("fragment").equals("color"))
//                    tr.replace(R.id.fragment_container_persnalize, adp.getItem(5));
//                else if(data.getStringExtra("fragment").equals("menu"))
//                {
//                    tr.replace(R.id.fragment_container_persnalize, adp.getItem(2));
//                    tr.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
//                }
//                else
//                    tr.replace(R.id.fragment_container_persnalize, adp.getItem(6));
//            }
//            tr.addToBackStack(null);
//            tr.commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adp = null;
        System.gc();
        Runtime.getRuntime().gc();
    }


}
