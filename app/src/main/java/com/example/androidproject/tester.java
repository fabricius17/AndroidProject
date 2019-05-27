package com.example.androidproject;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class tester {
    public static void main(String[] args) {
        TmdbMovies movies = new TmdbApi("ea786a1de5ea2977157afe4085e0bb07").getMovies();
        MovieResultsPage mrp = movies.getPopularMovies("en", 0);
//        System.out.println(mrp.getResults());
        List<MovieDb> list = mrp.getResults();

        int id = list.get(0).getId();
//        int id = 5353;
        System.out.println(id);
        MovieDb movie = movies.getMovie(id, "en");
        System.out.println("ReleaseDate:\t" + movie.getReleaseDate());
        System.out.println("Homepage:\t" + movie.getHomepage());
        System.out.println("Cast:\t" + movie.getCast());
        System.out.println("Budget:\t" + movie.getBudget());
        System.out.println("Genres:\t" + movie.getGenres());
        System.out.println("Overview:\t" + movie.getOverview());


        for (int i = 0; i < mrp.getResults().size(); i++) {
            int id = mrp.getResults().get(i).getId();
            System.out.println("index: " + i + "\tID: " + id + " - " + mrp.getResults().get(i));
        }

    }
}
