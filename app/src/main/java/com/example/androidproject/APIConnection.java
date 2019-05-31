package com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class APIConnection {
    private final static String api_key = "ea786a1de5ea2977157afe4085e0bb07";
    private final static TmdbMovies api = new TmdbApi(api_key).getMovies();

    public static TmdbMovies getTmdbMovies(){
        return api;
    }

    public static List<MovieDb> movies = new ArrayList<>();
}
