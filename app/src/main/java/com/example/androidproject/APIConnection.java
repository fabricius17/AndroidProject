package com.example.androidproject;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;

public class APIConnection {
    private final static String api_key = "ea786a1de5ea2977157afe4085e0bb07";
    private final static TmdbMovies api = new TmdbApi(api_key).getMovies();

    public static TmdbMovies getTmdbMovies(){
        return api;
    }
}
