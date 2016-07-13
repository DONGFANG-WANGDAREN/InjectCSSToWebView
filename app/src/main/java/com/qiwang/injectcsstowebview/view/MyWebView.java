package com.qiwang.injectcsstowebview.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.qiwang.injectcsstowebview.application.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qiwang on 16/7/13.
 */
public class MyWebView extends WebView {

    public static String mDefaultEncoding = "UTF-8";

    public String mCss;
    String mHtml = null;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadMyUrl(String url, String css) {
        this.mCss = css;
        process(url);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    private String injectCss(String html, String css) {
        int headEnd = html.indexOf("</head>");
        String res = "";
        if (headEnd > 0) {
            res = html.substring(0, headEnd) + css + html.substring(headEnd, html.length());
        } else {
            res = "<head>" + css + "</head>" + html;
        }
        return res;
    }

    private void process(String url) {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
    }

    private String buildCss() {
        StringBuilder sb = new StringBuilder();

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(MyApplication.getContext().getAssets().open(mCss), mDefaultEncoding);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "<style>" + sb.toString().trim().replace("\n", "") + "</style>";

    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private URL mUrl;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder total = new StringBuilder();
            try {
                mUrl = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();

                InputStream is = connection.getInputStream();

                String encoding = connection.getContentEncoding();

                if (encoding == null) {
                    encoding = mDefaultEncoding;
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
                String line;
                while ((line = br.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return total.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mHtml = s;
            String css = buildCss();
            mHtml = injectCss(mHtml, css);
            loadDataWithBaseURL(String.valueOf(mUrl), mHtml, null, mDefaultEncoding, null);
        }

    }


}
