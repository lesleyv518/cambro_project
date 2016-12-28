package com.cambro.app;

import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailView;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class YouTubeActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener, View.OnTouchListener{

	public static String VIDEO_ID1 = "9QMB0TtCWkQ";
	public static String VIDEO_ID2 = "b_7rfet7b8U";
	public static String VIDEO_ID3 = "8ReyYuFoyEM";
	
	private YouTubePlayer youTubePlayer;
	private YouTubePlayerView yp;
	private YouTubeThumbnailView yt1, yt2, yt3;
	private YouTubeThumbnailLoader youTubeThumbnailLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

		yp = (YouTubePlayerView) findViewById(R.id.youtubeplayerview);
		yp.initialize(Common.Google_Api_key, this);

		findViewById(R.id.vd_iv_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (youTubePlayer != null) youTubePlayer.release();
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});

		findViewById(R.id.vd_sc).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(youTubePlayer.isPlaying()) youTubePlayer.pause();
				return false;
			}
		});

		findViewById(R.id.vd_iv_loc).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(YouTubeActivity.this, SocialActivity.class);
				i.putExtra("social", 3);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});

		int height = dpToPx(250);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
        yp.setLayoutParams(param);
        
        yt1 = (YouTubeThumbnailView)findViewById(R.id.youtubethumbnailview);
        yt1.initialize(Common.Google_Api_key, new YouTubeThumbnailView.OnInitializedListener() {
			
			@Override
			public void onInitializationSuccess(YouTubeThumbnailView arg0,
					YouTubeThumbnailLoader arg1) {
				youTubeThumbnailLoader = arg1;
				arg1.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
					@Override
					public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
						youTubeThumbnailView.setVisibility(View.VISIBLE);
						findViewById(R.id.layout1).setVisibility(View.GONE);
					}

					@Override
					public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, ErrorReason errorReason) {

					}
				});
				youTubeThumbnailLoader.setVideo(VIDEO_ID1);
			}
			
			@Override
			public void onInitializationFailure(YouTubeThumbnailView arg0,
					YouTubeInitializationResult arg1) {
				arg0.setImageDrawable(getResources().getDrawable(R.drawable.no_thumbnail));
			}
		});
        
        yt1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(youTubePlayer != null){
					
					((LinearLayout)findViewById(R.id.layout_youtube)).removeView(yp);
					yp.setVisibility(View.VISIBLE);
					((LinearLayout)findViewById(R.id.layout_youtube)).addView(yp, 1);
					yt1.setVisibility(View.GONE);
					yt2.setVisibility(View.VISIBLE);
					yt3.setVisibility(View.VISIBLE);
					youTubePlayer.cueVideo(VIDEO_ID1);
				}
			}});
        
        yt2 = (YouTubeThumbnailView)findViewById(R.id.youtubethumbnailview1);
        yt2.initialize(Common.Google_Api_key, new YouTubeThumbnailView.OnInitializedListener() {
			
			@Override
			public void onInitializationSuccess(YouTubeThumbnailView arg0,
					YouTubeThumbnailLoader arg1) {
				
				youTubeThumbnailLoader = arg1;
				arg1.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
					@Override
					public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
						youTubeThumbnailView.setVisibility(View.VISIBLE);
						findViewById(R.id.layout2).setVisibility(View.GONE);
					}

					@Override
					public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, ErrorReason errorReason) {

					}
				});
				youTubeThumbnailLoader.setVideo(VIDEO_ID2);

			}
			
			@Override
			public void onInitializationFailure(YouTubeThumbnailView arg0,
					YouTubeInitializationResult arg1) {
				// TODO Auto-generated method stub
				arg0.setImageDrawable(getResources().getDrawable(R.drawable.no_thumbnail));
			}
		});
        yt2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(youTubePlayer != null){
					((LinearLayout)findViewById(R.id.layout_youtube)).removeView(yp);
					yp.setVisibility(View.VISIBLE);
					((LinearLayout)findViewById(R.id.layout_youtube)).addView(yp, 4);
					yt1.setVisibility(View.VISIBLE);
					yt2.setVisibility(View.GONE);
					yt3.setVisibility(View.VISIBLE);
					youTubePlayer.cueVideo(VIDEO_ID2);
				}
			}});
        
        yt3 = (YouTubeThumbnailView)findViewById(R.id.youtubethumbnailview2);
        yt3.initialize(Common.Google_Api_key, new YouTubeThumbnailView.OnInitializedListener() {
			
			@Override
			public void onInitializationSuccess(YouTubeThumbnailView arg0,
					YouTubeThumbnailLoader arg1) {
				
				youTubeThumbnailLoader = arg1;
				arg1.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
					@Override
					public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
						youTubeThumbnailView.setVisibility(View.VISIBLE);
						findViewById(R.id.layout3).setVisibility(View.GONE);
					}
					@Override
					public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, ErrorReason errorReason) {

					}
				});
				youTubeThumbnailLoader.setVideo(VIDEO_ID3);
				
			}
			
			@Override
			public void onInitializationFailure(YouTubeThumbnailView arg0,
					YouTubeInitializationResult arg1) {
				arg0.setImageDrawable(getResources().getDrawable(R.drawable.no_thumbnail));
			}
		});
        yt3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(youTubePlayer != null){
					((LinearLayout)findViewById(R.id.layout_youtube)).removeView(yp);
					yp.setVisibility(View.VISIBLE);
					((LinearLayout)findViewById(R.id.layout_youtube)).addView(yp, 7);
					yt1.setVisibility(View.VISIBLE);
					yt3.setVisibility(View.GONE);
					yt2.setVisibility(View.VISIBLE);
					youTubePlayer.cueVideo(VIDEO_ID3);

				}
			}});
                
    }

	private int dpToPx(int dp) {
		return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Toast.makeText(getApplicationContext(),
				"YouTubePlayer.onInitializationFailure()",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,
			boolean wasRestored) {
		
		youTubePlayer = player;
		if (!wasRestored) {
		      player.cueVideo(VIDEO_ID1);
		    }
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == R.id.youtubeplayerview) return false;
		return true;
	}

	private final class ThumbnailLoadedListener implements
    YouTubeThumbnailLoader.OnThumbnailLoadedListener {

		@Override
		public void onThumbnailError(YouTubeThumbnailView arg0, ErrorReason arg1) {

		}

		@Override
		public void onThumbnailLoaded(YouTubeThumbnailView arg0, String arg1) {

		}

	}

	@Override
	public void onBuffering(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPaused() {
		// TODO Auto-generated method stub
		Toast.makeText(YouTubeActivity.this,
				"Paused",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onPlaying() {
		// TODO Auto-generated method stub
		Toast.makeText(YouTubeActivity.this,
				"Stoped",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSeekTo(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopped() {
		// TODO Auto-generated method stub
		Toast.makeText(YouTubeActivity.this,
				"Stoped",
				Toast.LENGTH_LONG).show();
	}

}
