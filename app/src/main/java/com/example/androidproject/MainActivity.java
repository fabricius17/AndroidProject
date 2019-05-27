package com.example.androidproject;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disableStrictMode();

    }

    public void disableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void getMovies(View view) {
//        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskReads();
//        try {
            TmdbApi api = new TmdbApi("ea786a1de5ea2977157afe4085e0bb07");

//        } finally {
//            StrictMode.setThreadPolicy(oldPolicy);
//        }
        TmdbMovies movies = new TmdbApi("ea786a1de5ea2977157afe4085e0bb07").getMovies();
        MovieDb movie = movies.getMovie(5353,"en");
        System.out.println("TEST OF API:\t"+movie.getOverview());
    }
}
