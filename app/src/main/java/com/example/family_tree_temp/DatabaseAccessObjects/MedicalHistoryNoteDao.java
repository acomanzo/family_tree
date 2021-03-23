package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Models.MedicalHistoryNote;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MedicalHistoryNoteDao {

    @Insert
    void insert(MedicalHistoryNote medicalHistoryNote);

    @Query("DELETE FROM MedicalHistoryNote")
    void deleteAll();

    @Query("SELECT * FROM MedicalHistoryNote")
    LiveData<List<MedicalHistoryNote>> getAllMedicalHistoryNotes();
}
