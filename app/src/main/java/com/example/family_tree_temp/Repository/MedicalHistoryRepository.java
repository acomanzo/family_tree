package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.MedicalHistoryNoteDao;
import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.Models.MedicalHistory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class MedicalHistoryRepository {

    private MedicalHistoryNoteDao mMedicalHistoryDao;
    private LiveData<List<MedicalHistory>> mAllMedicalHistoryNotes;

    public MedicalHistoryRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mMedicalHistoryDao = db.medicalHistoryNoteDao();
        mAllMedicalHistoryNotes = mMedicalHistoryDao.getAllMedicalHistoryNotes();

    }

    public LiveData<List<MedicalHistory>> getAllMedicalHistories() {
        return mAllMedicalHistoryNotes;
    }

    public void insertMedicalHistory(MedicalHistory medicalHistory) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            String id = familyTreeSqlDatabase.insertMedicalHistory(medicalHistory);
            medicalHistory.setServerId(Integer.valueOf(id));
            handler.post(() -> new MedicalHistoryRepository.insertMedicalHistoryAsyncTask(mMedicalHistoryDao).execute(medicalHistory));
        });
    }

    public void updateMedicalHistory(MedicalHistory medicalHistory) {

    }

    public void deleteMedicalHistory(MedicalHistory medicalHistory) {

    }

    private static class insertMedicalHistoryAsyncTask extends AsyncTask<MedicalHistory, Void, Void> {
        private MedicalHistoryNoteDao mAsyncTaskDao;

        insertMedicalHistoryAsyncTask(MedicalHistoryNoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MedicalHistory... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
