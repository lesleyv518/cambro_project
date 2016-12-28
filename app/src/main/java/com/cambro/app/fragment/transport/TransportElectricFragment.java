package com.cambro.app.fragment.transport;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.TransportActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportElectricFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public TransportElectricFragment() {
        // Required empty public constructor
    }

    public static TransportElectricFragment newInstance() {
        TransportElectricFragment f = new TransportElectricFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_transport_electric, container, false);
        v.findViewById(R.id.tpe_ll_electric).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransportActivity)getActivity()).setElectric(true);
                ((TransportActivity)getActivity()).setTPTitle(2);
                onButtonPressed("2", true);
            }
        });
        v.findViewById(R.id.tpe_ll_non_electric).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransportActivity)getActivity()).setElectric(false);
                ((TransportActivity)getActivity()).setTPTitle(2);
                onButtonPressed("2", true);
            }
        });
        v.findViewById(R.id.tpe_ll_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("0", false);
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
