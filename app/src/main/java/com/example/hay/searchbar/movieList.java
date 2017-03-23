package com.example.hay.searchbar;

import java.util.List;

public class movieList {
    String title;
    String rating;
    String poster;
    String imdbID;
    String rottenTomatoesRating;
    String metacriticRating;
    String imdbRating;
    String releaseDate;

    public movieList(String title, String rating,String rottenTomatoesRating,String metacriticRating,String imdbRating, String poster,String imdbID,String releaseDate){
        this.title=title;
        this.rating=rating;
        this.rottenTomatoesRating = rottenTomatoesRating;
        this.metacriticRating = metacriticRating;
        this.imdbRating = imdbRating;
        this.poster=poster;
        this.imdbID = imdbID;
        this.releaseDate = releaseDate;
    }

    public String getTitle(){
        return title;
    }
    public String getRating(){
        return rating;
    }
    public String getRottenTomatoesRating(){
        return rottenTomatoesRating;
    }
    public void setRottenTomatoesRating(String id){
        rottenTomatoesRating = id;
    }
    public String getMetacriticRating(){
        return metacriticRating;
    }
    public void setMetacriticRating(String id){
        metacriticRating = id;
    }
    public String getImdbRating(){
            return imdbRating;
    }
    public void setImdbRating(String id){
        imdbRating = id;
    }
    public String getPoster(){
        return poster;
    }
    public String getimdbID(){
        return imdbID;
    }
    public void setimdbID(String id){
        this.imdbID = id;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

}
