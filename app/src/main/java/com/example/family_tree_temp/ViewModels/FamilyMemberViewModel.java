package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Repository.FamilyMemberRepository;
import com.example.family_tree_temp.Models.FamilyMember;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FamilyMemberViewModel extends AndroidViewModel {

    private FamilyMemberRepository mRepository;

    private LiveData<List<FamilyMember>> mAllFamilyMembers;

    public FamilyMemberViewModel (Application application) {
        super(application);
        mRepository = new FamilyMemberRepository(application);
        mAllFamilyMembers = mRepository.getAllFamilyMembers();
        Log.i("FamilyMemberViewModel", "Created FamilyMemberViewModel");
    }

    public LiveData<List<FamilyMember>> getAllFamilyMembers() {
        return mAllFamilyMembers;
    }

    public void insert(FamilyMember familyMember) {
        mRepository.insertFamilyMember(familyMember);
    }

    public void update(FamilyMember familyMember) {
        mRepository.updateFamilyMember(familyMember);
    }

    public void delete(FamilyMember familyMember) {
        mRepository.deleteFamilyMember(familyMember);
    }

    public FamilyMember getFamilyMemberAtIndex(int position) {
        return mAllFamilyMembers.getValue().get(position);
    }

    public void insertDescendant(AncestorDescendantBundle ancestorDescendantBundle) {
        mRepository.insertDescendant(ancestorDescendantBundle);
    }

    public void insertAncestor(AncestorDescendantBundle ancestorDescendantBundle) {
        mRepository.insertAncestor(ancestorDescendantBundle);
    }

    public void getContactInformationId(FamilyMember familyMember) {

    }
}
