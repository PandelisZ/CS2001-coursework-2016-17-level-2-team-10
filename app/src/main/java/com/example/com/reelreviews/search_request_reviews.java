package com.example.com.reelreviews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class search_request_reviews extends StringRequest{
    public search_request_reviews (String Search_Request_URL, Response.Listener<String> listener){
        super(Request.Method.GET, Search_Request_URL, listener, null);
    }
}