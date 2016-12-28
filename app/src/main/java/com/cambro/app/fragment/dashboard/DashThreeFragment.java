package com.cambro.app.fragment.dashboard;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.YouTubeActivity;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashThreeFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public DashThreeFragment() {
        // Required empty public constructor
    }

    public static DashThreeFragment newInstance() {
        DashThreeFragment f = new DashThreeFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_dashthree, container, false);
        v.findViewById(R.id.db_iv_blog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSocialWebview(2);
            }
        });
        v.findViewById(R.id.db_iv_cweb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getResources().getString(R.string.dlg_open_blog))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse(Common.CambroWebSite);
                                Intent launchIntent = new Intent(Intent.ACTION_VIEW, uri);
                                getActivity().startActivity(launchIntent);
                                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null).show();
            }
        });
        return v;
    }

    private void loadSocialWebview(int param)
    {
        Intent i = new Intent(getActivity(), SocialActivity.class);
        i.putExtra("social", param);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
