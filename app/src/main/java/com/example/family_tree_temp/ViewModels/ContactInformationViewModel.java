package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Models.ContactInformation;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Repository.ContactInformationRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ContactInformationViewModel extends AndroidViewModel {

    private final ContactInformationRepository mRepository;

    private final LiveData<List<ContactInformation>> mAllContactInformation;

    public ContactInformationViewModel (Application application) {
        super(application);
        mRepository = new ContactInformationRepository(application);
        mAllContactInformation = mRepository.getAllContactInformation();
        Log.i("ContactInfoViewModel", "Created ContactInformationViewModel");
    }

    public LiveData<List<ContactInformation>> getAllContactInformation() {
        return mAllContactInformation;
    }

    public ContactInformation getContactInformationFor(FamilyMember familyMember) {
        List<ContactInformation> contactInformationList = mAllContactInformation.getValue();
        for (ContactInformation contactInformation : contactInformationList) {
            if (contactInformation.getFamilyMemberId() == familyMember.getFamilyMemberId()) {
                return contactInformation;
            }
        }
        return null;
    }

    public void insert(ContactInformation contactInformation) {
        mRepository.insertContactInformation(contactInformation);
    }

    public void update(ContactInformation contactInformation) {
        mRepository.updateContactInformation(contactInformation);
    }

    public void delete(ContactInformation contactInformation) {
        mRepository.deleteContactInformation(contactInformation);
    }

    public ContactInformation getContactInformationAtIndex(int position) {
        return mAllContactInformation.getValue().get(position);
    }
}
