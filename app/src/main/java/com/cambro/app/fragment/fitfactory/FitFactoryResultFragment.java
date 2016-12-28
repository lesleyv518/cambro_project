package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
import android.graphics.drawable.Drawable;
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
import com.cambro.app.FitFactoryActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitFactoryResultFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    TextView ffr_txt_gaqs;
    TextView ffr_txt_prdcription;
    TextView ffr_txt_ml;
    TextView ffr_txt_lidcode;
    TextView ffr_txt_title;
    TextView ffr_txt_lidname;
    TextView ffr_txt_size;
    TextView ffr_txt_startover;
    ImageView ffr_iv_icon,ffr_iv_fit, ffr_iv_lid;

    LinearLayout ffr_ll_title;

    FresFit selectedFit, selectedLid;
    String category;

    public FitFactoryResultFragment() {
        // Required empty public constructor
    }

    public static FitFactoryResultFragment newInstance() {
        FitFactoryResultFragment f = new FitFactoryResultFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory_result, container, false);

        ffr_ll_title = (LinearLayout)v.findViewById(R.id.ffr_ll_title);
        ffr_iv_icon = (ImageView) v.findViewById(R.id.ffr_iv_icon);
        ffr_txt_title = (TextView) v.findViewById(R.id.ffr_txt_title);
        ffr_txt_size = (TextView) v.findViewById(R.id.ffr_txt_size);

        ffr_iv_fit = (ImageView) v.findViewById(R.id.ffr_iv_fit);
        ffr_iv_lid = (ImageView) v.findViewById(R.id.ffr_iv_lid);

        ffr_txt_prdcription = (TextView) v.findViewById(R.id.ffr_txt_prdcription);
        ffr_txt_ml = (TextView) v.findViewById(R.id.ffr_txt_ml);
        ffr_txt_lidcode = (TextView) v.findViewById(R.id.ffr_txt_lidcode);
        ffr_txt_lidname = (TextView) v.findViewById(R.id.ffr_txt_lidname);

        ffr_txt_startover = (TextView) v.findViewById(R.id.ffr_txt_startover);
        ffr_txt_startover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLid = null;
                ((FitFactoryActivity)getActivity()).setSelectedLid(null);
                onButtonPressed("0", false);
            }
        });

        ffr_txt_gaqs = (TextView) v.findViewById(R.id.ffr_txt_gaqs);
        ffr_txt_gaqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("6", true);
            }
        });

        v.findViewById(R.id.ffr_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLid == null)
                {
                    if (category.equals("DisposableLids"))
                        onButtonPressed("1", false);
                    else if(category.equals("Healthcare"))
                        onButtonPressed("7", false);
                    else if(category.equals("TranslucentFoodPans") || category.equals("CamwearPans") || category.equals("HighHeatFoodPans"))
                        onButtonPressed("8", false);
                    else if(category.equals("CamSquares") || category.equals("CamwearRounds"))
                        onButtonPressed("9", false);
                }
                else
                {
                    selectedLid = null;
                    ((FitFactoryActivity)getActivity()).setSelectedLid(null);
                    onButtonPressed("4", false);
                }
            }
        });

        category = ((FitFactoryActivity)getActivity()).getCategory();
        initUIByCategory();

        return v;
    }

    private void initUIByCategory()
    {
        selectedFit = ((FitFactoryActivity)getActivity()).getSelectedFit();
        Utils.loadFitImageByDrawableName(getActivity(), selectedFit, ffr_iv_fit);
        ffr_txt_prdcription.setText(selectedFit.getProductDescription().replace("_R_", getString(R.string.ff_txt_srt)));

        //Lid Drawable
        selectedLid = ((FitFactoryActivity)getActivity()).getSelectedLid();
        if(selectedLid != null){
            ffr_txt_lidcode.setText(selectedLid.getLidCode());
            Utils.loadFitImageByDrawableName(getActivity(), selectedLid, ffr_iv_lid);
        }

        if (category.equals("DisposableLids"))
        {
            ffr_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_thumbler));
            ffr_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.tumbler_icon));
            ffr_txt_title.setText(getString(R.string.ff_txt_thumbler));
            ffr_txt_title.setTextColor(getResources().getColor(R.color.black));
            //ffr_txt_lidname.setText(category);
            ffr_txt_ml.setText(selectedFit.getMl() + " ML");
            ffr_txt_size.setVisibility(View.GONE);
        }
        else if(category.equals("Healthcare"))
        {
            ffr_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_healthcare));
            ffr_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.healthcare_icon));
            ffr_txt_title.setText(getString(R.string.ff_txt_healthcare));
            ffr_txt_title.setTextColor(getResources().getColor(R.color.white));
            //ffr_txt_lidname.setText(category);
            ffr_txt_ml.setText(selectedFit.getMl() + " ML");
            ffr_txt_size.setVisibility(View.GONE);
        }
        else if(category.equals("TranslucentFoodPans") || category.equals("CamwearPans") || category.equals("HighHeatFoodPans")) {
            ffr_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_pans));
            ffr_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.pan_icon));
            ffr_txt_title.setText(getString(R.string.ff_txt_pans));
            ffr_txt_title.setTextColor(getResources().getColor(R.color.white));
            ffr_txt_ml.setText(selectedFit.getSize());
            String pSize = selectedFit.getPanDepth().replace("_12_", getString(R.string.ff_txt_sub_12)) + "/" + selectedFit.getMetricPanDepth();
            ffr_txt_size.setText(pSize);
            ffr_txt_size.setVisibility(View.VISIBLE);
            //ffr_txt_lidname.setText(selectedLid.getLidDescription().trim());
        }
        else if(category.equals("CamSquares") || category.equals("CamwearRounds")) {
            ffr_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_storage));
            ffr_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.storage_icon));
            ffr_txt_title.setText(getString(R.string.ff_txt_storage));
            ffr_txt_title.setTextColor(getResources().getColor(R.color.black));
            ffr_txt_ml.setText(selectedFit.getQt());
            ffr_txt_size.setVisibility(View.GONE);
            //ffr_txt_lidname.setText(selectedLid.getLidDescription().trim());
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
