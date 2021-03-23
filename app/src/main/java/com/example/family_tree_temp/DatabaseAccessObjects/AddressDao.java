package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.Address;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AddressDao {

    @Insert
    void insert(Address address);

    @Query("DELETE FROM Address")
    void deleteAll();

    @Query("SELECT * FROM Address")
    LiveData<List<Address>> getAllAddresses();
}
