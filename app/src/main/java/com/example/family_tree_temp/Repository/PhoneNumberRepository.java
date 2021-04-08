package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.PhoneNumberDao;
import com.example.family_tree_temp.Models.PhoneNumber;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PhoneNumberRepository {

    private PhoneNumberDao mPhoneNumberDao;
    private LiveData<List<PhoneNumber>> mAllPhoneNumbers;

    public PhoneNumberRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mPhoneNumberDao = db.phoneNumberDao();
        mAllPhoneNumbers = mPhoneNumberDao.getAllPhoneNumbers();

    }

    public LiveData<List<PhoneNumber>> getAllPhoneNumbers() {
        return mAllPhoneNumbers;
    }

    private static class insertPhoneNumberTask extends AsyncTask<PhoneNumber, Void, Void> {
        private PhoneNumberDao mAsyncTaskDao;

        insertPhoneNumberTask(PhoneNumberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PhoneNumber... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
