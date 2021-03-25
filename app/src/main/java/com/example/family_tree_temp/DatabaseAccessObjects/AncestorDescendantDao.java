package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.AncestorDescendant;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AncestorDescendantDao {

    @Insert
    void insert(AncestorDescendant ancestorDescendant);

    @Query("DELETE FROM AncestorDescendant")
    void deleteAll();

    @Query("SELECT * FROM AncestorDescendant")
    LiveData<List<AncestorDescendant>> getAllAncestorDescendants();
}
