package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Models.PhoneNumber;
import com.example.family_tree_temp.Repository.PhoneNumberRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PhoneNumberViewModel extends AndroidViewModel {

    private final PhoneNumberRepository mRepository;

    private final LiveData<List<PhoneNumber>> mAllPhoneNumbers;

    public PhoneNumberViewModel (Application application) {
        super(application);
        mRepository = new PhoneNumberRepository(application);
        mAllPhoneNumbers = mRepository.getAllPhoneNumbers();
        Log.i("FamilyMemberViewModel", "Created FamilyMemberViewModel");
    }

    public LiveData<List<PhoneNumber>> getAllPhoneNumbers() {
        return mAllPhoneNumbers;
    }

    public void insert(PhoneNumber phoneNumber) {
        mRepository.insertPhoneNumber(phoneNumber);
    }

    public void update(PhoneNumber phoneNumber) {
        mRepository.updatePhoneNumber(phoneNumber);
    }

    public void delete(PhoneNumber phoneNumber) {
        mRepository.deletePhoneNumber(phoneNumber);
    }

    public PhoneNumber getPhoneNumberAtIndex(int position) {
        return mAllPhoneNumbers.getValue().get(position);
    }
}
