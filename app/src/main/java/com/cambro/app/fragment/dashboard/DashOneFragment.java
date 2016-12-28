package com.cambro.app.fragment.dashboard;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.FitFactoryActivity;
import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.TransportActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashOneFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public DashOneFragment() {
        // Required empty public constructor
    }

    public static DashOneFragment newInstance() {
        DashOneFragment f = new DashOneFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_dashone, container, false);
        v.findViewById(R.id.db_iv_persnalize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PersonalizeToolActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                //onButtonPressed("16", true);
            }
        });
        v.findViewById(R.id.db_iv_fitfactory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FitFactoryActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                //onButtonPressed("16", true);
            }
        });
        v.findViewById(R.id.db_iv_transport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TransportActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        v.findViewById(R.id.db_iv_trivia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTrivia();
            }
        });
        v.findViewById(R.id.db_iv_np).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSocialWebview(1);
            }
        });

        v.findViewById(R.id.db_iv_fc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("14", true);
            }
        });

        return v;
    }

    private void loadSocialWebview(int param)
    {
        Intent i = new Intent(getActivity(), SocialActivity.class);
        i.putExtra("social", param);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void goTrivia()
    {
        SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Trivia", Context.MODE_PRIVATE);
        int answerNumber = prefs.getInt("answer_number", -1);
        if(answerNumber == Utils.calculateQuestionNumber())
        {
            onButtonPressed("13", true);
        }
        else
        {
            onButtonPressed("6", true);
        }
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
