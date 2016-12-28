package com.cambro.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class NonskidFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText ns_edt_census, ns_edt_cpc;
    Spinner ns_spn_mpc, ns_spn_synsct, ns_spn_sycc;
    int camtray=34, total_year = 0,total_waste=0,total_cost=0, mats_per_case = 1000;
    float cost_per_case = 60.0f, total_spent=0f,census = 0f;
    TextView ns_txt_total_year, ns_txt_total_spent,ns_txt_total_waste,ns_txt_total_cost_order,ns_txt_nolb;
    TextView ns_txt_year_one_savings, ns_txt_year_two_savings, ns_txt_year_three_savings, ns_txt_year_four_savings,ns_txt_year_five_savings;
    TextView ns_txt_year_one_percent, ns_txt_year_two_percent, ns_txt_year_three_percent, ns_txt_year_four_percent, ns_txt_year_five_percent;
    public NonskidFragment() {
        // Required empty public constructor
    }

    public static NonskidFragment newInstance() {
        NonskidFragment f = new NonskidFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nonskid, container, false);

        v.findViewById(R.id.ns_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("5", false);
            }
        });
        v.findViewById(R.id.ns_iv_loc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 3);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        v.findViewById(R.id.ns_btn_fr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRepValue();
            }
        });
        ns_txt_total_year = (TextView) v.findViewById(R.id.ns_txt_total_year);
        ns_txt_total_spent = (TextView) v.findViewById(R.id.ns_txt_total_spent);
        ns_txt_total_waste = (TextView) v.findViewById(R.id.ns_txt_total_waste);
        ns_txt_total_cost_order = (TextView) v.findViewById(R.id.ns_txt_total_cost_order);

        ns_txt_year_one_savings = (TextView) v.findViewById(R.id.ns_txt_year_one_savings);
        ns_txt_year_two_savings = (TextView) v.findViewById(R.id.ns_txt_year_two_savings);
        ns_txt_year_three_savings = (TextView) v.findViewById(R.id.ns_txt_year_three_savings);
        ns_txt_year_four_savings = (TextView) v.findViewById(R.id.ns_txt_year_four_savings);
        ns_txt_year_five_savings = (TextView) v.findViewById(R.id.ns_txt_year_five_savings);

        ns_txt_year_one_percent = (TextView) v.findViewById(R.id.ns_txt_year_one_percent);
        ns_txt_year_two_percent = (TextView) v.findViewById(R.id.ns_txt_year_two_percent);
        ns_txt_year_three_percent = (TextView) v.findViewById(R.id.ns_txt_year_three_percent);
        ns_txt_year_four_percent = (TextView) v.findViewById(R.id.ns_txt_year_four_percent);
        ns_txt_year_five_percent = (TextView) v.findViewById(R.id.ns_txt_year_five_percent);

        ns_spn_sycc = (Spinner) v.findViewById(R.id.ns_spn_sycc);

        ns_txt_nolb = (TextView) v.findViewById(R.id.ns_txt_nolb);

        ns_edt_census = (EditText) v.findViewById(R.id.ns_edt_census);
        ns_edt_census.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    census = Integer.parseInt(ns_edt_census.getText().toString());
                    updateScreen();
                }
                return false;
            }
        });
        ns_edt_cpc = (EditText) v.findViewById(R.id.ns_edt_cpc);
        ns_edt_cpc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                cost_per_case = Float.parseFloat(ns_edt_cpc.getText().toString());
                updateScreen();
                return false;
            }
        });
        ns_spn_mpc = (Spinner) v.findViewById(R.id.ns_spn_mpc);
        ns_spn_mpc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                    mats_per_case = 1000;
                else
                    mats_per_case = 1500;
                updateScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ns_spn_synsct = (Spinner) v.findViewById(R.id.ns_spn_synsct);
        ns_spn_synsct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) camtray = 34;
                else camtray = 38;
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
        i.putExtra(Intent.EXTRA_SUBJECT, "Non-Skid Camtrays");
        String messageBody = "  Census: " + census + "\n" + "  Cost Per Case: " + ns_edt_cpc.getText().toString() + "\n" + "  Mats Per Case: " + ns_spn_mpc.getSelectedItem().toString()
                + "\n" + "  Non-Skid Camtray Selected: " + ns_spn_synsct.getSelectedItem().toString() + "\n" + "  Color Combination:  " + ns_spn_sycc.getSelectedItem().toString();
        i.putExtra(Intent.EXTRA_TEXT, messageBody);

        getActivity().startActivity(Intent.createChooser(i, "Send Email"));
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    private void updateScreen()
    {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        total_year = Math.round(census * 3 * 365);
        total_spent = Math.round((cost_per_case / mats_per_case) * total_year);
        total_waste = (int) Math.round((0.02 * total_year) / 2000);
        total_cost = Math.round(census * camtray);

        ns_txt_total_year.setText(formatter.format((int)total_year));
        ns_txt_total_spent.setText("$" + formatter.format((int) total_spent));
        ns_txt_total_waste.setText(formatter.format((int)total_waste));
        ns_txt_nolb.setText(formatter.format((int)census));
        ns_txt_total_cost_order.setText("$" + formatter.format((int)total_cost));

        updateSavings();
    }

    private void updateSavings(){

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        if(total_spent == 0) return;
        int year_one = Math.round(total_spent - total_cost);
        int year_two = Math.round((total_spent * 2) - total_cost);
        int year_three = Math.round((total_spent * 3) - total_cost);
        int year_four = Math.round((total_spent * 4) - total_cost);
        int year_five = Math.round((total_spent * 5) - total_cost);

        ns_txt_year_one_savings.setText("$" + formatter.format((int)year_one));
        ns_txt_year_two_savings.setText("$" + formatter.format((int)year_two));
        ns_txt_year_three_savings.setText("$" + formatter.format((int)year_three));
        ns_txt_year_four_savings.setText("$" + formatter.format((int)year_four));
        ns_txt_year_five_savings.setText("$" + formatter.format((int)year_five));

        int year_one_percent = Math.round((year_one / total_spent) * 100);
        int year_two_percent = Math.round((year_two / total_spent) * 100);
        int year_three_percent = Math.round((year_three / total_spent) * 100);
        int year_four_percent = Math.round((year_four / total_spent) * 100);
        int year_five_percent = Math.round((year_five / total_spent) * 100);

        ns_txt_year_one_percent.setText(formatter.format((int)year_one_percent) + "%");
        ns_txt_year_two_percent.setText(formatter.format((int)year_two_percent) + "%");
        ns_txt_year_three_percent.setText(formatter.format((int)year_three_percent) + "%");
        ns_txt_year_four_percent.setText(formatter.format((int)year_four_percent) + "%");
        ns_txt_year_five_percent.setText(formatter.format((int)year_five_percent) + "%");
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
