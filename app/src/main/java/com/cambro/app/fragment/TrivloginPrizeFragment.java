package com.cambro.app.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cambro.app.R;
import com.cambro.app.DashboardActivity;
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrivloginPrizeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public TrivloginPrizeFragment() {
        // Required empty public constructor
    }

    public static TrivloginPrizeFragment newInstance() {
        TrivloginPrizeFragment f = new TrivloginPrizeFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trivia_login, container, false);
        v.findViewById(R.id.tl_txt_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("4", false);
            }
        });
        SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Trivia", Context.MODE_PRIVATE);
        Integer answerNumber = prefs.getInt("answer_number", -1);
        if (answerNumber == Utils.calculateQuestionNumber())
        {
            v.findViewById(R.id.tl_ll_prize).setVisibility(View.VISIBLE);
            v.findViewById(R.id.tl_ll_login).setVisibility(View.GONE);
            v.findViewById(R.id.tl_iv_fb).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), SocialActivity.class);
                    i.putExtra("social", 4);
                    getActivity().startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
            v.findViewById(R.id.tl_iv_tw).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), SocialActivity.class);
                    i.putExtra("social", 5);
                    getActivity().startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
        }
        else
        {
            v.findViewById(R.id.tl_ll_fb).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.dlg_open_FB))
                            .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onButtonPressed("100", true);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null).show();
                }
            });
            v.findViewById(R.id.tl_ll_tw).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.dlg_open_tw))
                            .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onButtonPressed("200", true);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null).show();
                }
            });
            v.findViewById(R.id.tl_ll_ya).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DashboardActivity) getActivity()).whereFromLogin = getResources().getString(R.string.login);
                    onButtonPressed("1", true);
                }
            });
            v.findViewById(R.id.tl_ll_ca).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DashboardActivity) getActivity()).whereFromLogin = getResources().getString(R.string.login);
                    onButtonPressed("2", true);
                }
            });
        }

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
