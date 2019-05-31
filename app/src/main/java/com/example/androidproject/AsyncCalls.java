package com.example.androidproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

@SuppressLint("Registered")
public class AsyncCalls extends Activity {
    static List<MovieDb> movieDbList;
    TmdbMovies movies;

    public List<MovieDb> getMovies() {
        if (movieDbList == null) {
            new AsyncTmdbMovies().execute();
        }
        return movieDbList;
    }

    public class AsyncTmdbMovies extends AsyncTask<List, Integer, List> {

        @Override
        protected List doInBackground(List... moviesArray) {
            if (movies == null) {
                movies = APIConnection.getTmdbMovies();
            }
            movieDbList = new ArrayList<>();


            TmdbMovies allMovies = APIConnection.getTmdbMovies();
            MovieResultsPage movieResultsPage = allMovies.getPopularMovies("en", 1);
            List<MovieDb> moviesWithoutPictures = movieResultsPage.getResults();
            List<MovieDb> moviesWithPictures = new ArrayList<>();
            for (int i = 0; i < moviesWithoutPictures.size(); i++) {
                int id = moviesWithoutPictures.get(i).getId();
                movieDbList.add(allMovies.getMovie(id, "en"));
                System.out.println(id);
            }
            movieDbList = moviesWithPictures;

            return movieDbList;

        }

        @Override
        protected void onPostExecute(List list) {
        }
    }
}
