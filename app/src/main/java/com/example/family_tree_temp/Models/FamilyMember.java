package com.example.family_tree_temp.Models;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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

    private String birthDate;

    private String gender;

    private int serverId;

    @NonNull
    private final int familyTreeId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    private List<FamilyMember> children;

    @Ignore
    public FamilyMember(String firstName, String lastName, String birthDate, String gender, int familyTreeId) {
        this(firstName, lastName, birthDate, gender, familyTreeId, "", "");
    }

    public FamilyMember(@NonNull String firstName, @NonNull String lastName, String birthDate, String gender, int familyTreeId, @NonNull String createdAt, @NonNull String updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.familyTreeId = familyTreeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        children = new ArrayList<>();
    }

    @Ignore
    public FamilyMember(@NonNull String firstName, @NonNull String lastName, String birthDate, String gender, int familyTreeId, int serverId) {
        this(-1, firstName, lastName, birthDate, gender, familyTreeId, serverId);
    }

    @Ignore
    public FamilyMember(int familyMemberId, @NonNull String firstName, @NonNull String lastName, String birthDate, String gender, int familyTreeId, int serverId) {
        this.familyMemberId = familyMemberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.serverId = serverId;
        this.familyTreeId = familyTreeId;

        children = new ArrayList<>();
    }

    @Ignore
    public FamilyMember(int familyMemberId, @NonNull String firstName, @NonNull String lastName, String birthDate, String gender, int familyTreeId, int serverId, String createdAt, String updatedAt) {
        this.familyMemberId = familyMemberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.serverId = serverId;
        this.familyTreeId = familyTreeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        children = new ArrayList<>();
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public int getServerId() {
        return serverId;
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

    public void setFamilyTreeId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public int getFamilyTreeId() {
        return familyTreeId;
    }

    @NonNull
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NonNull String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
