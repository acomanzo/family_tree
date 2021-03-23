package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "FamilyMember")
public class FamilyMember {

    @PrimaryKey(autoGenerate = true)
    private int familyMemberId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String gender;

    public FamilyMember(@NonNull String firstName, @NonNull String lastName, @NonNull String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    @Ignore
    public FamilyMember(int familyMemberId, @NonNull String firstName, @NonNull String lastName, @NonNull String gender) {
        this.familyMemberId = familyMemberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }
}
