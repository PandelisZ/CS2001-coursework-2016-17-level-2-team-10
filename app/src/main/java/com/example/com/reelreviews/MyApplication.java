package com.example.com.reelreviews;

import android.app.Application;
import android.content.Context;

/**
 * Created by subam on 2/11/17.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance =this;

    }

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}