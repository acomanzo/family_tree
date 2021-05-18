package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.PhoneNumberDao;
import com.example.family_tree_temp.Models.PhoneNumber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class PhoneNumberRepository {

    private final PhoneNumberDao mPhoneNumberDao;
    private final LiveData<List<PhoneNumber>> mAllPhoneNumbers;

    public PhoneNumberRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mPhoneNumberDao = db.phoneNumberDao();
        mAllPhoneNumbers = mPhoneNumberDao.getAllPhoneNumbers();
    }

    public LiveData<List<PhoneNumber>> getAllPhoneNumbers() {
        return mAllPhoneNumbers;
    }

    public void insertPhoneNumber(PhoneNumber phoneNumber) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            PhoneNumber newPhoneNumber = familyTreeSqlDatabase.insertPhoneNumber(phoneNumber);
            handler.post(() -> new PhoneNumberRepository.insertPhoneNumberAsyncTask(mPhoneNumberDao).execute(newPhoneNumber));
        });
    }

    public void updatePhoneNumber(PhoneNumber phoneNumber) {
        // TODO
    }

    public void deletePhoneNumber(PhoneNumber phoneNumber) {
        // TODO
    }

    private static class insertPhoneNumberAsyncTask extends AsyncTask<PhoneNumber, Void, Void> {
        private final PhoneNumberDao mAsyncTaskDao;

        insertPhoneNumberAsyncTask(PhoneNumberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PhoneNumber... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
