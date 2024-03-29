package com.example.androidproject;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidproject.Database.AppDatabase;
import com.example.androidproject.Database.Favorite;

import java.util.HashMap;

public class MovieFragment extends Fragment {
    private View view;
    private Bundle arguments;
    private Integer id;
    private AppDatabase database;

    private ImageView favorite;
    private ImageView backdrop;
    private TextView title;
    private TextView releaseDate;
    private TextView overview;
    private TextView homepage;
    private TextView favoriteTextView;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_details, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        arguments = getArguments();

        if (arguments == null) {
            CharSequence text = "Could not load movie details!";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        } else {
            final HashMap<String, String> movieInfo = (HashMap) arguments.getSerializable(getString(R.string.bundle_key));

            //Set backdrop here
            backdrop = view.findViewById(R.id.movie_backdrop);
            String backdropPath = movieInfo.get("backdrop");
            Glide.with(view).clear(backdrop);
            String urlToBackdrop = String.format("https://image.tmdb.org/t/p/w500%s", backdropPath);
            Glide.with(view).load(urlToBackdrop).into(backdrop);

            // Initialize integer, id, for usage in addToFavorite()
            id = Integer.parseInt(movieInfo.get("id"));

            // Initializing the favorite icon and textView
            favorite = view.findViewById(R.id.movie_favorite);
            database = AppDatabase.getAppDatabase(getContext());

            favoriteTextView = view.findViewById(R.id.favorite_textview);
            if (database.favoriteDAO().getFavorite(id) != null) {
                favoriteTextView.setText("This movie is your favorite");
                favorite.setImageDrawable(AppCompatResources.getDrawable(getContext(), android.R.drawable.btn_star_big_on));
            } else {
                favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addToFavorite(view);
                    }
                });
            }

            // Insert all the information
            title = view.findViewById(R.id.movie_title);
            title.setText(movieInfo.get("title"));

            releaseDate = view.findViewById(R.id.release_date);
            releaseDate.setText(movieInfo.get("release"));

            overview = view.findViewById(R.id.movie_overview);
            overview.setText(movieInfo.get("overview"));

            homepage = view.findViewById(R.id.movie_homepage);
            String homepageText = movieInfo.get("homepage");
            if (homepageText == null) {
                homepageText = "No homepage for this movie";
            }
            homepage.setText(homepageText);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addToFavorite(View view) {
        String movieTitle = title.getText().toString();
        database = AppDatabase.getAppDatabase(getContext());
        Favorite favoritemovie = new Favorite();
        favoritemovie.mid = id;
        database.favoriteDAO().addFavorite(favoritemovie);
        CharSequence text = movieTitle + " added to your favorites";
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

        // update UI elements
        favoriteTextView.setText("This movie is your favorite");
        favorite.setImageDrawable(AppCompatResources.getDrawable(getContext(), android.R.drawable.btn_star_big_on));
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
