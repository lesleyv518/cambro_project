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
import com.cambro.app.R;
import com.cambro.app.DashboardActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditAccountFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView ey_txt_email, ey_txt_password;
    EditText ey_edt_chemail,ey_edt_password,ey_edt_cp;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    public static EditAccountFragment newInstance() {
        EditAccountFragment f = new EditAccountFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_account, container, false);
        ey_txt_email = (TextView) v.findViewById(R.id.ey_txt_email);
        ey_txt_password = (TextView) v.findViewById(R.id.ey_txt_password);
        ParseUser user = ParseUser.getCurrentUser();
        ey_txt_email.setText(user.getEmail());
        ey_txt_password.setText("undefinedPass");

        ey_edt_chemail = (EditText) v.findViewById(R.id.ey_edt_chemail);
        ey_edt_chemail.setText(user.getEmail());
        ey_edt_password = (EditText) v.findViewById(R.id.ey_edt_password);
        ey_edt_cp = (EditText) v.findViewById(R.id.ey_edt_cp);

        v.findViewById(R.id.ey_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.whereFromLogin = getString(R.string.mnu);

                onButtonPressed("10", false);
            }
        });

        v.findViewById(R.id.ey_txt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserInfo();
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

    private void changeUserInfo() {

        String newEmail = ey_edt_chemail.getText().toString();
        String newPass = ey_edt_password.getText().toString();
        String newConfirmPass = ey_edt_cp.getText().toString();

        if(newEmail.length() == 0 || newPass.length() == 0 || newConfirmPass.length() == 0)
        {
            Toast.makeText(getActivity(), "Please complete the change form",Toast.LENGTH_LONG).show();
            return;
        }

        if(!newPass.equals(newConfirmPass))
        {
            Toast.makeText(getActivity(), "Please confirm your password again",Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Updating......");
        dlg.show();
        ParseUser user = ParseUser.getCurrentUser();
        user.setEmail(newEmail);
        user.setPassword(newPass);
        user.saveInBackground(new SaveCallback() {
            public void done(com.parse.ParseException e) {
                // TODO Auto-generated method stub
                dlg.dismiss();
                if (e == null) {
                    Toast.makeText(getActivity(), "Succesfully Updated!",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Error in updating the information.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
