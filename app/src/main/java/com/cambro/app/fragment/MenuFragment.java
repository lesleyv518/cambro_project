package com.cambro.app.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cambro.app.DashboardActivity;
import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        MenuFragment f = new MenuFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment mnu_txt_so
        final View rootview = inflater.inflate(R.layout.fragment_menu, container, false);
        final ParseUser user = ParseUser.getCurrentUser();
        if (user != null) {
            rootview.findViewById(R.id.mnu_ll_login).setVisibility(View.GONE);
            rootview.findViewById(R.id.mnu_ll_signout).setVisibility(View.VISIBLE);
            SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
            String fullname = prefs.getString("fullname", "");
            ((TextView) rootview.findViewById(R.id.mnu_txt_not)).setText("Not " + fullname.split(" ")[0]);
        }
        if (Reg.facebookId != null || Reg.twitterId != null) {
            rootview.findViewById(R.id.mnu_ll_login).setVisibility(View.GONE);
            rootview.findViewById(R.id.mnu_ll_signout).setVisibility(View.VISIBLE);

            SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
            String fullname = prefs.getString("fullname", "");
            ((TextView) rootview.findViewById(R.id.mnu_txt_not)).setText("Not " + fullname.split(" ")[0]);
        }
        rootview.findViewById(R.id.mnu_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("10", false);
            }
        });

        rootview.findViewById(R.id.mnu_iv_loc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 3);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        rootview.findViewById(R.id.mnu_ll_fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getResources().getString(R.string.dlg_open_FB))
                        .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onButtonPressed("100", true);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null).show();
            }
        });

        rootview.findViewById(R.id.mnu_ll_tw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getResources().getString(R.string.dlg_open_tw))
                        .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onButtonPressed("200", true);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null).show();
            }
        });

        rootview.findViewById(R.id.mnu_ll_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity) getActivity()).whereFromLogin = getResources().getString(R.string.mnu);
                onButtonPressed("1", true);
            }
        });
        rootview.findViewById(R.id.mnu_ll_ca).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity) getActivity()).whereFromLogin = getResources().getString(R.string.mnu);
                onButtonPressed("2", true);
            }
        });

        rootview.findViewById(R.id.mnu_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.mnu_wc)});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "");

                getActivity().startActivity(Intent.createChooser(i, "Send Email"));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
        rootview.findViewById(R.id.mnu_txt_fq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 12);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        rootview.findViewById(R.id.mnu_ll_cblog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 2);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        rootview.findViewById(R.id.mnu_ll_cweb).setOnClickListener(new View.OnClickListener() {
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

        rootview.findViewById(R.id.mnu_txt_so).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                if(token != null)
                {
                    LoginManager manager = LoginManager.getInstance();
                    manager.logOut();
                }
                ParseUser.logOut();

                rootview.findViewById(R.id.mnu_ll_login).setVisibility(View.VISIBLE);
                rootview.findViewById(R.id.mnu_ll_signout).setVisibility(View.GONE);
            }
        });

        rootview.findViewById(R.id.mnu_txt_ya_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("7", true);
            }
        });

        return rootview;
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
