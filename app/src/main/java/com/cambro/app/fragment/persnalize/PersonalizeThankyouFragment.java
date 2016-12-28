package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cambro.app.FitFactoryActivity;
import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeThankyouFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    ImageView pst_iv_fb_share, pst_iv_email_blue, pst_iv_download;
    private String category;

    public PersonalizeThankyouFragment() {
        // Required empty public constructor
    }

    public static PersonalizeThankyouFragment newInstance() {
        PersonalizeThankyouFragment f = new PersonalizeThankyouFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_personalize_thankyou, container, false);
        category = ((PersonalizeToolActivity) getActivity()).getPsCategory().getCategory();
        v.findViewById(R.id.pst_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("9", false);
            }
        });

        v.findViewById(R.id.pst_txt_gh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("9", false);
            }
        });

        pst_iv_fb_share = (ImageView) v.findViewById(R.id.pst_iv_fb_share);
        pst_iv_fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.fbSharePhoto(getActivity(), PersonalizeThankyouFragment.this);
            }
        });

        pst_iv_email_blue = (ImageView) v.findViewById(R.id.pst_iv_email_blue);
        pst_iv_email_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.sendDefaultEmail(getActivity(), category, "");
            }
        });
        pst_iv_download = (ImageView) v.findViewById(R.id.pst_iv_download);
        pst_iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.saveImageToInternalStorage(Reg.ptLastImage);
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
