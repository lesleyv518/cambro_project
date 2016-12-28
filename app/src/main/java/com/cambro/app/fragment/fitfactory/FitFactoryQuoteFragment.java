package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.R;
import com.cambro.app.FitFactoryActivity;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.utils.MultiLineEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitFactoryQuoteFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    EditText ffq_edt_name, ffq_edt_email,ffq_edt_phone,ffq_edt_address,ffq_edt_quantity;
    CheckBox ffq_chk_hcsr;
    TextView ffq_txt_quote;

    public FitFactoryQuoteFragment() {
        // Required empty public constructor
    }

    public static FitFactoryQuoteFragment newInstance() {
        FitFactoryQuoteFragment f = new FitFactoryQuoteFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory_quote, container, false);

        ffq_edt_name = (EditText)v.findViewById(R.id.ffq_edt_name);
        ffq_edt_email = (EditText)v.findViewById(R.id.ffq_edt_email);
        ffq_edt_phone = (EditText)v.findViewById(R.id.ffq_edt_phone);
        ffq_edt_address = (EditText)v.findViewById(R.id.ffq_edt_address);
        ffq_edt_quantity = (EditText)v.findViewById(R.id.ffq_edt_quantity);

        v.findViewById(R.id.ffq_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("5", false);
            }
        });

        v.findViewById(R.id.ffq_txt_quote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInputItems())
                {
                    onButtonPressed("10", true);
                    new SendMailProcess().execute();
                }
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        ffq_edt_name.setText(username);
        String email = prefs.getString("email", "");
        ffq_edt_email.setText(email);

        return v;
    }

    private boolean checkInputItems() {
        if (ffq_edt_name.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), getString(R.string.ff_txt_piyn), Toast.LENGTH_LONG).show();
            return false;
        }

        if (ffq_edt_email.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), getString(R.string.ff_txt_piyea), Toast.LENGTH_LONG).show();
            return false;
        }

        String email = ffq_edt_email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ffq_edt_phone.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), getString(R.string.ff_txt_piypn), Toast.LENGTH_LONG).show();
            return false;
        }

        if (ffq_edt_address.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), getString(R.string.ff_txt_piya), Toast.LENGTH_LONG).show();
            return false;
        }

        if (ffq_edt_quantity.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), getString(R.string.ff_txt_piq), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private class SendMailProcess extends AsyncTask {
        @Override

        protected Object doInBackground(Object... arg0) {

            sendEmail();
            return null;
        }
    }

    private void sendEmail() {
        try {
            String body = "";
            body += "Name: " + ffq_edt_name.getText().toString() + "\n";
            body += "Email: " + ffq_edt_email.getText().toString()  + "\n";
            body += "Phone: " + ffq_edt_phone.getText().toString()  + "\n";
            body += "Address: " + ffq_edt_address.getText().toString()  + "\n";
            body += "Quantity: " + ffq_edt_quantity.getText().toString()  + "\n";
            body += "Product Code: " + ((FitFactoryActivity)getActivity()).getSelectedFit().getProductCode()  + "\n";
            if (((FitFactoryActivity)getActivity()).getSelectedLid() != null)
                body += "Lid Code: " + ((FitFactoryActivity)getActivity()).getSelectedLid().getLidCode()  + "\n";
            GMailSender sender = new GMailSender(Common.cambroEmail, Common.cambroEmailPassword);
            sender.sendMail("Fit Factory",
                    body,
                    Common.ianEmail,
                    ffq_edt_email.getText().toString());
            sender.sendMail("Fit Factory",
                    body,
                    Common.cramosEmail,
                    ffq_edt_email.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.ff_txt_picea), Toast.LENGTH_LONG).show();
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
