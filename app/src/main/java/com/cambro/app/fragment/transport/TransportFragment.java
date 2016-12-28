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
import com.cambro.app.interfce.Reg;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public TransportFragment() {
        // Required empty public constructor
    }

    public static TransportFragment newInstance() {
        TransportFragment f = new TransportFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_transport, container, false);
        v.findViewById(R.id.tp_ll_foodpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransportActivity)getActivity()).setCategory("Food");
                ((TransportActivity)getActivity()).setTPTitle(1);
                onButtonPressed("1", true);
            }
        });
        v.findViewById(R.id.tp_ll_beverages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransportActivity)getActivity()).setCategory("Beverage");
                ((TransportActivity)getActivity()).setTPTitle(5);
                onButtonPressed("5", true);
            }
        });
        ((TransportActivity)getActivity()).setTitle("");
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
