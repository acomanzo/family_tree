package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.family_tree_temp.Activities.WelcomeActivity;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.Models.FamilyTree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FamilyTreeViewModel extends AndroidViewModel {

    private MutableLiveData<List<FamilyTree>> mAllFamilyTrees;
    private FamilyTreeSqlDatabase familyTreeSqlDatabase;

    public FamilyTreeViewModel(@NonNull Application application) {
        super(application);

        familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
        mAllFamilyTrees = new MutableLiveData<>();

        initialize();
    }

    private void initialize() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            JSONArray response = null;
            try {
                response = familyTreeSqlDatabase.selectAllFamilyTrees();
                ArrayList<FamilyTree> familyTrees = new ArrayList<>();
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        int familyTreeId = object.getInt("FamilyTreeId");
                        int appUserId = object.getInt("AppUserId");
                        String treeName = object.getString("TreeName");
                        FamilyTree familyTree = new FamilyTree(familyTreeId, appUserId, treeName);
                        familyTrees.add(familyTree);
                    }
                }
                mAllFamilyTrees.postValue(familyTrees);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public LiveData<List<FamilyTree>> getmAllFamilyTrees() {
        return mAllFamilyTrees;
    }

    public List<FamilyTree> getFamilyTreesByAppUserId(int appUserId) {
        ArrayList<FamilyTree> familyTrees = new ArrayList<>();
        for (FamilyTree familyTree : mAllFamilyTrees.getValue()) {
            if (familyTree.getAppUserId() == appUserId) {
                familyTrees.add(familyTree);
            }
        }
        return familyTrees;
    }

    public void insert(FamilyTree familyTree) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            FamilyTree response = familyTreeSqlDatabase.insertFamilyTree(familyTree);
            if (response != null) {
                familyTree.setFamilyTreeId(response.getFamilyTreeId());
                List<FamilyTree> familyTrees = mAllFamilyTrees.getValue();
                familyTrees.add(familyTree);
                mAllFamilyTrees.postValue(familyTrees);
            }
        });
    }

    public FamilyTree getFamilyTreeAtIndex(int index, int appUserId) {
        List<FamilyTree> usersTrees = getFamilyTreesByAppUserId(appUserId);
        return usersTrees.get(index);
    }
}
