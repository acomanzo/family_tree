package com.example.family_tree_temp.Models;

public class FamilyTree {

    private int familyTreeId;

    private int appUserId;

    private String treeName;

    public FamilyTree(int appUserId, String treeName) {
        this(-1, appUserId, treeName);
    }

    public FamilyTree(int familyTreeId, int appUserId, String treeName) {
        this.familyTreeId = familyTreeId;
        this.appUserId = appUserId;
        this.treeName = treeName;
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
}
