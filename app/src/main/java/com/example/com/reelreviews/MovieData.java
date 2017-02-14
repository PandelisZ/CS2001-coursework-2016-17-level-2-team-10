package com.example.com.reelreviews;

/**
 * Created by subam on 2/13/17.
 */

public class MovieData {
    //Stores title of the movie
    private String title;
    //Stores url for movie image
    private String poster_path;
    //Stores genre in a String Array
    private String[] genre;
    //Stores Movie synopsis
    private String synopsis;
    //Stores Movie cast members in Array object
    private String[] cast;

    public MovieData(String title, String poster_path, String [] genre, String synopsis){
        this.title = title;
        this.poster_path = poster_path;
        this.genre = genre;
        this.synopsis =  synopsis;
    }

    /**]
     * Contructor just to get the cast members
     * @return MovieData object with only cast
     */
    public MovieData(String[] cast){
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String[] getGenre() {
        return genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String[] getCast() {
        return cast;
    }
}