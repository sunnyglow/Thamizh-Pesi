package com.blogspot.vayalumvazhvum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WordActivity extends AppCompatActivity {
    WebView wordView = null;
    public static String htmlContent = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wordView = (WebView) findViewById(R.id.wordRead);
        wordView.setWebViewClient(new WebViewClient());

        // Enable Javascript
        WebSettings webSettings = wordView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wordView.loadData(htmlContent,"text/html",null);
    }

}
