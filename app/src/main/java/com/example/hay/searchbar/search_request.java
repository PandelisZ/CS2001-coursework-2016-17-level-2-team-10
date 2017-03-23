package com.example.hay.searchbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class search_request extends StringRequest {

    private static final String Search_Request_URL = "https://api.themoviedb.org/3/search/movie";
    private Map<String,String> params;

    public search_request(String api_key, String query, Response.Listener<String> listener){

        super(Method.POST, Search_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("api_key", api_key);
        params.put("query", query);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
