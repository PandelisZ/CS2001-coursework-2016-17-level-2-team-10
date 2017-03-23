package moviedb.brunel.group10.myapplication;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class search_request_imdbID extends StringRequest {

    public search_request_imdbID (String Search_Request_URL, Response.Listener<String> listener){
        super(Method.GET, Search_Request_URL, listener, null);
    }
}
