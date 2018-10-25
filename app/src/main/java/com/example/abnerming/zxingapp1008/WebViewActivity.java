package com.example.abnerming.zxingapp1008;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
       WebView mWebView= (WebView) findViewById(R.id.webview);

       String url=getIntent().getStringExtra("web_url");
        mWebView.setWebViewClient(new WebViewClient());
       mWebView.loadUrl(url);


    }
}
