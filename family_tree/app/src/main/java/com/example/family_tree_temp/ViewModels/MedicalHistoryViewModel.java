package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.Repository.MedicalHistoryRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MedicalHistoryViewModel extends AndroidViewModel {
    private final MedicalHistoryRepository mRepository;

    private final LiveData<List<MedicalHistory>> mAllMedicalHistories;

    public MedicalHistoryViewModel (Application application) {
        super(application);
        mRepository = new MedicalHistoryRepository(application);
        mAllMedicalHistories = mRepository.getAllMedicalHistories();
        Log.i("FamilyMemberViewModel", "Created FamilyMemberViewModel");
    }

    public LiveData<List<MedicalHistory>> getAllMedicalHistories() {
        return mAllMedicalHistories;
    }

    public void insert(MedicalHistory medicalHistory) {
        mRepository.insertMedicalHistory(medicalHistory);
    }

    public void update(MedicalHistory medicalHistory) {
        mRepository.updateMedicalHistory(medicalHistory);
    }

    public void delete(MedicalHistory medicalHistory) {
        mRepository.deleteMedicalHistory(medicalHistory);
    }

    public MedicalHistory getMedicalHistoryAtIndex(int position) {
        return mAllMedicalHistories.getValue().get(position);
    }
}
