package com.cambro.app.fragment.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.YouTubeActivity;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashTwoFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public DashTwoFragment() {
        // Required empty public constructor
    }

    public static DashTwoFragment newInstance() {
        DashTwoFragment f = new DashTwoFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_dashtwo, container, false);
        v.findViewById(R.id.fs_ll_fsm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 0);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        v.findViewById(R.id.db_iv_camrack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("9", true);
            }
        });
        v.findViewById(R.id.db_iv_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonPressed("7", true);
                Intent i = new Intent(getActivity(), YouTubeActivity.class);
                i.putExtra("url", 0);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        v.findViewById(R.id.db_iv_social).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("8", true);
            }
        });

        v.findViewById(R.id.db_iv_healthcare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("5", true);
            }
        });
        v.findViewById(R.id.db_iv_cambro_care).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 13);
                i.putExtra("link", Common.CambroCareUrl);
                i.putExtra("title", "CAMBRO CARES");
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
