package com.example.android.searchview;

/**
 * Created by Bashir on 08/03/2017.
 */

import android.app.Application;


public class MovieDB extends Application {

    public static final String url = "https://api.themoviedb.org/3/";
    public static final String key = "b100be8111f00affe3773ea55d4b47d3";
    public static final String imageUrl = "https://image.tmdb.org/t/p/";
    /**
     * Example URL:
     * http://i1.ytimg.com/vi/TDFAYRtrYuk/hqdefault.jpg
     * For more info:
     * http://stackoverflow.com/questions/2068344/how-do-i-get-a-youtube-video-thumbnail-from-the-youtube-api
     */
    public static final String trailerImageUrl = "http://i1.ytimg.com/vi/";
    public static final String youtube = "https://www.youtube.com/watch?v=";
    public static final String appId = "95a38b9 2c5cb4bbfd779c0e2fcaef5a6";
    public static final String analyticsKey = "yourGoogleAnalyticsKey";

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
