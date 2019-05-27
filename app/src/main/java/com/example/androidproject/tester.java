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
        System.out.println(mrp.getResults());
        List<MovieDb> l = mrp.getResults();
        System.out.println(((MovieDb)(l.get(0))).);

//        System.out.println(mrp.getTotalPages());
//        mrp = movies.getPopularMovies("en",1);
//        System.out.println(mrp.getResults());
//        mrp = movies.getPopularMovies("en",2);
//        System.out.println(mrp.getResults());
//        mrp = movies.getPopularMovies("en",3);
//        System.out.println(mrp.getResults());

    }
}
