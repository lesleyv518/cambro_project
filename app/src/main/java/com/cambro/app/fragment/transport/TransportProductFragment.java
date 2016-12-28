package com.cambro.app.fragment.transport;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.TransportActivity;
import com.cambro.app.adapter.TransportListViewAdapter;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.model.FresFit;
import com.cambro.app.model.Transport;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportProductFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    String  category;
    private ImageView tpp_iv_category;
    private TextView    tpp_txt_category;
    private ImageView tpp_iv_electric;
    private TextView tpp_txt_electric;
    private ImageView tpp_iv_pans;
    private TextView tpp_txt_pans;
    private ImageView tpp_iv_deep;
    private TextView tpp_txt_deep;
    private ListView tpp_lst_prd;
    private String sDeep;
    private String sPan, sPanOfDB;
    private boolean isElectric;
    private ArrayList<Transport> lstTransPrd;
    private LinearLayout tpp_ll_electric, tpp_ll_pan;
    private View tpp_v_empty;

    public TransportProductFragment() {
        // Required empty public constructor
    }

    public static TransportProductFragment newInstance() {
        TransportProductFragment f = new TransportProductFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_transport_prd, container, false);

        category = ((TransportActivity) getActivity()).getCategory();
        isElectric = ((TransportActivity) getActivity()).isElectric();
        tpp_iv_category = (ImageView) v.findViewById(R.id.tpp_iv_category);
        tpp_txt_category = (TextView) v.findViewById(R.id.tpp_txt_category);
        tpp_iv_electric = ((ImageView) v.findViewById(R.id.tpp_iv_electric));
        tpp_txt_electric = (TextView) v.findViewById(R.id.tpp_txt_electric);
        tpp_lst_prd = (ListView) v.findViewById(R.id.tpp_lst_prd);

        tpp_iv_pans = ((ImageView) v.findViewById(R.id.tpp_iv_pans));
        tpp_txt_pans = (TextView) v.findViewById(R.id.tpp_txt_pans);
        sPan = ((TransportActivity) getActivity()).getPans();
        Log.e("pan", sPan);
        tpp_ll_electric = (LinearLayout) v.findViewById(R.id.tpp_ll_electric);
        tpp_ll_pan = (LinearLayout) v.findViewById(R.id.tpp_ll_pan);
        tpp_v_empty = (View) v.findViewById(R.id.tpp_v_empty);

        if (category.equals("Food")) {

            tpp_iv_category.setImageDrawable(getResources().getDrawable(R.drawable.tp_food_pan));
            tpp_txt_category.setText(getString(R.string.tp_txt_tfp));
            tpp_ll_electric.setVisibility(View.VISIBLE);
            tpp_ll_pan.setVisibility(View.VISIBLE);
            tpp_v_empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.5f));
            if (isElectric) {
                tpp_iv_electric.setImageDrawable(getResources().getDrawable(R.drawable.tp_electric));
                tpp_txt_electric.setText(getString(R.string.tp_txt_electric));
            } else {
                tpp_iv_electric.setImageDrawable(getResources().getDrawable(R.drawable.tp_non_electric));
                tpp_txt_electric.setText(getString(R.string.tp_txt_non_electric));
            }

            if (sPan.equals(getString(R.string.tp_txt_single_pan))) {
                tpp_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_single_pan));
                tpp_txt_pans.setText(getString(R.string.tp_txt_single_pan));
                sPanOfDB = getString(R.string.tp_txt_sfp);
            } else if (sPan.equals(getString(R.string.tp_txt_multiple_pan))) {
                tpp_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_multiple_pan));
                tpp_txt_pans.setText(getString(R.string.tp_txt_multiple_pan));
                sPanOfDB = getString(R.string.tp_txt_mp);
            } else if (sPan.equals(getString(R.string.tp_txt_sheet_pan))) {
                tpp_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_sheet_pan));
                tpp_txt_pans.setText(getString(R.string.tp_txt_sheet_pan));
                sPanOfDB = getString(R.string.tp_txt_mpsp);
            }
            else if (sPan.equals(getString(R.string.tp_txt_food_store_boxes))) {
                tpp_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_food_storage_boxes));
                tpp_txt_pans.setText(getString(R.string.tp_txt_food_store_boxes));
                sPanOfDB = getString(R.string.tp_txt_food_store_boxes_db);
            }else if (sPan.equals(getString(R.string.tp_txt_pan_sheetpan))) {
                tpp_iv_pans.setImageDrawable(getResources().getDrawable(R.drawable.tp_pan_sheet));
                tpp_txt_pans.setText(getString(R.string.tp_txt_pan_sheetpan).toUpperCase());
                sPanOfDB = getString(R.string.tp_txt_foodpan_sheetpan_db);
            }
        } else {
            tpp_iv_category.setImageDrawable(getResources().getDrawable(R.drawable.tp_beverage1));
            tpp_txt_category.setText(getString(R.string.tp_txt_beverage));
            tpp_ll_electric.setVisibility(View.GONE);
            tpp_ll_pan.setVisibility(View.GONE);
            tpp_v_empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2.5f));
        }

        sDeep = ((TransportActivity) getActivity()).getDeep();
        Log.e("deep", sDeep);
        tpp_iv_deep = ((ImageView) v.findViewById(R.id.tpp_iv_deep));
        tpp_txt_deep = (TextView) v.findViewById(R.id.tpp_txt_deep);
        tpp_txt_deep.setText(sDeep);

        if(sDeep.equals(getString(R.string.tp_txt_15))) {

            tpp_iv_deep.setImageDrawable(getResources().getDrawable(R.drawable.tp_1_5));
            tpp_txt_deep.setText(getString(R.string.tp_txt_15));

        }else if(sDeep.equals(getString(R.string.tp_txt_375))) {

            tpp_iv_deep.setImageDrawable(getResources().getDrawable(R.drawable.tp_3_75));
            tpp_txt_deep.setText(getString(R.string.tp_txt_375));

        }else if(sDeep.equals(getString(R.string.tp_txt_10))) {

            tpp_iv_deep.setImageDrawable(getResources().getDrawable(R.drawable.tp_10));
            tpp_txt_deep.setText(getString(R.string.tp_txt_10));

        }else {

            if (sDeep != null && sDeep.length() > 0)
            {
                v.findViewById(R.id.tpp_ll_deep).setVisibility(View.VISIBLE);
                boolean isSmall = ((TransportActivity)getActivity()).isSmallDeep();
                if (isSmall)
                {
                    tpp_iv_deep.setImageDrawable(getResources().getDrawable(R.drawable.tp_electric_multi_pans_small_carriers));
                }
                else
                {
                    tpp_iv_deep.setImageDrawable(getResources().getDrawable(R.drawable.tp_electric_mp_larger_cart));
                }
            }
            else
            {
                v.findViewById(R.id.tpp_ll_deep).setVisibility(View.INVISIBLE);
            }
            tpp_txt_deep.setText(sDeep.toUpperCase());

        }
        getTranspost();
        return v;
    }

    private void getTranspost() {
        if (lstTransPrd != null && lstTransPrd.size() > 0) return;
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Loading...");
        dlg.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("BuildATransporter");
        query.whereEqualTo("Category", category);
        sDeep = sDeep.replace("ALLONS","al.").replace("TO","to");
        query.whereEqualTo("Deep", sDeep);
        if (category.equals("Food")) {
            query.whereEqualTo("Electric", isElectric == true ? "Electric" : "Non-Electric");
            query.whereEqualTo("Pan", sPanOfDB);
        }
        query.orderByAscending("ProductDescription");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {

                        lstTransPrd = new ArrayList<Transport>();
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject fruit = list.get(i);

                            Transport trans = new Transport();
                            trans.setObjectId(fruit.getObjectId());
                            trans.setCategory(fruit.getString("Category"));
                            trans.setDeep(fruit.getString("Deep"));
                            trans.setElectric(fruit.getString("Electric"));
                            trans.setPan(fruit.getString("Pan"));
                            trans.setProductCode(fruit.getString("ProductCode"));
                            String s = fruit.getString("ProductDescription");
                            if (s != null)
                            {
                                s = s.replace("_12_", getString(R.string.ff_txt_sub_12));
                                s = s.replace("_34_", getString(R.string.ff_txt_sub_34));
                                s = s.replace("_14_", getString(R.string.ff_txt_sub_14));
                            }
                            else
                                s = "";

                            trans.setProductDescription(s);
                            trans.setProductImageName(fruit.getString("ProductImageName"));
                            lstTransPrd.add(trans);
                        }
                        tpp_lst_prd.setAdapter(new TransportListViewAdapter(getActivity(), TransportProductFragment.this, lstTransPrd));
                    }
                } else {
                    Log.e("Transport", e.toString());
                }
                dlg.dismiss();
            }
        });
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
