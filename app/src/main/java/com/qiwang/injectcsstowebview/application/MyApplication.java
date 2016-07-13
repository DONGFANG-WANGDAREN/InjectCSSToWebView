package com.qiwang.injectcsstowebview.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by qiwang on 16/7/13.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

    public static Context getContext() {
        return mContext;
    }


}
