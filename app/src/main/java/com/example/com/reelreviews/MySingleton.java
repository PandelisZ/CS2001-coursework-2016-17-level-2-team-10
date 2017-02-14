package com.example.com.reelreviews;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by subam on 2/11/17.
 */

public class MySingleton {

    private static MySingleton sInstance = null;

    private ImageLoader imageLoader;

    private RequestQueue mRequestQueue;

    private MySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

    }

    public static MySingleton getsInstance() {

        if (sInstance == null) {
            sInstance = new MySingleton();
        }

        return sInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
