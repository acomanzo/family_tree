package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.Gender;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GenderDao {

    @Insert
    void insert(Gender gender);

    @Query("DELETE FROM Gender")
    void deleteAll();

    @Query("SELECT * FROM Gender")
    LiveData<List<Gender>> getAllGenders();
}
