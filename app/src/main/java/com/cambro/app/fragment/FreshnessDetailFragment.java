package com.cambro.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.Utils;
import com.cambro.app.utils.image.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreshnessDetailFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    FresFit fresFit;
    TextView frd_txt_name,frd_txt_temp,frd_txt_humi,frd_txt_ctd,frd_txt_tips;
    TextView frd_txt_prdsubject;
    ImageView frd_iv_pi,frd_iv_ssci;
    ImageLoader imageLoader = new ImageLoader(getActivity());

    public FreshnessDetailFragment() {
        // Required empty public constructor
    }

    public static FreshnessDetailFragment newInstance() {
        FreshnessDetailFragment f = new FreshnessDetailFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_freshness_d, container, false);

        frd_txt_name = (TextView) v.findViewById(R.id.frd_txt_name);
        frd_txt_temp = (TextView) v.findViewById(R.id.frd_txt_temp);
        frd_txt_humi = (TextView) v.findViewById(R.id.frd_txt_humi);
        frd_txt_ctd = (TextView) v.findViewById(R.id.frd_txt_ctd);
        frd_txt_tips = (TextView) v.findViewById(R.id.frd_txt_tips);
        frd_txt_prdsubject = (TextView) v.findViewById(R.id.frd_txt_prdsubject);

        frd_iv_pi = (ImageView) v.findViewById(R.id.frd_iv_pi);
        frd_iv_ssci = (ImageView) v.findViewById(R.id.frd_iv_ssci);

        v.findViewById(R.id.frd_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("14", false);
            }
        });

        v.findViewById(R.id.frd_txt_seemore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 13);
                i.putExtra("link", fresFit.getLink());
                i.putExtra("title", fresFit.getName());
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        loadFreshnessDetail();
        return v;
    }

    private void loadFreshnessDetail()
    {
        if(Reg.selectedFresh.equals("Fruit"))
        {
            fresFit = Reg.selectedFruit;
        }
        else if(Reg.selectedFresh.equals("Vegetable"))
        {
            fresFit = Reg.selectedVeg;
        }

        frd_txt_name.setText(fresFit.getName());
        String temperature = "";
        String[] sTemp = fresFit.getTemperature().split("\n");
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        if(sTemp.length ==1) {
            frd_txt_temp.setTextSize(getResources().getDimension(R.dimen.font_textview1)/metrics.density);
            frd_txt_humi.setTextSize(getResources().getDimension(R.dimen.font_textview1) / metrics.density);
            temperature = sTemp[0] + getResources().getString(R.string.frs_txt_degree) + "F";
            temperature = fresFit.getTemperature().replace("_D_", getString(R.string.frs_txt_degree));
        }
        else
        {
            temperature = fresFit.getTemperature().replace("_D_", getString(R.string.frs_txt_degree));
            frd_txt_temp.setTextSize(getResources().getDimension(R.dimen.font_textview2) / metrics.density);
            frd_txt_humi.setTextSize(getResources().getDimension(R.dimen.font_textview2) / metrics.density);
        }
        frd_txt_temp.setText(temperature);
        frd_txt_humi.setText(fresFit.getHumidity());
        String subject = fresFit.getContainer().split("\n")[0];
        frd_txt_prdsubject.setText(subject);
        frd_txt_ctd.setText(fresFit.getContainer().replace(subject+"\n","").replace("_D_", getString(R.string.frs_txt_degree)));
        frd_txt_tips.setText(fresFit.getTip().replace("_D_", getString(R.string.frs_txt_degree)));

        String s = fresFit.getImage().getName().toLowerCase().trim();
        Utils.loadFreshImageByDrawableName(getActivity(), frd_iv_pi, s);
        String imgContainer = fresFit.getProductImage().replace(".jpg","").toLowerCase();
        try {
            Drawable drawable = getResources().getDrawable(getResources()
                    .getIdentifier("fresh_" + imgContainer, "drawable", getActivity().getPackageName()));
            frd_iv_ssci.setImageDrawable(drawable);
        }catch (Exception e)
        {
            frd_iv_ssci.setImageDrawable(getResources().getDrawable(R.drawable.trans));
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
