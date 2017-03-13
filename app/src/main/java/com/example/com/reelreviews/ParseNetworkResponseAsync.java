package com.example.com.reelreviews;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by subam on 2/15/17.
 */

public class ParseNetworkResponseAsync extends AsyncTask<JSONObject, Void, MovieData> {

    private Context context;
    private AsyncTaskCompleteListener<MovieData> listener;

    public ParseNetworkResponseAsync(Context context, AsyncTaskCompleteListener<MovieData> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected MovieData doInBackground(JSONObject... params) {
        if (params[0].has("cast")) {
            try {
                JSONArray cast = params[0].getJSONArray("cast");
                String[] resultCast = new String[7];
                for (int i = 0; i < 7; i++) {
                    JSONObject members = cast.getJSONObject(i);
                    resultCast[i] = members.getString("name") + " - " + members.getString("character");
                }
                return new MovieData(resultCast);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (params[0].has("title")) {
            try {
                String title = params[0].getString("title");
                String posterpath = "https://image.tmdb.org/t/p/w500" + params[0].getString("poster_path");
                JSONArray genreJson = params[0].getJSONArray("genres");
                String[] genre = new String[genreJson.length()];
                for (int i = 0; i < genreJson.length(); i++) {
                    JSONObject genreObject = genreJson.getJSONObject(i);
                    genre[i] = genreObject.getString("name");
                }
                String synopsis = params[0].getString("overview");
                MovieData movieData = new MovieData(title, posterpath, genre, synopsis);
                FutureTarget<File> future = Glide.with(context)
                        .load(posterpath)
                        .downloadOnly(130, 195);
                future.get();
                return movieData;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else if (params[0].has("Metascore")) {
            try {
                String imdb = params[0].getString("imdbRating");
                String rottenTomatoes = params[0].getString("tomatoMeter");
                String metacritic = params[0].getString("Metascore");
                return new MovieData(imdb, rottenTomatoes, metacritic);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONArray itemArray = params[0].getJSONArray("results");
                JSONObject firstItem = itemArray.getJSONObject(0);
                String youtube_id = firstItem.getString("key");
                return new MovieData(youtube_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(MovieData movieData) {
        super.onPostExecute(movieData);
        listener.onTaskComplete(movieData);
    }
}
