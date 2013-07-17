package com.example.magicball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class AfterVoiceActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.after_voice);
	    ImageView ball = (ImageView)findViewById(R.id.ball);
	    
	}

}
