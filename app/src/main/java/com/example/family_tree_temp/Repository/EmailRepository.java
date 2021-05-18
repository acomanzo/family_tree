package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.EmailDao;
import com.example.family_tree_temp.Models.Email;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class EmailRepository {

    private final EmailDao mEmailDao;
    private final LiveData<List<Email>> mAllEmails;

    public EmailRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mEmailDao = db.emailDao();
        mAllEmails = mEmailDao.getAllEmails();

    }

    public LiveData<List<Email>> getAllEmails() {
        return mAllEmails;
    }

    public void insertEmail(Email email) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            Email newEmail = familyTreeSqlDatabase.insertEmail(email);
            handler.post(() -> new InsertEmailAsyncTask(mEmailDao).execute(newEmail));
        });
    }

    public void updateEmail(Email email) {

    }

    public void deleteEmail(Email email) {

    }

    private static class InsertEmailAsyncTask extends AsyncTask<Email, Void, Void> {
        private final EmailDao mAsyncTaskDao;

        InsertEmailAsyncTask(EmailDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Email... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
