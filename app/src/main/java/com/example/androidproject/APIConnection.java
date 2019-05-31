package com.example.androidproject;

import info.movito.themoviedbapi.TmdbApi;

public class APIConnection {
    private final static String api_key = "ea786a1de5ea2977157afe4085e0bb07";
    private final static TmdbApi api = new TmdbApi(api_key);

    public static TmdbApi getConnection(){
        return api;
    }
}
