package com.example.androidproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class MainActivity extends AppCompatActivity {
    int movieid = 5350;
    TmdbMovies movies;
    MovieDb movie;

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncTmdbMovies().execute();
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    public void getMovies(View view) {
        new AsyncMovieDb().execute(movieid);
    }

    public class AsyncTmdbMovies extends AsyncTask<TmdbMovies, Integer, TmdbMovies> {


        @Override
        protected TmdbMovies doInBackground(TmdbMovies[] moviesArray) {
            if (movies == null) {
                movies = new TmdbApi(Config.getAPI()).getMovies();
            }
            return movies;
        }
    }

    public class AsyncMovieDb extends AsyncTask<Integer, Integer, MovieDb> {
        MovieDb getMovie(int movid) {
            System.out.println(movid);
            try {
                movie = movies.getMovie(movid, "en");
            } catch (RuntimeException e) {
                movid++;
                return (getMovie(movid));
            }
            movieid = movid;
            return movie;
        }

        @Override
        protected MovieDb doInBackground(Integer... movid) {
            getMovie(movid[0]);
            System.out.println("ID: " + movieid + " " + movie.toString());
            return movie;
        }


        @Override
        protected void onPostExecute(MovieDb movie) {
            movieid++;
            TextView tv = findViewById(R.id.tv);
            tv.setText(movie.getTitle());
        }
    }
}
