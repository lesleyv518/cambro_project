package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.FitFactoryActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitFactoryThankyouFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public FitFactoryThankyouFragment() {
        // Required empty public constructor
    }

    public static FitFactoryThankyouFragment newInstance() {
        FitFactoryThankyouFragment f = new FitFactoryThankyouFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory_thankyou, container, false);
        v.findViewById(R.id.ffty_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("6", false);
            }
        });

        v.findViewById(R.id.ffty_txt_fal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FitFactoryActivity)getActivity()).setSelectedLid(null);
                ((FitFactoryActivity)getActivity()).setSelectedFit(null);
                onButtonPressed("0", false);
            }
        });

        v.findViewById(R.id.ffty_txt_gh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("finish", false);
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
}
