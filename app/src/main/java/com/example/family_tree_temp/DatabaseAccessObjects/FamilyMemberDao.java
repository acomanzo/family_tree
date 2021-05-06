package com.example.family_tree_temp.DatabaseAccessObjects;

import com.example.family_tree_temp.Models.FamilyMember;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FamilyMemberDao {

    @Insert
    long insert(FamilyMember familyMember);

    @Query("DELETE FROM FamilyMember")
    void deleteAll();

    @Query("SELECT * FROM FamilyMember")
    LiveData<List<FamilyMember>> getAllFamilyMembers();

//    @Query("SELECT * FROM FamilyMember WHERE FamilyMemberId = :id")
//    LiveData<List<FamilyMember>> getFamilyMemberById(int id);
    @Query("SELECT * FROM FamilyMember WHERE FamilyMemberId = :id")
    List<FamilyMember> getFamilyMemberById(int id);

    @Update
    void update(FamilyMember familyMember);

    @Delete
    void delete(FamilyMember familyMember);
}
