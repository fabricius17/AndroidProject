package com.example.androidproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.sql.SQLOutput;
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
        }
    }

    public MovieViewerAdapter(Context context) {
        System.out.println("CONSTRUCTOR");
        movies=getMovies();
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return null;
        System.out.println("ONCREATEVIEWHOLDER");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewerAdapter.ViewHolder viewHolder, int i) {
//        String posterPath = "";
//        for (; i < movies.size(); ) {
        System.out.println("ONBINDVIEWHOLDER");
        String posterPath = movies.get(i).getPosterPath();
//        }
        Glide.with(context).clear(viewHolder.imageView);
        String urlToCall = String.format("https://image.tmdb.org/t/p/w500%s", posterPath);
        Glide.with(context).load(urlToCall).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        System.out.println("GETITEMCOUNT");
        return movies.size();
    }

    private List<MovieDb> getMovies() {
        TmdbMovies allMovies = APIConnection.getConnection().getMovies();
        MovieResultsPage movieResultsPage = allMovies.getPopularMovies("en", 1);
        List<MovieDb> moviesWithoutPictures = movieResultsPage.getResults();
        //Skal ændres til noget andet
        List<MovieDb> MWP = new ArrayList<>();
        for (int i = 0; i < moviesWithoutPictures.size(); i++) {
            int id = moviesWithoutPictures.get(i).getId();
            MovieDb movie = allMovies.getMovie(id,"en");
            System.out.printf("Title: %s\t%s\t%s\t%s\t%s\n", movie.getTitle(),
                    movie.getReleaseDate(), movie.getHomepage(),
                    movie.getBudget(), movie.getGenres(), movie.getOverview());
            MWP.add(allMovies.getMovie(id, "en"));
        }
        return MWP;
    }
}
