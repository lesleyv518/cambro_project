package com.cambro.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.cambro.app.R;


public class SpalshActivity extends Activity {

    private final int SPALSH_DISPLAY_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SpalshActivity.this, SocialActivity.class);
                SpalshActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                SpalshActivity.this.finish();
            }
        }, SPALSH_DISPLAY_TIME);
    }

}
