package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.PersonalizeCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeToolFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public PersonalizeToolFragment() {
        // Required empty public constructor
    }

    public static PersonalizeToolFragment newInstance() {
        PersonalizeToolFragment f = new PersonalizeToolFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_persnal, container, false);

        v.findViewById(R.id.ps_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Runtime.getRuntime().gc();
                onButtonPressed("finish", false);
            }
        });

        v.findViewById(R.id.ps_ll_tray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg.ptKind = "tray";
                onButtonPressed("1", true);
            }
        });

        v.findViewById(R.id.ps_ll_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg.ptKind = "product";
                onButtonPressed("1", true);
            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }
}
