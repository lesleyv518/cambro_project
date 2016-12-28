package com.cambro.app.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cambro.app.DashboardActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText si_txt_email, si_txt_password;

    public SigninFragment() {
        // Required empty public constructor
    }

    public static SigninFragment newInstance() {
        SigninFragment f = new SigninFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signin, container, false);
        si_txt_email = (EditText) v.findViewById(R.id.si_edt_email);
        si_txt_password = (EditText) v.findViewById(R.id.si_edt_password);

        v.findViewById(R.id.si_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sFrom = ((DashboardActivity)getActivity()).whereFromLogin;
                if(sFrom.equals(getString(R.string.login)))
                    onButtonPressed("0", false);
                else
                    onButtonPressed("10", false);
            }
        });
        v.findViewById(R.id.si_txt_fp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("3", true);
            }
        });
        v.findViewById(R.id.si_txt_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseLogin();
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

    private void parseLogin() {
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Please wait......");
        dlg.show();

        final String username = si_txt_email.getText().toString();
        String password = si_txt_password.getText().toString();

        // Send data to Parse.com for verification
        ParseUser.logInInBackground(username, password,
                new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        dlg.dismiss();
                        if (parseUser != null) {
                            // If user exist and authenticated, send user to Welcome.class

                            SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", parseUser.getString("name"));
                            editor.putString("fullname", parseUser.getString("name"));
                            editor.putString("email", parseUser.getEmail());
                            editor.commit();
                            Toast.makeText(getActivity(),
                                    "Successfully Logged in",
                                    Toast.LENGTH_LONG).show();
                            parseUser.saveInBackground();

                            onButtonPressed("4", true);

                        } else {
                            Toast.makeText(
                                    getActivity(),
                                    "No such user exist, please signup",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
