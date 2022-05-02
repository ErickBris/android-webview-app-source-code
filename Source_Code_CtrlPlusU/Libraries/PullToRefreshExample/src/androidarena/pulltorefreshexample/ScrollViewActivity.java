package androidarena.pulltorefreshexample;

import androidarena.pulltorefreshexample.R;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScrollViewActivity extends BaseActivity implements OnRefreshListener {

	 private PullToRefreshLayout mPullToRefreshLayout;
	   TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_view);
	   
		textView=(TextView)findViewById(R.id.scrollTextView);
		
		///You will setup the action bar with pull to refresh layout 
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
		ActionBarPullToRefresh.from(this)
              .allChildrenArePullable()
              .listener(this)
              .setup(mPullToRefreshLayout);
		
	}


	@Override
	public void onRefreshStarted(View view) {
		// TODO Auto-generated method stub
	
		/**
		 * Below  AsyncTask class is used to update the view 
		 * Asynchronously 
		 */
		new AsyncTask<Void, Void, Void>() {

	            @Override
	            protected Void doInBackground(Void... params) {
	                try {
	                    Thread.sleep(5000);
	                    //Here you can get the new text from DB or through a web service
	                    //Then YOu can pass it to onPostExecute() method to
	                    //Update the view 
	                    
	        
	                    
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                return null;
	            }

	            @Override
	            protected void onPostExecute(Void result) {
	                super.onPostExecute(result);

	                //Here you can update the view 
	                textView.setText(textView.getText().toString()+"--New Content Added");
	              
	                // Notify PullToRefreshLayout that the refresh has finished
	                mPullToRefreshLayout.setRefreshComplete();
	            }
	        }.execute();
	}


}
