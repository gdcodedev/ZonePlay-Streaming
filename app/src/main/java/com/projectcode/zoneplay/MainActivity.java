package com.projectcode.zoneplay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import static android.view.WindowManager.*;

public class MainActivity extends AppCompatActivity {

    private WebView zoneplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoneplay = findViewById(R.id.site);
        zoneplay.getSettings().setJavaScriptEnabled(true);
        zoneplay.setFocusable(true);
        zoneplay.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        zoneplay.getSettings().setAppCacheEnabled(true);
        zoneplay.getSettings().setDomStorageEnabled(true);
        zoneplay.setWebViewClient(new WebViewClient());
        zoneplay.setWebChromeClient(new ZONEPLAY());
        zoneplay.loadUrl("http://zoneplay.app.br");

    }

    private class ZONEPLAY extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ZONEPLAY () {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null){
                return  null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    @Override
    public void onBackPressed() {
        if (zoneplay.canGoBack()) {
            zoneplay.goBack();
        } else {
            super.onBackPressed();
        }
    }


    }
