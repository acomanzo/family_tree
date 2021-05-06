package com.example.family_tree_temp.Models;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//@Entity(tableName = "FamilyMember", foreignKeys = {@ForeignKey(
//        entity = Gender.class,
//        parentColumns = "genderId",
//        childColumns = "genderId",
//        onDelete = ForeignKey.CASCADE
//)})
@Entity(tableName = "FamilyMember")
public class FamilyMember {

    @PrimaryKey(autoGenerate = true)
    private int familyMemberId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    //private int age;
    private String birthDate;

    //private int genderId;
    private String gender;

    private int serverId;

    @Ignore
    private List<FamilyMember> children;

//    public FamilyMember(@NonNull String firstName, @NonNull String lastName, int age, int genderId) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//        this.genderId = genderId;
//    }
    public FamilyMember(@NonNull String firstName, @NonNull String lastName, String birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;

        children = new ArrayList<>();
    }

//    @Ignore
//    public FamilyMember(@NonNull String firstName, @NonNull String lastName, int age, int genderId, int serverId) {
//        this(-1, firstName, lastName, age, genderId, serverId);
//    }

    @Ignore
    public FamilyMember(@NonNull String firstName, @NonNull String lastName, String birthDate, String gender, int serverId) {
        this(-1, firstName, lastName, birthDate, gender, serverId);
    }

    @Ignore
    public FamilyMember(int familyMemberId, @NonNull String firstName, @NonNull String lastName, String birthDate, String gender, int serverId) {
        this.familyMemberId = familyMemberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.serverId = serverId;

        children = new ArrayList<>();
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

//    public int getAge() {
//        return age;
//    }

    public String getBirthDate() {
        return birthDate;
    }

//    public int getGenderId() {
//        return genderId;
//    }

    public String getGender() {
        return gender;
    }

    public int getServerId() {
        return serverId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void addChild(FamilyMember child) {
        children.add(child);
    }

    public List<FamilyMember> getChildren() {
        return children;
    }

    public void setChildren(List<FamilyMember> children) {
        this.children = children;
    }
}
