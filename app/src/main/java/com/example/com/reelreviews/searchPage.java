package com.example.com.reelreviews;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class searchPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<movieList> MovieList;
    private movieAdapter adapter;
    private String movie;
    //private static final String Search_Request_URL = "https://api.themoviedb.org/3/search/movie";
    final String url = "https://image.tmdb.org/t/p/w154/";
    final String api_key="4acd02f362b8c19c39b89f7c765d105c";
    private RequestQueue requestQueue = MySingleton.getsInstance().getmRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        setTitle("Movie Search");

        final SearchView search = (SearchView) findViewById(R.id.search);

        search.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movie=query;
                findMovie();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        MovieList = new ArrayList<>();

    }

    public void findMovie(){
        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray array = jsonResponse.getJSONArray("results");
                    MovieList.clear();

                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);

                        movieList list = new movieList(
                                object.getString("title"),
                                //reel rating
                                "N/A",
                                //rottentomatoes
                                "N/A",
                                //metacritic
                                "N/A",
                                //imdb
                                "N/A",
                                url+object.getString("poster_path"),
                                object.getString("id"),
                                object.getString("release_date")
                        );
                        getMovieRatings(list);
                        MovieList.add(list);
                    }
                    adapter = new movieAdapter(MovieList,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        search_request request = new search_request(api_key,movie,responseListener);
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void getIMDBID(movieList list1) {
        final movieList list = list1;
        String id = list.getimdbID();
        String URL = "https://api.themoviedb.org/3/movie/"+id+"?api_key=4acd02f362b8c19c39b89f7c765d105c";

        Response.Listener<String> listener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    list.setimdbID(jsonResponse.getString("imdb_id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        list1.setimdbID(list.getimdbID());
        search_request_imdbID requestID = new search_request_imdbID(URL,listener);
        RequestQueue queue = Volley.newRequestQueue(this);
        requestID.setShouldCache(true);
        queue.add(requestID);
    }
    public void getMovieRatings(final movieList list){
        String title = list.getTitle();
        String date = list.getReleaseDate();
        String [] dateSplit = date.split("-");
        String year = dateSplit[0];
        String URL = "http://www.omdbapi.com/?t="+title+"&y="+year;
        // when rottentomatoes api is fixed, enable rotten tomatoes flag(SEE BELOW).
        //String URL = "http://www.omdbapi.com/?t="+title+"&y="+year+"&tomatoes=true";

        Response.Listener<String> listener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    // add the line below when rottentomatoes api is fixed
                    //list.setRottenTomatoesRating(jsonResponse.getString("tomatoRating"));
                    if(!"N/A".equals(jsonResponse.getString("Metascore"))){
                        list.setMetacriticRating(jsonResponse.getString("Metascore"));
                    }
                    if(!"N/A".equals(jsonResponse.getString("imdbRating"))){
                        list.setImdbRating(jsonResponse.getString("imdbRating"));
                    }
                    list.setimdbID(jsonResponse.getString("imdbID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        search_request_reviews requestReviews = new search_request_reviews(URL,listener);
        requestReviews.setShouldCache(false);
        requestQueue.add(requestReviews);
    }
}
