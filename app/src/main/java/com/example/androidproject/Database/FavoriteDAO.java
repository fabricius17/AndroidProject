package com.example.androidproject.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Query("SELECT * FROM Favorite WHERE mid = :id")
    Favorite getFavorite(int id);

    @Insert
    void addFavorite(Favorite id);
}
