package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.EmailDao;
import com.example.family_tree_temp.Models.Email;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EmailRepository {

    private EmailDao mEmailDao;
    private LiveData<List<Email>> mAllEmails;

    public EmailRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mEmailDao = db.emailDao();
        mAllEmails = mEmailDao.getAllEmails();

    }

    public LiveData<List<Email>> getAllEmails() {
        return mAllEmails;
    }

    private static class insertEmailAsyncTask extends AsyncTask<Email, Void, Void> {
        private EmailDao mAsyncTaskDao;

        insertEmailAsyncTask(EmailDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Email... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
