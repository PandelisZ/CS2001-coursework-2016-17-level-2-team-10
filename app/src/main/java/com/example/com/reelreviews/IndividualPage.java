package com.example.com.reelreviews;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONObject;

public class IndividualPage extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, AsyncTaskCompleteListener<MovieData> {
    //Search can be used using both imdb id and tmdb id
    //Movie Id TMDB
    private static String tmdbId = "tt3783958";
    //Imdb id
    private static String imdbID = tmdbId   ;
    //Query URL that is used to get the youtube video id
    private static String youtubeQueryURL = "https://api.themoviedb.org/3/movie/" + tmdbId + "/videos?api_key=b100be8111f00affe3773ea55d4b47d3&language=en-US";
    //Query URL that is used to get hte movie info
    private static String movieInfoURL = "https://api.themoviedb.org/3/movie/" + tmdbId + "?api_key=b100be8111f00affe3773ea55d4b47d3&language=en-US";
    //Query URL that is used to get cast members
    private static String castInfoURL = "https://api.themoviedb.org/3/movie/" + tmdbId + "/credits?api_key=b100be8111f00affe3773ea55d4b47d3";
    //Query URL that is used to get ratings
    private static String ratingURL = "http://www.omdbapi.com/?i=" + imdbID + "&plot=short&r=json&tomatoes=true";
    //String to store Youtube Video Id
    private String videoID;
    //Instance Of Youtube player
    private YouTubePlayer m_youTubePlayer;
    //Instance of request queue
    private RequestQueue requestQueue = MySingleton.getsInstance().getmRequestQueue();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_page_layout);

        //Calls method that returns youtube id from json response and sets to respective view
        getYoutubeVideoId_Volley();

        //Call methods that returns movie info from json response and sets to respective view
        getMovieInfo();

        //Calls methods to extract ratings
        getRatings();

        //Calls method that returns MovieData object with cast members and sets to respective view
        getCastInfo();

        /**
         * Listener for Share button
         * Shares the info about the movie through intents
         * Opens a dialog for the user to choose the platfrom to share
         */

        Button shareButton = (Button) findViewById(R.id.share_Button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Text view object to get the title anme*/
                TextView title = (TextView) findViewById(R.id.title);

                /**
                 * Creating intents to share and population menu
                 */
                Intent optionInflator = null;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, title.getText().toString());
                intent.setType("text/plain");
                optionInflator = Intent.createChooser(intent, "Share");
                startActivity(optionInflator);
            }
        });

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        m_youTubePlayer = youTubePlayer;
        if (!b) {
            youTubePlayer.cueVideo(videoID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Error loading Youtube", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            m_youTubePlayer.setFullscreen(true);
        }
    }

    /**
     * Using volley to extract youtube video id data
     */
    public void getYoutubeVideoId_Volley() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, youtubeQueryURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                new ParseNetworkResponseAsync(IndividualPage.this, IndividualPage.this).execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Using Volley to extract ratings from omdb api
     */
    public void getRatings() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ratingURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                new ParseNetworkResponseAsync(IndividualPage.this, IndividualPage.this).execute(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    /**
     * Using Volley to extract movie data(synopse, genre, ...)
     */

    public void getMovieInfo() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, movieInfoURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                new ParseNetworkResponseAsync(IndividualPage.this, IndividualPage.this).execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Using volley to extract cast info of the movie
     */
    public void getCastInfo() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, castInfoURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                new ParseNetworkResponseAsync(IndividualPage.this, IndividualPage.this).execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Returns Object from async task
     *
     * @param result The resulting object from the AsyncTask.
     */
    @Override
    public void onTaskComplete(final MovieData result) {
        //Set cast members to respective view
        if (result.getTitle() == null && result.getYoutubeId() == null && result.getImdbRating() == null) {
            //Set cast members to respective View
            ((TextView) findViewById(R.id.cast)).setText(TextUtils.join("\n", result.getCast()));
        } else if (result.getYoutubeId() == null && result.getImdbRating()==null) {
            //Set the title of the movie to the respective view
            ((TextView) findViewById(R.id.title)).setText(result.getTitle());
            //Set Image to respective image view
            Glide.with(this).load(result.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) findViewById(R.id.thumbnail));
            //Set the genre of the movie to the respective view
            ((TextView) findViewById(R.id.genre)).setText(TextUtils.join(", ", result.getGenre()));
            //Set the synopsis of the movie to the respective view
            ((TextView) findViewById(R.id.synopsis)).setText(result.getSynopsis());
        }
        else if (result.getImdbRating() != null){
            //Set imdb rating to respective View
            ((TextView) findViewById(R.id.imdb)).setText(String.valueOf((int) (Double.valueOf(result.getImdbRating()) * 10)));
            //Set rotten tomatoes rating to respective View
            ((TextView) findViewById(R.id.rottenTomatoes)).setText(result.getRottentomatoesRating());
            //Set metacritic rating rating to respective View
            ((TextView) findViewById(R.id.metacritic)).setText(result.getMetacritcRating());
            //Set the reel rating by calculating the average of different sources
            double reelSum =  (Double.valueOf(result.getImdbRating())*100/10)+Double.valueOf(result.getRottentomatoesRating())+Double.valueOf(result.getMetacritcRating());
            double reelRating = reelSum/3;
            ((TextView) findViewById(R.id.reelreview)).setText(String.valueOf((int)reelRating));
        }
        else {

            videoID = result.getYoutubeId();
            //Create Youtube Support Fragment
            YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubepLAYER_fragment);
            youTubePlayerSupportFragment.initialize(Config.API_KEY, this);
        }
    }

}