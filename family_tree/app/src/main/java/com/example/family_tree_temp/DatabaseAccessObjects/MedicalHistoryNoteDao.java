package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.MedicalHistory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MedicalHistoryNoteDao {

    @Insert
    void insert(MedicalHistory medicalHistory);

    @Query("DELETE FROM MedicalHistory")
    void deleteAll();

    @Query("SELECT * FROM MedicalHistory")
    LiveData<List<MedicalHistory>> getAllMedicalHistoryNotes();
}
