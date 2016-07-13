package com.qiwang.injectcsstowebview.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qiwang.injectcsstowebview.R;
import com.qiwang.injectcsstowebview.util.ConstantsUtil;
import com.qiwang.injectcsstowebview.view.MyWebView;

/**
 * Created by qiwang on 16/7/13.
 */
public class WebViewActivity1 extends AppCompatActivity {
    private  MyWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mWebView.loadMyUrl(ConstantsUtil.URL,ConstantsUtil.CSS_FILE_NAME);
    }

    private void initView() {
        setContentView(R.layout.activity_webview);
        this.mWebView = (MyWebView) findViewById(R.id.webview);
    }
}
