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
public class FitFactoryPansFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    String category;

    public FitFactoryPansFragment() {
        // Required empty public constructor
    }

    public static FitFactoryPansFragment newInstance() {
        FitFactoryPansFragment f = new FitFactoryPansFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory_pan, container, false);
        category = ((FitFactoryActivity) getActivity()).getCategory();
        v.findViewById(R.id.ffp_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("0", false);
            }
        });
        v.findViewById(R.id.ffp_ll_tfp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == null || !category.equals("TranslucentFoodPans"))
                {
                    ((FitFactoryActivity) getActivity()).setCategory("TranslucentFoodPans");
                    Reg.blLoadModePan = true;
                }
                onButtonPressed("8", true);
            }
        });
        v.findViewById(R.id.ffp_ll_cp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == null || !category.equals("CamwearPans"))
                {
                    ((FitFactoryActivity) getActivity()).setCategory("CamwearPans");
                    Reg.blLoadModePan = true;
                }
                onButtonPressed("8", true);
            }
        });
        v.findViewById(R.id.ffp_ll_hhfp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == null || !category.equals("HighHeatFoodPans"))
                {
                    ((FitFactoryActivity) getActivity()).setCategory("HighHeatFoodPans");
                    Reg.blLoadModePan = true;
                }
                onButtonPressed("8", true);
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
