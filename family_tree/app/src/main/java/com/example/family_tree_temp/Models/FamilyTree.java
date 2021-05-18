package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;

public class FamilyTree {

    private int familyTreeId;

    private int appUserId;

    private String treeName;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    public FamilyTree(int appUserId, String treeName) {
        this(-1, appUserId, treeName);
    }

    public FamilyTree(int familyTreeId, int appUserId, String treeName) {
        this(familyTreeId, appUserId, treeName, "", "");
    }

    public FamilyTree(int familyTreeId, int appUserId, String treeName, @NonNull String createdAt, @NonNull String updatedAt) {
        this.familyTreeId = familyTreeId;
        this.appUserId = appUserId;
        this.treeName = treeName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getFamilyTreeId() {
        return familyTreeId;
    }

    public void setFamilyTreeId(int familyTreeId) {
        this.familyTreeId = familyTreeId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
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
