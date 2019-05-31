package com.example.androidproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TmdbMovies movies;
    List<MovieDb> movieDbList;
    boolean loading = true;

    public List<MovieDb> getMovies() {
        if (movieDbList == null) {
            new AsyncTmdbMovies().execute();
        }
        return movieDbList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieViewerAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        new AsyncTmdbMovies().execute();
    }

    public class AsyncTmdbMovies extends AsyncTask<List, Integer, List> {

        @Override
        protected List doInBackground(List... moviesArray) {
            if (movies == null) {
                movies = APIConnection.getTmdbMovies();
            }

            if (movieDbList == null) {
                movieDbList = new ArrayList<>();
            }
            TmdbMovies allMovies = movies;
            MovieResultsPage movieResultsPage = allMovies.getPopularMovies("en", 1);
            List<MovieDb> moviesWithoutPictures = movieResultsPage.getResults();
            for (int i = 0; i < moviesWithoutPictures.size(); i++) {
                MovieDb movie = moviesWithoutPictures.get(i);
                int id = moviesWithoutPictures.get(i).getId();
                APIConnection.movies.add(movie);

                adapter.notifyItemInserted(adapter.getItemCount() - 1);

            }
            loading = false;
            return movieDbList;

        }

        @Override
        protected void onPostExecute(List list) {

        }
    }
}
