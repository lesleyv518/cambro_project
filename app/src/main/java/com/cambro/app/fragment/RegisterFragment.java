package com.cambro.app.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.DashboardActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText ca_edt_fn,ca_edt_ln,ca_edt_email,ca_edt_password, ca_edt_cp;
    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment f = new RegisterFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_account, container, false);
        ca_edt_fn = (EditText) v.findViewById(R.id.ca_edt_fn);
        ca_edt_ln = (EditText) v.findViewById(R.id.ca_edt_ln);
        ca_edt_email = (EditText) v.findViewById(R.id.ca_edt_email);
        ca_edt_password = (EditText) v.findViewById(R.id.ca_edt_password);
        ca_edt_cp = (EditText) v.findViewById(R.id.ca_edt_cp);

        v.findViewById(R.id.ca_txt_ca).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseSignup();
            }
        });

        v.findViewById(R.id.ca_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sFrom = ((DashboardActivity)getActivity()).whereFromLogin;
                if(sFrom.equals(getString(R.string.login)))
                    onButtonPressed("0", false);
                else
                    onButtonPressed("10", false);
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

    private void parseSignup()
    {
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Please wait......");
        dlg.show();

        String firstname = ca_edt_fn.getText().toString();
        String lastname = ca_edt_ln.getText().toString();
        String email = ca_edt_email.getText().toString();
        String password = ca_edt_password.getText().toString();
        String confirmPassword = ca_edt_cp.getText().toString();

        // Force user to fill up the form
        if (firstname.length() == 0 || lastname.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            Toast.makeText(getActivity(), "Please complete the sign up form",Toast.LENGTH_LONG).show();

        } else {
            if(!password.equals(confirmPassword))
            {
                Toast.makeText(getActivity(), "Please check the confirm password.",Toast.LENGTH_LONG).show();
                return;
            }
            // Save new user data into Parse.com Data Storage
            ParseUser user = new ParseUser();
            user.setUsername(email);
            user.setPassword(password);
            user.setEmail(email);
            user.put("name", firstname + " " + lastname);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null) {
                        // Show a simple Toast message upon successful registration
                        dlg.dismiss();
                        Toast.makeText(getActivity(),  "Successfully Signed up, please log in.", Toast.LENGTH_LONG).show();
                        onButtonPressed("1", false);
                        RegisterFragment.this.onDestroy();
                    } else {
                        Toast.makeText(getActivity(),
                                "Sign up Error", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }
    }
}