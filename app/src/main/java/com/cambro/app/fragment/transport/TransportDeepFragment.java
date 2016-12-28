package com.cambro.app.fragment.transport;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.TransportActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.model.Transport;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportDeepFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ImageView tpd_iv_electric, tpd_iv_pans;
    private TextView tpd_txt_electric, tpd_txt_pans;
    private ImageView tpd_iv_small, tpd_iv_large;
    private TextView tpd_txt_small, tpd_txt_large;
    String  sDeepSmall, sDeepLarge;

    public TransportDeepFragment() {
        // Required empty public constructor
    }

    public static TransportDeepFragment newInstance() {
        TransportDeepFragment f = new TransportDeepFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_transport_deep, container, false);

        Boolean isElectric = ((TransportActivity)getActivity()).isElectric();
        tpd_iv_electric = ((ImageView)v.findViewById(R.id.tpd_iv_electric));
        tpd_txt_electric = (TextView) v.findViewById(R.id.tpd_txt_electric);
        tpd_iv_pans = ((ImageView)v.findViewById(R.id.tpd_iv_pans));
        tpd_txt_pans = (TextView) v.findViewById(R.id.tpd_txt_pans);

        tpd_iv_small = ((ImageView)v.findViewById(R.id.tpd_iv_small));
        tpd_iv_large = (ImageView) v.findViewById(R.id.tpd_iv_large);

        tpd_txt_small = (TextView) v.findViewById(R.id.tpd_txt_small);
        tpd_txt_large = (TextView) v.findViewById(R.id.tpd_txt_large);

        String sPan = ((TransportActivity)getActivity()).getPans();
        if (isElectric)
        {
            tpd_iv_electric.setImageDrawable(getResources().getDrawable(R.drawable.tp_electric));
            tpd_txt_electric.setText(getString(R.string.tp_txt_electric));
            if (sPan.equals(getString(R.string.tp_txt_multiple_pan)))
            {
                tpd_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_multiple_pan));
                tpd_txt_pans.setText(getString(R.string.tp_txt_multiple_pan));
                sDeepSmall = getString(R.string.tp_txt_small_carriers);
                sDeepLarge = getString(R.string.tp_txt_larger_carts);
//                tpd_iv_small.setImageDrawable(getResources().getDrawable(R.drawable.tp_electric_multi_pans_small_carriers));
//                tpd_iv_large.setImageDrawable(getResources().getDrawable(R.drawable.tp_electric_mp_larger_cart));
            }
        }
        else
        {
            tpd_iv_electric.setImageDrawable(getResources().getDrawable(R.drawable.tp_non_electric));
            tpd_txt_electric.setText(getString(R.string.tp_txt_non_electric));

            if (sPan.equals(getString(R.string.tp_txt_multiple_pan)))
            {
                tpd_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_multiple_pan));
                tpd_txt_pans.setText(getString(R.string.tp_txt_multiple_pan));
                sDeepSmall = getString(R.string.tp_txt_small_carriers);
                sDeepLarge = getString(R.string.tp_txt_larger_carts);
//                tpd_iv_small.setImageDrawable(getResources().getDrawable(R.drawable.tp_nonelectric_multi_pans_small_carriers));
//                tpd_iv_large.setImageDrawable(getResources().getDrawable(R.drawable.tp_nonelectric_multi_pans_larger_cart));
            }
            else if (sPan.equals(getString(R.string.tp_txt_sheet_pan)))
            {
                tpd_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_sheet_pan));
                tpd_txt_pans.setText(getString(R.string.tp_txt_sheet_pan));
                sDeepSmall = getString(R.string.tp_txt_small_carriers_cart);
                sDeepLarge = getString(R.string.tp_txt_larger_cart);
//                tpd_iv_small.setImageDrawable(getResources().getDrawable(R.drawable.tp_nonelectric_sheetpan_small_carriers_carts));
//                tpd_iv_large.setImageDrawable(getResources().getDrawable(R.drawable.tp_nonelectric_sheet_pans_larger_cart));
            }
            else if (sPan.equals(getString(R.string.tp_txt_food_store_boxes)))
            {
                tpd_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_food_storage_boxes));
                tpd_txt_pans.setText(getString(R.string.tp_txt_food_store_boxes));
                sDeepSmall = getString(R.string.tp_txt_small_cart);
                sDeepLarge = getString(R.string.tp_txt_larger_cart);
//                tpd_iv_small.setImageDrawable(getResources().getDrawable(R.drawable.tp_nonelectric_food_storage_boxes_small_cart));
//                tpd_iv_large.setImageDrawable(getResources().getDrawable(R.drawable.tp_nonelectric_food_storage_boxes_larger_cart));
            }
            else if (sPan.equals(getString(R.string.tp_txt_pan_sheetpan)))
            {
                tpd_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_pan_sheet));
                tpd_txt_pans.setText(getString(R.string.tp_txt_pan_sheetpan));
            }
        }

        if(sDeepSmall != null)
            tpd_txt_small.setText(sDeepSmall.toUpperCase());
        if(sDeepLarge != null)
            tpd_txt_large.setText(sDeepLarge.toUpperCase());

        v.findViewById(R.id.tpd_ll_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((TransportActivity)getActivity()).setDeep(sDeepSmall);
                ((TransportActivity)getActivity()).setSmallDeep(true);
                ((TransportActivity)getActivity()).setTPTitle(4);
                onButtonPressed("4", true);
            }
        });
        v.findViewById(R.id.tpd_ll_large).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((TransportActivity)getActivity()).setDeep(sDeepLarge);
                ((TransportActivity)getActivity()).setSmallDeep(false);
                ((TransportActivity)getActivity()).setTPTitle(4);
                onButtonPressed("4", true);
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
