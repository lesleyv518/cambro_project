package com.cambro.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.fragment.DashboardFragment;
import com.cambro.app.interfce.Common;
import com.cambro.app.adapter.ViewPagerFitFactoryAdapter;

public class SocialActivity extends Activity {

    ViewPager pager;
    private String titles[];
    ViewPagerFitFactoryAdapter adp;

    WebView web;
    ProgressBar progressBar;
    String url = "";
    private int position = 0;
    TextView tv;
    WebView webView;
    ImageView wv_iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        tv = (TextView) findViewById(R.id.wv_txt_title);
        webView = (WebView) findViewById(R.id.wv_wv);
        wv_iv_back = (ImageView) findViewById(R.id.wv_iv_back);
        wv_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

        Intent i = getIntent();
        position = i.getIntExtra("social",0);
        String link = i.getStringExtra("link");
        String title = i.getStringExtra("title");
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
            case 11:
                url = DashboardFragment.lastBlogLink; tv.setText("CAMBRO BLOG");break;
            case 12:
                url = Common.FeedQuestion; tv.setText("Contact Us");break;
            case 13:
                url = link; tv.setText(title);break;
        }

        loadWebview(webView, url);

    }

    public void loadWebview(View v, String url)
    {
        web = (WebView) v.findViewById(R.id.wv_wv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

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
}
