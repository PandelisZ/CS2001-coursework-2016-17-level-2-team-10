package com.example.com.reelreviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class MovieArrayAdapter extends ArrayAdapter {

    private List<MovieDetails> movieDetailsList;

    private int resource;

    private Context context;


    public MovieArrayAdapter(Context context, int resource, List<MovieDetails> movieDetails) {
        super(context, resource, movieDetails);
        this.context = context;
        this.movieDetailsList = movieDetails;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieDetails details = movieDetailsList.get(position);

        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView movieName = (TextView) view.findViewById(R.id.textView);
        TextView movieRating = (TextView) view.findViewById(R.id.textView2);
        TextView imdbID = (TextView) view.findViewById(R.id.textView3);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);

        movieName.setText(details.getOriginal_title());

        movieRating.setText(Double.toString(details.getVote_average()));

        imdbID.setText(details.getImdb_id());


        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + details.getPoster_path()).into(image);

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieDetailsList.get(position);
    }
}
