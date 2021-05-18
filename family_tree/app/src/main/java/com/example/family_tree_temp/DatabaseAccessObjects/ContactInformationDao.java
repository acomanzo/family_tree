package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.ContactInformation;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ContactInformationDao {

    @Insert
    void insert(ContactInformation contactInformation);

    @Query("DELETE FROM ContactInformation")
    void deleteAll();

    @Query("SELECT * FROM ContactInformation")
    LiveData<List<ContactInformation>> getAllContactInformation();
}
