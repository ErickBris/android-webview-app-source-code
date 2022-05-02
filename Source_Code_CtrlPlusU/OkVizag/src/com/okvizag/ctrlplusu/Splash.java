package com.okvizag.ctrlplusu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);
        
        final Handler handel = new Handler();
        handel.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent loadSplash = new Intent(Splash.this, MainActivity.class);
				
				startActivity(loadSplash);
				
				finish();
			}
		}, 3000);
    }
}