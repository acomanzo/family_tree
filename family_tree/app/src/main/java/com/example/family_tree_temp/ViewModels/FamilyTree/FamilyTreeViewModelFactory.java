package com.example.family_tree_temp.ViewModels.FamilyTree;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FamilyTreeViewModelFactory implements ViewModelProvider.Factory {
    private final Application mApplication;
    private final int mAppUserId;

    public FamilyTreeViewModelFactory(Application application, int appUserId) {
        mApplication = application;
        mAppUserId = appUserId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FamilyTreeViewModel(mApplication, mAppUserId);
    }
}
