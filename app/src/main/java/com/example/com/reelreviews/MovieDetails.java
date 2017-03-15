package com.example.com.reelreviews;

import java.io.Serializable;


public class MovieDetails implements Serializable {

    private String imdb_id;

    private String tmdb_id;

    private String original_title;

    private String overview;

    private String release_date;

    private double vote_average;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String poster_path;

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    //    public String movieName, movieYear, genre, director, writer, actors, plot, language, imdbRating;
//    public void mapJson(String jsonData) throws JSONException {
//
//        JSONObject movieData = new JSONObject(jsonData);
//        movieName = "Movie: " + movieData.getString("Title");
//        movieYear = "Year: " + movieData.getString("Year");
//        genre = "Genre: " + movieData.getString("Genre");
//        director = "Director: " + movieData.getString("Director");
//        writer = "Writer:" + movieData.getString("Writer");
//        actors = "Actors: " + movieData.getString("Actors");
//        plot = "Plot: " + movieData.getString("Plot");
//        language = "Language: " + movieData.getString("Language");
//        imdbRating = "IMDB Rating: " + movieData.getString("imdbRating");
//
//    }
}
