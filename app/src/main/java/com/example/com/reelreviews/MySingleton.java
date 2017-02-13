package com.example.com.reelreviews;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by subam on 2/11/17.
 */

public class MySingleton {

   private static MySingleton sInstance = null;

    private RequestQueue mRequestQueue;

    private MySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

    }

    public static MySingleton getsInstance() {

        if (sInstance == null){
            sInstance = new MySingleton();
        }

        return sInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
}
