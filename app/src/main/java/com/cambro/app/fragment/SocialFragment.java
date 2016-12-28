package com.cambro.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public SocialFragment() {
        // Required empty public constructor
    }

    public static SocialFragment newInstance() {
        SocialFragment f = new SocialFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    private void loadSocialWebview(int param)
    {
        Intent i = new Intent(getActivity(), SocialActivity.class);
        i.putExtra("social", param);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_social, container, false);
        v.findViewById(R.id.gs_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("4", false);
            }
        });
        v.findViewById(R.id.gs_iv_loc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 3);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        v.findViewById(R.id.gs_iv_fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonPressed("16", true);
                loadSocialWebview(4);
            }
        });
        v.findViewById(R.id.gs_iv_fb1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("16", true);
                loadSocialWebview(4);
            }
        });

        v.findViewById(R.id.gs_iv_tw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("17", true);
                loadSocialWebview(5);
            }
        });
        v.findViewById(R.id.gs_iv_tw1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("17", true);
                loadSocialWebview(5);
            }
        });

        v.findViewById(R.id.gs_iv_yt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("18", true);
                loadSocialWebview(6);
            }
        });
        v.findViewById(R.id.gs_iv_yt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("18", true);
                loadSocialWebview(6);
            }
        });

        v.findViewById(R.id.gs_iv_lk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("19", true);
                loadSocialWebview(7);
            }
        });
        v.findViewById(R.id.gs_iv_lk1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("19", true);
                loadSocialWebview(7);
            }
        });

        v.findViewById(R.id.gs_iv_pt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("20", true);
                loadSocialWebview(8);
            }
        });
        v.findViewById(R.id.gs_iv_pt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("20", true);
                loadSocialWebview(8);
            }
        });

        v.findViewById(R.id.gs_iv_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("21", true);
                loadSocialWebview(9);
            }
        });
        v.findViewById(R.id.gs_iv_in1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("21", true);
                loadSocialWebview(9);
            }
        });

        v.findViewById(R.id.gs_iv_gg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("22", true);
                loadSocialWebview(10);
            }
        });
        v.findViewById(R.id.gs_iv_gg1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("22", true);
                loadSocialWebview(10);
            }
        });
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
