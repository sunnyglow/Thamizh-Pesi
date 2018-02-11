package com.blogspot.vayalumvazhvum.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class BrowseModeActivity extends AppCompatActivity {
    private WebView mWebView;
    public static String url = "";
    ProgressBar pbWebpage = null;
    EditText edittext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        final Context c = this.getBaseContext();
        pbWebpage = (ProgressBar) findViewById(R.id.webpage_progressBar);
        ImageView readMode = (ImageView) findViewById(R.id.webpage_listennow_iv);
        readMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ExtendedReadModeActivity.class);
                url = mWebView.getOriginalUrl();

                startActivity(intent);
            }
        });

        edittext = (EditText) findViewById(R.id.url_editttext);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //Toast.makeText(c, edittext.getText(), Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    goToURLString(edittext.getText().toString(),c);
                    return true;
                }
                return false;
            }
        });


        ImageView clear = (ImageView) findViewById(R.id.clear_webpage_iv);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edittext.setText("");
                edittext.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edittext, 0);
            }
        });

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.google.in/");

        mWebView.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
            {
                if (paramAnonymousInt == 100)
                {
                    pbWebpage.setVisibility(View.GONE);
                    return;
                }
                edittext.setText(paramAnonymousWebView.getUrl());
                pbWebpage.setVisibility(View.VISIBLE);
                pbWebpage.setProgress(paramAnonymousInt);
            }
        });


        ImageView prev = (ImageView) findViewById(R.id.webpage_prev_iv);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.goBack();
            }
        });

        ImageView next = (ImageView) findViewById(R.id.webpage_next_iv);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.goForward();
            }
        });

        ImageView back = (ImageView) findViewById(R.id.webpage_back_iv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void goToURLString(String paramString, Context c)
    {
        if(paramString == null || paramString.trim().isEmpty())
        {
            return;
        }
        if(paramString.indexOf(".") < 0)
        {
            String gooleURL = "https://www.google.com/search?q="+paramString;
            Toast.makeText(c,gooleURL, Toast.LENGTH_SHORT).show();
            this.mWebView.loadUrl(gooleURL);
            return;
        }
        String str1 = paramString;
        if (paramString.length() < 7) {
            str1 = "http://" + paramString;
        }
        else
        {
            String str2 = paramString.substring(0, 7);
            if (!str2.equals("http://")) {
                str1 = paramString;
                if (!str2.equals("https:/")) {
                    str1 = "http://" + paramString;
                }
            }

        }
        Toast.makeText(c,str1, Toast.LENGTH_SHORT).show();
        this.mWebView.loadUrl(str1);
        return;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(mWebView != null) {
                mWebView.goBack();
            }
        }
       return true;
    }
}

