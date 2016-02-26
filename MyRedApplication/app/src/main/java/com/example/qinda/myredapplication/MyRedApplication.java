package com.example.qinda.myredapplication;

import android.app.Application;

/**
 * Created by Qinda on 2016/1/12.
 */
public class MyRedApplication extends Application {
    private static MyRedApplication mInstance;

    public static Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = null;
    }
}
