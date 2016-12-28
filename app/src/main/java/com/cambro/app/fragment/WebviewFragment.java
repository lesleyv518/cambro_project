package com.cambro.app.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebviewFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private OnFragmentInteractionListener mListener;
    WebView web;
    ProgressBar progressBar;
    String url = "";
    private int position = 0;

    public WebviewFragment() {
        // Required empty public constructor
    }

    public static WebviewFragment newInstance(int position) {
        WebviewFragment f = new WebviewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        position = getArguments().getInt(ARG_POSITION);

        View v = inflater.inflate(R.layout.activity_webview, container, false);
        v.findViewById(R.id.wv_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < 8)
                    onButtonPressed("4", false);
                else
                    onButtonPressed("10", false);
            }
        });
        TextView tv = (TextView) v.findViewById(R.id.wv_txt_title);

        switch (position)
        {
            case 0:
                url = Common.StoreSafe; tv.setText("StoreSafe");break;
            case 1:
                url = Common.Products; tv.setText("Products");break;
            case 2:
                url = Common.Blog; tv.setText("Blog");break;
            case 3:
                url = Common.WhereToBuy; tv.setText("WhereToBuy");break;
            case 4:
                url = Common.Facebook; tv.setText("Facebook");break;
            case 5:
                url = Common.Twitter; tv.setText("Twitter");break;
            case 6:
                url = Common.Youtube; tv.setText("Youtube");break;
            case 7:
                url = Common.LinkedIn; tv.setText("LinkedIn");break;
            case 8:
                url = Common.Pinterest; tv.setText("Pinterest");break;
            case 9:
                url = Common.Instagram; tv.setText("Instagram");break;
            case 10:
                url = Common.Google; tv.setText("Google+");break;
        }

        loadWebview(v, url);
        return v;
    }

    public void loadWebview(View v, String url)
    {
        web = (WebView) v.findViewById(R.id.wv_wv);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);

        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(url);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            web.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
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
