package com.okvizag.ctrlplusu;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import java.util.List;

import com.pushbots.push.Pushbots;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import fr.nicolaspomepuy.discreetapprate.AppRate;


public class MainActivity extends ActionBarActivity implements OnRefreshListener, OnClickListener {

	private WebView mWebView;
	private PullToRefreshLayout mPullToRefreshLayout;
	private ProgressBar progress;
	private static final String TAG = "MainActivity";
	
	//This is your Main Activity, you have to set your Main Default URL here.
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Push Notification Call
		Pushbots.sharedInstance().init(this);
		
		setContentView(R.layout.activity_main);
		//Activate the below codes if you want to hide or change color of Action Bar
		
        //Hide ActionBar Code
		//android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		//actionBar.hide();
		
		//ActionBar color #e58073 #1fb6db
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000"))); // set your desired color
		
		//Hide Title
		//ActionBar actionBar = getActionBar();
		//actionBar.setTitle("");
		
		//We load the below WebView in activity_main
		mWebView = (WebView) findViewById(R.id.activity_main_webview);
		
		//Progress Circle loader at middle
		progress = (ProgressBar) findViewById(R.id.progressBar);
		
				// Enable Javascript
				WebSettings webSettings = mWebView.getSettings();
				webSettings.setJavaScriptEnabled(true);
				
				//loads the WebView completely zoomed out
		        mWebView.getSettings().setLoadWithOverviewMode(true);

		        //true makes the Webview have a normal viewport such as a normal desktop browser 
		        //when false the webview will have a viewport constrained to it's own dimensions
		        mWebView.getSettings().setUseWideViewPort(true);
		        
		        //Rate this app popup at bottom using Math (library)
		        AppRate.with(MainActivity.this).checkAndShow();
                
			   
			    //Calling no connection layout when there is no Internet connection.
			    mWebView.setWebViewClient(new MyAppWebViewClient() {
		            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		            	setContentView(R.layout.no_connection);
		            	
		            	// On Button Click
		            	final Button button = (Button) findViewById(R.id.button1);
		                button.setOnClickListener(new View.OnClickListener() {

							public void onClick(View v) {
		                    	
		                         // Perform action on click
								
								 Intent intent = new Intent(MainActivity.this, MainActivity.class);
								 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
								 startActivity(intent);

		                    	
		                    }
							
		                });
		                
		                
		                }
		                
		        });
			    
			    
			    
			    // Pull down to Refresh Layout
                mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
                ActionBarPullToRefresh.from(this)
                        .allChildrenArePullable()
                        .listener(this)
                        .setup(mPullToRefreshLayout);
               
                //Set your app's default(Main) URL here.
                mWebView.loadUrl("http://okvizag.com/stream");
			    
		}
	
	
	@Override
    public void onRefreshStarted(View view) {
          // TODO Auto-generated method stub

          //////This method will Automatically call when
          /////pull to refresh event occurs

            mWebView.reload();
    }

      private class MyAppWebViewClient extends WebViewClient {

            @Override
            //This class loads the url in the same activity
            //if it ends with abcd.com (Eg.: "okvizag.com")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            	if(Uri.parse(url).getHost().endsWith("okvizag.com")) {
                    return false;
                }
                 
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
        }

            @Override
            public void onPageFinished(WebView view, String url) {
            	
            	//The progress circle and pull down to refresh animation will disappear
            	//As soon as the page loads completely.
            	progress.setVisibility(View.GONE);
				MainActivity.this.progress.setProgress(100);
                super.onPageFinished(view, url);

                // If the PullToRefreshAttacher is refreshing, make it as complete
                if (mPullToRefreshLayout.isRefreshing()) {
                    mPullToRefreshLayout.setRefreshComplete();
                    
                    
                }
            }

    		 @Override
    		public void onPageStarted(WebView view, String url, Bitmap favicon) {
    			 progress.setVisibility(View.VISIBLE);
    			MainActivity.this.progress.setProgress(0);
    			super.onPageStarted(view, url, favicon);
    		}
            
      }
            
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
		
	}
	
//Menu OverFlow 
	//Add your Menu overflow options here.
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			
		
		//How to post ...
		
		switch (item.getItemId()) {
		case R.id.howto_post:
			mWebView.loadUrl("http://okvizag.com/stream/how-to-post-your-ad-or-event/");
			}
		
		//Email Us ...
		
			switch (item.getItemId()) {
			case R.id.email_us:
			       emailUs();
				}
				
		
		//Share this app ...
		
		switch (item.getItemId()) {
	    case R.id.share_this_app:
	        shareAPP();
		}

		//PlayStore linking
		
		switch (item.getItemId()) {
		case R.id.action_share:
			shareMRK();
	    }
	    
		// Share url
		
		switch (item.getItemId()) {
		case R.id.share_with:
			shareURL();
		}
	
		//Reload
		switch (item.getItemId()) {
		case R.id.action_refresh:
    	mWebView.loadUrl("http://okvizag.com/stream/category/deals/");
		}
		
	    return super.onOptionsItemSelected(item);
		}
	
	
	private void emailUs() {
	//Replace the email id (below) with yours
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "emailus@okvizag.com" });
		intent.putExtra(Intent.EXTRA_SUBJECT, "Title: ");
		intent.putExtra(Intent.EXTRA_TEXT, "Description: ");
		startActivity(Intent.createChooser(intent, "email_us"));
	}

	private void shareURL() {
	    Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent.setType("plain/text");
	    shareIntent.putExtra(Intent.EXTRA_TEXT, mWebView.getUrl());
	    startActivity(Intent.createChooser(shareIntent, "share_with"));
	}
	
	private void shareAPP() {
		//Share this app: Replace your Share text and App URL with yours (below)
		try
		{ Intent i = new Intent(Intent.ACTION_SEND);  
		  i.setType("text/plain");
		  i.putExtra(Intent.EXTRA_SUBJECT, "Ok Vizag");
		  String sAux = "\nLet me recommend you this application : \n";
		  sAux = sAux + "https://play.google.com/store/apps/details?id=com.okvizag.ctrlplusu \n\n";
		  i.putExtra(Intent.EXTRA_TEXT, sAux);  
		  startActivity(Intent.createChooser(i, "choose one"));
		}
		catch(Exception e)
		{ //e.toString();
		}   
	
	}
	
	
	@SuppressWarnings("unused")
	//Rate this app: Replace the below Google Play URL with yours
	private void shareMRK() {
		final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
	    try {
	        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.okvizag.ctrlplusu")));
	    } catch (android.content.ActivityNotFoundException anfe) {
	        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.okvizag.ctrlplusu")));
	    }
}
	
	@Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
        
       
    }
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

        
    }
