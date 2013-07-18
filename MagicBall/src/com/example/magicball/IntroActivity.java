package com.example.magicball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class IntroActivity extends Activity {

	Handler handler = new Handler();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		handler.postDelayed(new splashHandler(), 2000);
		// TODO Auto-generated method stub
	}

	class splashHandler implements Runnable {

		public void run() {
			Intent i = new Intent(IntroActivity.this, MainActivity.class);
			startActivity(i);
			finish();
			
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			
		}
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		handler.removeCallbacks(new splashHandler());
	}

}
