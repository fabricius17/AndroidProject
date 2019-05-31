package com.example.androidproject;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disableStrictMode();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieViewerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    public void disableStrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

//    private ArrayList<Integer> generateNumbers(int amount) {
//        ArrayList<Integer> tmpArray = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < amount; i++) {
//            tmpArray.add(random.nextInt(10_000));
//        }
//        return tmpArray;
//    }

//    private List<MovieDb> getMovies() {
//        TmdbMovies allMovies = APIConnection.getConnection().getMovies();
//        MovieResultsPage movieResultsPage = allMovies.getPopularMovies("en", 1);
//        List<MovieDb> moviesWithoutPictures = movieResultsPage.getResults();
//        //Skal ændres til noget andet
//        List<MovieDb> MWP = null;
//        for (int i = 0; i < moviesWithoutPictures.size(); i++) {
//            int id = moviesWithoutPictures.get(i).getId();
//            MWP.add(allMovies.getMovie(id, "en"));
//        }
//        return MWP;
//    }
}
