package com.example.androidproject;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class tester {
    public static void main(String[] args) {
        TmdbMovies movies = new TmdbApi("ea786a1de5ea2977157afe4085e0bb07").getMovies();
        MovieResultsPage mrp = movies.getPopularMovies("en", 0);

        for (int i = 0; i < mrp.getResults().size(); i++) {
            int id = mrp.getResults().get(i).getId();
            System.out.println("index: " + i + "\tID: " + id + " - " + mrp.getResults().get(i));
        }

    }
}
