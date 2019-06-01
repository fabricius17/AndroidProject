package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieViewerAdapter extends RecyclerView.Adapter<MovieViewerAdapter.ViewHolder> {
    private List<MovieDb> movies;
    private Context context;
    private MovieDb movie;


    public class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.ivCell);
            // Add an onClickListener so that an image is clickable
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idToSearch = Integer.parseInt(String.valueOf(imageView.getContentDescription()));
                    MovieDb intentMovie = null;
                    for (int i = 0; i < movies.size(); i++) {
                        int id = movies.get(i).getId();
                        if (id == idToSearch) {
                            intentMovie = movies.get(i);
                            break;
                        }
                    }
                    // Starts a localbroadcast so that it can be received in MainActivity
                    if (intentMovie == null) {
                        // Make a toast in case something goes wrong
                        CharSequence text = "Something went wrong, please restart app!";
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context.getString(R.string.intent_key));
                        intent.putExtra(context.getString(R.string.intent_value), intentMovie);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }
            });
        }
    }

    public MovieViewerAdapter(Context context) {
        // movies = getMovies();
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
        movies = APIConnection.movies;
        return movies.size();
    }

    public void addToMovies(MovieDb movie) {
        this.movie = movie;
        movies.add(movie);
    }

}
