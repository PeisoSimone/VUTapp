package com.peisocodes.vutapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class news extends AppCompatActivity {

    ProgressDialog progressDialog;

    public boolean isNetworkAvailable (final Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo()!= null &&connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        if (isNetworkAvailable(getApplicationContext())) {

            String url ="https://www.vut.ac.za/latest-news/";
            WebView newsWebView = findViewById(R.id.news_webView);
            newsWebView.setWebViewClient(new news.MyWebViewClient());

            newsWebView.getSettings().setJavaScriptEnabled(true);
            newsWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            newsWebView.loadUrl(url);
        }

        else {
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_LONG).show();
        }


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl("https://www.vut.ac.za/latest-news/");
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = new ProgressDialog(news.this);
            progressDialog.setMessage("Please Wait...");
            // progressDialog.setCancelable(false);
            progressDialog.show();
            Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:document.getElementById('Action_bar').style.display='none'; void(0);");
            view.loadUrl("javascript:document.getElementById('Header_wrapper').style.display='none'; void(0);");
            view.loadUrl("javascript:document.getElementById('Footer').style.display='none'; void(0);");
            view.loadUrl("javascript:document.getElementById('fsml_ff').style.display='none'; void(0);");
            view.loadUrl("javascript:document.getElementById('real-accessability').style.display='none'; void(0);");

            //super.onPageFinished(view, url);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }
}
