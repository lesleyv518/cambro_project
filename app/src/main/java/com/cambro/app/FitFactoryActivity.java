package com.cambro.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.adapter.ViewPagerFitFactoryAdapter;
import com.cambro.app.model.FresFit;

import java.util.List;


public class FitFactoryActivity extends FragmentActivity implements OnFragmentInteractionListener {

    private String titles[];
    ViewPagerFitFactoryAdapter adp;
    FresFit selectedFit;
    FresFit selectedLid;
    String category;
    List<FresFit> lstLids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitfactory);

        titles = new String[]{"a", "b", "c"};
        adp = new ViewPagerFitFactoryAdapter("dash", getSupportFragmentManager(), titles);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.fragment_container_fitfactory, adp.getItem(0)).commit();
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
            tr.replace(R.id.fragment_container_fitfactory, adp.getItem(i));
            tr.addToBackStack(null);
            tr.commit();

        }
    }

    public FresFit getSelectedFit() {
        return selectedFit;
    }

    public void setSelectedFit(FresFit selectedFit) {
        this.selectedFit = selectedFit;
    }

    public FresFit getSelectedLid() {
        return selectedLid;
    }

    public void setSelectedLid(FresFit selectedLid) {
        this.selectedLid = selectedLid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<FresFit> getLstLids() {
        return lstLids;
    }

    public void setLstLids(List<FresFit> lstLids) {
        this.lstLids = lstLids;
    }
}
