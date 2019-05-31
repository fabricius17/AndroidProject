package com.example.androidproject;

import java.util.List;

import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class NonAndroidTesterClass {
    public static void main(String[] args) {

//        TmdbMovies movies = new TmdbApi("ea786a1de5ea2977157afe4085e0bb07").getMovies();
//        MovieResultsPage mrp = movies.getPopularMovies("en", 1);
////        for (int i = 1; i <= 5; i++) {
////            mrp= movies.getPopularMovies("en",i);
//            List<MovieDb> list = mrp.getResults();
//            System.out.println(list);
//        }
        // Test respons tid på at hente en håndfuld film og info omkring disse film
        long startTime = System.currentTimeMillis();
        TmdbMovies film = APIConnection.getTmdbMovies();

        int totalMoviesFetched = 0;
        int number = 1;
        for (int i = 1; i <= 2; i++) {
            MovieResultsPage movies = film.getPopularMovies("en", i);
            List<MovieDb> listOfPopularMovies = movies.getResults();
            totalMoviesFetched += listOfPopularMovies.size();
            for (int j = 0; j < listOfPopularMovies.size(); j++) {
                int id = listOfPopularMovies.get(j).getId();
                MovieDb movie = film.getMovie(id, "en");
                System.out.printf("Title: %d - %s\t%s\t%s\t%s\t%s\n", number, movie.getTitle(),
                        movie.getReleaseDate(), movie.getHomepage(),
                        movie.getBudget(), movie.getGenres(), movie.getOverview());
                number++;
            }
        }
//        System.out.println((totalMoviesFetched == 100) ? "Fetched 100 movies" : "Something went did not fetch exactly 100 movies: " + totalMoviesFetched);
        System.out.println("Total runtime: " + (System.currentTimeMillis() - startTime));


//        int id = list.get(0).getId();
//        System.out.println(id);
//        MovieDb movie = movies.getMovie(id, "en");
//
//        for (int i = 0; i < 150; i++) {
//            System.out.print("-");
//        }
//        System.out.println();
//        for (int i = 0; i < mrp.getResults().size(); i++) {
//            id = mrp.getResults().get(i).getId();
//            System.out.println("index: " + i + "\tID: " + id + " - " + mrp.getResults().get(i));
//        }

    }
}
