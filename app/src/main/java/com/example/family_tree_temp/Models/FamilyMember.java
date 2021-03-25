package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "FamilyMember", foreignKeys = {@ForeignKey(
        entity = Gender.class,
        parentColumns = "genderId",
        childColumns = "genderId",
        onDelete = ForeignKey.CASCADE
)})
public class FamilyMember {

    @PrimaryKey(autoGenerate = true)
    private int familyMemberId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private int genderId;

    public FamilyMember(@NonNull String firstName, @NonNull String lastName, @NonNull int genderId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderId = genderId;
    }

    @Ignore
    public FamilyMember(int familyMemberId, @NonNull String firstName, @NonNull String lastName, @NonNull int genderId) {
        this.familyMemberId = familyMemberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderId = genderId;
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

    public int getGenderId() {
        return genderId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }
}
