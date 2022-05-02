package com.okvizag.ctrlplusu;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MyAppWebViewClient extends WebViewClient {
	@Override
	//Loads the url(s) in the app if they end with abcd.com (Eg. "okvizag.com")
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(Uri.parse(url).getHost().endsWith("okvizag.com")) {
            return false;
        }
         
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
}
}
