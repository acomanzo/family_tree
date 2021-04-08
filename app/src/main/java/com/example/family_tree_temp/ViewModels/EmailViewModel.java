package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Models.Email;
import com.example.family_tree_temp.Repository.EmailRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EmailViewModel extends AndroidViewModel {
    private EmailRepository mRepository;

    private LiveData<List<Email>> mAllEmails;

    public EmailViewModel (Application application) {
        super(application);
        mRepository = new EmailRepository(application);
        mAllEmails = mRepository.getAllEmails();
        Log.i("FamilyMemberViewModel", "Created FamilyMemberViewModel");
    }

    public LiveData<List<Email>> getAllEmails() {
        return mAllEmails;
    }

    public void insert(Email email) {
        mRepository.insertEmail(email);
    }

    public void update(Email email) {
        mRepository.updateEmail(email);
    }

    public void delete(Email email) {
        mRepository.deleteEmail(email);
    }

    public Email getEmailAtIndex(int position) {
        return mAllEmails.getValue().get(position);
    }
}
