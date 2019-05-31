package com.example.androidproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MovieViewerAdapter extends RecyclerView.Adapter<MovieViewerAdapter.ViewHolder> {
    private List<MovieDb> movies;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.ivCell);
            // Add an onClickListener so that an image is clickable
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(imageView.getContentDescription());
//                    MainActivity.startFragment();
                }
            });
        }
    }

    public MovieViewerAdapter(Context context) {
        movies = getMovies();
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewerAdapter.ViewHolder viewHolder, int i) {
        String posterPath = movies.get(i).getPosterPath();

        //Adds the ID of the movie to contentdescription, because why not
        int id = movies.get(i).getId();
        String idAsString = Integer.toString(id);
        viewHolder.imageView.setContentDescription(idAsString);

        Glide.with(context).clear(viewHolder.imageView);
        String urlToCall = String.format("https://image.tmdb.org/t/p/w500%s", posterPath);
        Glide.with(context).load(urlToCall).into(viewHolder.imageView);
//        System.out.println(viewHolder.imageView.getContentDescription());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private List<MovieDb> getMovies() {
        TmdbMovies allMovies = APIConnection.getTmdbMovies();
        MovieResultsPage movieResultsPage = allMovies.getPopularMovies("en", 1);
        List<MovieDb> moviesWithoutPictures = movieResultsPage.getResults();
        List<MovieDb> moviesWithPictures = new ArrayList<>();
        for (int i = 0; i < moviesWithoutPictures.size(); i++) {
            int id = moviesWithoutPictures.get(i).getId();
            moviesWithPictures.add(allMovies.getMovie(id, "en"));
        }
        return moviesWithPictures;
    }
}
