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
        if(!"N/A".equals(metacriticRating)&&!"N/A".equals(imdbRating)) {
            double rottentomates = 0.0;
            // remove above line and replace with line below when api is fixed
            //double rottentomates = Double.parseDouble(rottenTomatoesRating);
            double metacritic = Double.parseDouble(metacriticRating);
            double imdb = (Double.parseDouble(imdbRating)) * 10;
            // change to 3 when api is fixed
            rating = String.valueOf((int) (Math.round((rottentomates + metacritic + imdb) / 2)));
        }
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
        if(!"N/A".equals(imdbRating)){
            double imdbDouble = Double.parseDouble(imdbRating);
            return String.valueOf((int)(imdbDouble * 10));
        }else{
            return imdbRating;
        }
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
