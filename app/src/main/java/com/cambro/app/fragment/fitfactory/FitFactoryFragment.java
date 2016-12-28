package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitFactoryFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public FitFactoryFragment() {
        // Required empty public constructor
    }

    public static FitFactoryFragment newInstance() {
        FitFactoryFragment f = new FitFactoryFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory, container, false);
        v.findViewById(R.id.ff_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg.blLoadModeTumbler = true;
                onButtonPressed("finish", false);
            }
        });

        v.findViewById(R.id.ff_ll_thumbler).setBackgroundColor(getResources().getColor(R.color.color_bk_thumbler));

        v.findViewById(R.id.ff_ll_thumbler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("1", true);
            }
        });

        v.findViewById(R.id.ff_ll_pan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("2", true);
            }
        });

        v.findViewById(R.id.ff_ll_storage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("3", true);
            }
        });

        v.findViewById(R.id.ff_ll_healthcare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("7", true);
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
