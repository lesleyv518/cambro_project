package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
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
import com.cambro.app.FitFactoryActivity;
import com.cambro.app.adapter.FitFacotyLidListViewAdapter;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitFactoryLidFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<FresFit> lstLids = new ArrayList<FresFit>();

    ListView ffl_lst_lid;
    ImageView ffl_iv_icon,ffl_iv_img;
    TextView ffl_txt_title, ffl_txt_family, ffl_txt_capacity, ffl_txt_startover, ffl_txt_size;
    FresFit seletedFit;
    String category;
    LinearLayout ffl_ll_title;

    public FitFactoryLidFragment() {
        // Required empty public constructor
    }

    public static FitFactoryLidFragment newInstance() {
        FitFactoryLidFragment f = new FitFactoryLidFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory_lid, container, false);

        ffl_ll_title = (LinearLayout)v.findViewById(R.id.ffl_ll_title);
        ffl_iv_icon = (ImageView) v.findViewById(R.id.ffl_iv_icon);
        ffl_txt_title = (TextView) v.findViewById(R.id.ffl_txt_title);
        ffl_txt_size = (TextView) v.findViewById(R.id.ffl_txt_size);

        ffl_iv_img = (ImageView) v.findViewById(R.id.ffl_iv_img);
        ffl_lst_lid = (ListView) v.findViewById(R.id.ffl_lst_lid);

        ffl_txt_family = (TextView) v.findViewById(R.id.ffl_txt_family);
        ffl_txt_capacity = (TextView) v.findViewById(R.id.ffl_txt_capacity);
        ffl_txt_startover = (TextView) v.findViewById(R.id.ffl_txt_startover);

        v.findViewById(R.id.ffl_txt_startover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seletedFit = null;
                ((FitFactoryActivity)getActivity()).setSelectedLid(null);
                ((FitFactoryActivity)getActivity()).setSelectedFit(null);
                ((FitFactoryActivity)getActivity()).setLstLids(null);
                onButtonPressed("0", false);
            }
        });


        v.findViewById(R.id.ffl_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seletedFit = null;
                ((FitFactoryActivity)getActivity()).setSelectedLid(null);
                ((FitFactoryActivity)getActivity()).setSelectedFit(null);
                ((FitFactoryActivity)getActivity()).setLstLids(null);

                if(category.equals("DisposableLids"))
                    onButtonPressed("1", false);
                else if(category.equals("Healthcare"))
                    onButtonPressed("7", false);
                else if(category.equals("TranslucentFoodPans") || category.equals("CamwearPans") || category.equals("HighHeatFoodPans"))
                    onButtonPressed("8", false);
                else if(category.equals("CamSquares") || category.equals("CamwearRounds"))
                    onButtonPressed("9", false);
            }
        });

        category = ((FitFactoryActivity)getActivity()).getCategory();

        initLoad();

        return v;
    }


    private void initUIByCategory()
    {
        seletedFit = ((FitFactoryActivity)getActivity()).getSelectedFit();
        ffl_txt_family.setText(seletedFit.getProductDescription().replace("_R_", getResources().getString(R.string.ff_txt_srt)));
        Utils.loadFitImageByDrawableName(getActivity(), seletedFit, ffl_iv_img);
        if (category.equals("DisposableLids"))
        {
            ffl_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_thumbler));
            ffl_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.tumbler_icon));
            ffl_txt_title.setText(getString(R.string.ff_txt_thumbler));
            ffl_txt_title.setTextColor(getResources().getColor(R.color.black));
            ffl_txt_capacity.setText(seletedFit.getMl() + " ML");
        }
        else if(category.equals("Healthcare"))
        {
            ffl_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_healthcare));
            ffl_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.healthcare_icon));
            ffl_txt_title.setText(getString(R.string.ff_txt_healthcare));
            ffl_txt_title.setTextColor(getResources().getColor(R.color.white));
            ffl_txt_capacity.setText(seletedFit.getMl() + " ML");
        }
        else if(category.equals("TranslucentFoodPans") || category.equals("CamwearPans") || category.equals("HighHeatFoodPans"))
        {
            ffl_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_pans));
            ffl_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.pan_icon));
            ffl_txt_title.setText(getString(R.string.ff_txt_pans));
            ffl_txt_title.setTextColor(getResources().getColor(R.color.white));
            ffl_txt_capacity.setText(seletedFit.getSize());
            String pName = seletedFit.getPanDepth() + "/" + seletedFit.getMetricPanDepth();
            ffl_txt_size.setText(pName);
            ffl_txt_size.setVisibility(View.VISIBLE);
        }
        else if(category.equals("CamSquares") || category.equals("CamwearRounds"))
        {
            ffl_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_storage));
            ffl_iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.storage_icon));
            ffl_txt_title.setText(getString(R.string.ff_txt_storage));
            ffl_txt_title.setTextColor(getResources().getColor(R.color.black));
            ffl_txt_capacity.setText(seletedFit.getQt());
        }
    }

    private void initLoad()
    {
        initUIByCategory();
        List<FresFit> lst = ((FitFactoryActivity)getActivity()).getLstLids();
        if(lst.size() > 0)
            ffl_lst_lid.setAdapter(new FitFacotyLidListViewAdapter(getActivity(), FitFactoryLidFragment.this, lst));
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
