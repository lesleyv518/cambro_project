package com.cambro.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamlidsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText cr_edt_census, cr_edt_clsm, cr_edt_clsb;
    Spinner cr_spn_clsm,cr_spn_clsb,cr_spn_mllr;
    TextView cr_txt_dclua,cr_txt_dcblua,cr_txt_tadc, cr_txt_irco_clsm,cr_txt_irco_clsb, cr_txt_cftico_clrsm,cr_txt_cftico_clrsb,cr_txt_cftico_mrc,cr_txt_ytirco,cr_txt_ytas,cr_txt_ytas_p;
    float census;
    int select_clsm = 0;
    float select_clsb = 0f;
    float clsm_cost;
    float clsb_cost;
    float loss_rate = 0f;

    float total_clsm;
    float total_clsb;
    float total_waste;

    float clsm_per_case = 1500.0f;
    float clsb_per_case = 1000.0f;
    float clsm_case_cost = 119f;
    float clsb_case_cost = 158.5f;

    float total_clsm_cases;
    float total_clsb_cases;
    float total_clsm_cost;
    float total_clsb_cost;
    float monthly_cost;

    float initial_order;
    float total_savings;
    float percentage_savings;

    public CamlidsFragment() {
        // Required empty public constructor
    }

    public static CamlidsFragment newInstance() {
        CamlidsFragment f = new CamlidsFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_camlids, container, false);

        cr_txt_dclua = (TextView) v.findViewById(R.id.cr_txt_dclua);
        cr_txt_dcblua = (TextView) v.findViewById(R.id.cr_txt_dcblua);
        cr_txt_tadc = (TextView) v.findViewById(R.id.cr_txt_tadc);
        cr_txt_irco_clsm = (TextView) v.findViewById(R.id.cr_txt_irco_clsm);
        cr_txt_irco_clsb = (TextView) v.findViewById(R.id.cr_txt_irco_clsb);
        cr_txt_cftico_clrsm = (TextView) v.findViewById(R.id.cr_txt_cftico_clrsm);
        cr_txt_cftico_clrsb = (TextView) v.findViewById(R.id.cr_txt_cftico_clrsb);
        cr_txt_cftico_mrc = (TextView) v.findViewById(R.id.cr_txt_cftico_mrc);
        cr_txt_ytirco = (TextView) v.findViewById(R.id.cr_txt_ytirco);
        cr_txt_ytas = (TextView) v.findViewById(R.id.cr_txt_ytas);
        cr_txt_ytas_p = (TextView) v.findViewById(R.id.cr_txt_ytas_p);

        v.findViewById(R.id.rc_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("5", false);
            }
        });
        v.findViewById(R.id.cr_iv_loc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 3);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        v.findViewById(R.id.cr_btn_fr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRepValue();
            }
        });
        cr_edt_census = (EditText) v.findViewById(R.id.cr_edt_census);
        cr_edt_census.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(cr_edt_census.length() == 0) cr_edt_census.setText("0");
                    census = Float.parseFloat(cr_edt_census.getText().toString());
                    updateScreen();
                }
                return false;
            }
        });
        cr_spn_clsm = (Spinner) v.findViewById(R.id.cr_spn_clsm);
        cr_spn_clsm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_clsm = position;
                updateScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cr_spn_clsb = (Spinner) v.findViewById(R.id.cr_spn_clsb);
        cr_spn_clsb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_clsb = position;
                updateScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cr_edt_clsm = (EditText) v.findViewById(R.id.cr_edt_clsm);
        cr_edt_clsm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(cr_edt_clsm.length() == 0) cr_edt_clsm.setText("0");
                    clsm_cost = Float.parseFloat(cr_edt_clsm.getText().toString());
                    updateScreen();
                }
                return false;
            }
        });

        cr_edt_clsb = (EditText) v.findViewById(R.id.cr_edt_clsb);
        cr_edt_clsb.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(cr_edt_clsb.length() == 0) cr_edt_clsb.setText("0");
                    clsb_cost = Float.parseFloat(cr_edt_clsb.getText().toString());
                    updateScreen();
                }
                return false;
            }
        });

        cr_spn_mllr = (Spinner) v.findViewById(R.id.cr_spn_mllr);
        cr_spn_mllr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loss_rate = position / 10.0f;
                updateScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    private void sendRepValue()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.mnu_wc)});
        i.putExtra(Intent.EXTRA_SUBJECT, "Reusable Camlids");
        String messageBody = "  Census: " + census+ "\n" + "  CLSM: " + cr_spn_clsm.getSelectedItem().toString() + "\n" + "  CLSM Cost: " + cr_edt_clsm.getText().toString() + "\n" +
                "  CLSB: " + cr_spn_clsb.getSelectedItem().toString() + "\n" + "  CLSB Cost: " + cr_edt_clsb.getText().toString() + "\n" + "  Monthly Lid Loss Rate: " +
                cr_spn_mllr.getSelectedItem().toString();
        i.putExtra(Intent.EXTRA_TEXT, messageBody);

        getActivity().startActivity(Intent.createChooser(i, "Send Email"));
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void updateScreen()
    {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        total_clsm = census * select_clsm * 3 * 365;
        total_clsb = census * select_clsb * 3 * 365;
        total_clsm_cost = Math.round((total_clsm / clsm_per_case) * clsm_cost);
        total_clsb_cost = Math.round((total_clsb / clsb_per_case) * clsb_cost);
        total_waste = total_clsm_cost + total_clsb_cost;

        total_clsm_cases = Math.round((census * 2 * select_clsm) / 240.0f);
        total_clsb_cases = Math.round((census * 2 * select_clsb) / 240.0f);

        float total_clsm_case_cost = (total_clsm_cases * clsm_case_cost);
        float total_clsb_case_cost = (total_clsb_cases * clsb_case_cost);

        initial_order = total_clsm_case_cost + total_clsb_case_cost;
        monthly_cost = Math.round(initial_order * loss_rate);
        double total_savings = Math.ceil(total_waste - (initial_order + (monthly_cost * 12)));
        percentage_savings = Math.round((total_savings / total_waste) * 100);

        cr_txt_dclua.setText(formatter.format((int) total_clsm));
        cr_txt_dcblua.setText(formatter.format((int) total_clsb));

        cr_txt_tadc.setText("$" + formatter.format((int) total_waste));
        cr_txt_irco_clsm.setText(formatter.format((int) total_clsm_cases));
        cr_txt_irco_clsb.setText(formatter.format((int) total_clsb_cases));

        cr_txt_cftico_clrsm.setText("$" + formatter.format((int) total_clsm_case_cost));
        cr_txt_cftico_clrsb.setText("$" + formatter.format((int) Math.ceil(total_clsb_case_cost)));
        cr_txt_cftico_mrc.setText("$" + formatter.format((int) monthly_cost));

        if(initial_order > 0){
            cr_txt_ytirco.setText("$" + formatter.format((int) Math.ceil(initial_order)));
        }
        else{
            cr_txt_ytirco.setText("$0");
        }

        if(total_savings > 0){
            cr_txt_ytas.setText("$" + formatter.format((int) total_savings));
            cr_txt_ytas_p.setText(formatter.format((int) percentage_savings) + "%");
        }
        else{
            cr_txt_ytas.setText("$0");
            cr_txt_ytas_p.setText("0%");
        }
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
