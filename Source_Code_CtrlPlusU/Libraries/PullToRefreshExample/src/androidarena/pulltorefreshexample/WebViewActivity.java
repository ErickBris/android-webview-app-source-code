package androidarena.pulltorefreshexample;

import androidarena.pulltorefreshexample.R;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends BaseActivity implements OnRefreshListener {

	 private PullToRefreshLayout mPullToRefreshLayout;

	    private WebView mWebView;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_web_view);
			
			 mWebView = (WebView) findViewById(R.id.webview);
		        mWebView.getSettings().setJavaScriptEnabled(true);
		        mWebView.setWebViewClient(new SampleWebViewClient());

		        // Now find the PullToRefreshLayout and set it up
		        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
		        ActionBarPullToRefresh.from(this)
		                .allChildrenArePullable()
		                .listener(this)
		                .setup(mPullToRefreshLayout);

		        // Finally make the WebView load something...
		        mWebView.loadUrl("http://www.androidarena.co.in");
			
		}

		

		@Override
		public void onRefreshStarted(View view) {
			// TODO Auto-generated method stub
			
			//////This method will Automatically call when 
			/////pull to refresh event occurs 
			
			  mWebView.reload();
		}
		
		  private class SampleWebViewClient extends WebViewClient {

		        @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		            // Return false so the WebView loads the url
		            return false;
		        }

		        @Override
		        public void onPageFinished(WebView view, String url) {
		            super.onPageFinished(view, url);

		            // If the PullToRefreshAttacher is refreshing, make it as complete
		            if (mPullToRefreshLayout.isRefreshing()) {
		                mPullToRefreshLayout.setRefreshComplete();
		            }
		        }
		    }
		


}
