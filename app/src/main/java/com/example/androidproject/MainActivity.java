package com.example.androidproject;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
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

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter(getString(R.string.intent_key)));
    }

    public BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MovieDb movie = (MovieDb) intent.getSerializableExtra(getString(R.string.intent_value));
            startFragment(movie);
        }
    };

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
            for (int j = 1; j <= 1; j++) {

                MovieResultsPage movieResultsPage = allMovies.getPopularMovies("en", j);
                List<MovieDb> moviesWithoutPictures = movieResultsPage.getResults();

                for (int i = 0; i < moviesWithoutPictures.size(); i++) {
                    int id = moviesWithoutPictures.get(i).getId();
                    MovieDb realMovie = allMovies.getMovie(id, "en");
                    APIConnection.movies.add(realMovie);

                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                }
            }
            loading = false;
            return movieDbList;

        }

        @Override
        protected void onPostExecute(List list) {

        }

    }

    public void startFragment(MovieDb movie) {
        HashMap<String, String> movieInfo = new HashMap<>();
        movieInfo.put("backdrop", movie.getBackdropPath());
        movieInfo.put("title", movie.getTitle());
        movieInfo.put("release", movie.getReleaseDate());
        movieInfo.put("overview", movie.getOverview());
        movieInfo.put("homepage", movie.getHomepage());

        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.bundle_key), movieInfo);

        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, movieFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
