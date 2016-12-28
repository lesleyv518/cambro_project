package com.cambro.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cambro.app.DashboardActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthcareFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public HealthcareFragment() {
        // Required empty public constructor
    }

    public static HealthcareFragment newInstance() {
        HealthcareFragment f = new HealthcareFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_healthcare, container, false);
        v.findViewById(R.id.hc_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("4", false);
            }
        });
        v.findViewById(R.id.hc_ll_rcc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("11", true);
            }
        });
        v.findViewById(R.id.hc_ll_nstc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("12", true);
            }
        });
        //((TextView)v.findViewById(R.id.hc_txt_explain)).setTypeface(DashboardActivity.fnt_mr_boldcond);
        return v;
    }

    public void onButtonPressed(String uri, boolean bl) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri, bl);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
