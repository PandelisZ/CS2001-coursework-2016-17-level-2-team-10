package moviedb.brunel.group10.myapplication;


import java.util.List;

public class movieList {
    String title;
    String rating;
    String date;
    String poster;

    public movieList(String title, String rating, String date, String poster){
        this.title=title;

        this.rating=rating;
        this.date=date;
        this.poster=poster;
    }

    private List<movieList> movielist;

    public String getTitle(){
        return title;
    }


    public String getRating(){
        return rating;
    }

    public String getDate(){
        return date;
    }

    public String getPoster(){
        return poster;
    }
}
