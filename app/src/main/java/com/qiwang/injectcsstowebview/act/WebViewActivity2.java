package com.qiwang.injectcsstowebview.act;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qiwang.injectcsstowebview.R;
import com.qiwang.injectcsstowebview.util.ConstantsUtil;
import com.qiwang.injectcsstowebview.view.MyWebView;

import java.io.InputStream;

/**
 * Created by qiwang on 16/7/13.
 */
public class WebViewActivity2 extends AppCompatActivity {


    private MyWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setWebView();
        initData();
    }

    private void setWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectCSS();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    private void initData() {
        mWebView.loadUrl(ConstantsUtil.URL);
    }

    private void initView() {
        setContentView(R.layout.activity_webview);
        this.mWebView = (MyWebView) findViewById(R.id.webview);

    }
    private void injectCSS() {
        try {
            InputStream is = getAssets().open(ConstantsUtil.CSS_FILE_NAME_2);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            mWebView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})();");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
