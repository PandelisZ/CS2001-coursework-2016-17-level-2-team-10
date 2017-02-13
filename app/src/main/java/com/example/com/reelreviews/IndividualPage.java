package com.example.com.reelreviews;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndividualPage extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    //Query URL that is used to get the youtube video id
    private static String youtubeQueryURL = "https://api.themoviedb.org/3/movie/150540/videos?api_key=b100be8111f00affe3773ea55d4b47d3&language=en-US";
    //Query URL that is used to get hte movie info
    private static String movieInfoURL = "https://api.themoviedb.org/3/movie/150540?api_key=b100be8111f00affe3773ea55d4b47d3&language=en-US";
    //String to store Youtube Video Id
    private String videoID;
    //Instance Of Youtube player
    private YouTubePlayer m_youTubePlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_page_layout);

        //Calls method that returns youtube id from json response
        getYoutubeVideoId_Volley(new VolleyCallback() {
            @Override
            public void onSuccess(MovieData movieData) {
                //Do nothing
            }

            @Override
            public void onSuccess(String result) {
                videoID = result;
            }
        });

        //Calls methods that returns MovieData object from json response
        getMovieInfo(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                //Do nothing
            }

            @Override
            public void onSuccess(MovieData movieData) {
                //Set the title of the movie to the respective view
                ((TextView) findViewById(R.id.title)).setText(movieData.getTitle());
                //Set the genre of the movie to the respective view
                ((TextView) findViewById(R.id.genre)).setText(TextUtils.join(", ",movieData.getGenre()));
                //Set the synopsis of the movie to the respective view
                ((TextView) findViewById(R.id.synopsis)).setText(movieData.getSynopsis());
            }
        });

        //List of cast memebers
        String[] castMemebersList = {
                "Amy Poehler - Joy (voice)",
                "Phyllis Smith - Sadness (voice)",
                "Richard Kind - Bing Bong (voice)",
                "Bill Hader - Fear(voice)"
        };

        //Concatenates String array into a single string variable
        String castMembers = TextUtils.join("\n", castMemebersList);

        //Locates the text view to display the cast members
        TextView castMember = (TextView) findViewById(R.id.cast);
        castMember.setText(castMembers);

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

        //Create Youtube Support Fragment
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubepLAYER_fragment);
        youTubePlayerSupportFragment.initialize(Config.API_KEY, this);

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
    public void  getYoutubeVideoId_Volley(final VolleyCallback callback) {
        RequestQueue requestQueue = MySingleton.getsInstance().getmRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, youtubeQueryURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray itemArray = response.getJSONArray("results");
                    JSONObject firstItem = itemArray.getJSONObject(0);
                    String youtube_id = firstItem.getString("key");
                    callback.onSuccess(youtube_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public void getMovieInfo(final VolleyCallback callback){
        RequestQueue requestQueue = MySingleton.getsInstance().getmRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, movieInfoURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title = response.getString("title");
                    String posterpath = "https://image.tmdb.org/t/p/w500"+ response.getString("poster_path");
                    JSONArray genreJson = response.getJSONArray("genres");
                    String [] genre = new String[genreJson.length()];
                    for(int i = 0; i< genreJson.length(); i++){
                        JSONObject genreObject = genreJson.getJSONObject(i);
                        genre[i] = genreObject.getString("name");
                    }
                    String synopsis = response.getString("overview");
                    callback.onSuccess(new MovieData(title,posterpath,genre,synopsis));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
     * Volley call back interface to return values from listener interface
     */

    public interface VolleyCallback{
        void onSuccess(String result);
        void onSuccess(MovieData movieData);
    }

}