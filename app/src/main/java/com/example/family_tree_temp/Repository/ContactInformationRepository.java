package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.ContactInformationDao;
import com.example.family_tree_temp.Models.ContactInformation;
import com.example.family_tree_temp.Models.FamilyMember;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ContactInformationRepository {

    private ContactInformationDao mContactInformationDao;
    private LiveData<List<ContactInformation>> mAllContactInformation;

    public ContactInformationRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mContactInformationDao = db.contactInformationDao();
        mAllContactInformation = mContactInformationDao.getAllContactInformation();

    }

    public LiveData<List<ContactInformation>> getAllContactInformation() {
        return mAllContactInformation;
    }

    public void insertContactInformation(ContactInformation contactInformation) {

    }

    public void updateContactInformation(ContactInformation contactInformation) {

    }

    public void deleteContactInformation(ContactInformation contactInformation) {

    }

    private static class insertContactInformationAsyncTask extends AsyncTask<ContactInformation, Void, Void> {
        private ContactInformationDao mAsyncTaskDao;

        insertContactInformationAsyncTask(ContactInformationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ContactInformation... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
