package moviedb.brunel.group10.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;




public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<movieList> MovieList;
    private movieAdapter adapter;
    private String movie;
    final String url = "https://image.tmdb.org/t/p/w154/";
    final String api_key="b100be8111f00affe3773ea55d4b47d3";
    String query="Lord of the Rings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                        movieList list = new movieList(object.getString("title"),
                                object.getString("vote_average"),
                                object.getString("release_date"),
                                url+object.getString("poster_path")
                        );
                        MovieList.add(list);
                    }
                    adapter = new movieAdapter(MovieList,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };



        // MovieList.add(new movieList("The Avengers", "7.4","2012-04-25","https://image.tmdb.org/t/p/w154/cezWGskPY5x7GaglTTRN4Fugfb8.jpg"));
        // MovieList.add(new movieList("Avengers: Age of Ultron", "7.4", "2015-05-01", "t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg"));

        search_request request = new search_request(api_key,movie,responseListener);
      //  Toast.makeText(MainActivity.this, movie, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        request.setShouldCache(false);
        queue.add(request);
    }
}
