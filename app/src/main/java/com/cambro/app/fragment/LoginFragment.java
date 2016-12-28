package com.cambro.app.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.DashboardActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.parse.LogInCallback;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment f = new LoginFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final ParseUser user = ParseUser.getCurrentUser();
        Log.e("User name:",String.valueOf(user));
        if (user != null) {
            onButtonPressed("4",true);
        }


        View v = inflater.inflate(R.layout.fragment_login, container, false);
        v.findViewById(R.id.main_iv_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity)getActivity()).whereFromLogin = getResources().getString(R.string.login);
                onButtonPressed("1", true);
            }
        });
        v.findViewById(R.id.main_iv_ca).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity)getActivity()).whereFromLogin = getResources().getString(R.string.login);
                onButtonPressed("2", true);
            }
        });
        v.findViewById(R.id.main_iv_rml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed("4", true);
            }
        });
        v.findViewById(R.id.main_ll_fb).setOnClickListener(new View.OnClickListener() {
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

        v.findViewById(R.id.main_ll_tw).setOnClickListener(new View.OnClickListener() {
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
