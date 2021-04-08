package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.MedicalHistoryNoteDao;
import com.example.family_tree_temp.Models.MedicalHistory;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MedicalHistoryRepository {

    private MedicalHistoryNoteDao mMedicalHistoryNoteDao;
    private LiveData<List<MedicalHistory>> mAllMedicalHistoryNotes;

    public MedicalHistoryRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mMedicalHistoryNoteDao = db.medicalHistoryNoteDao();
        mAllMedicalHistoryNotes = mMedicalHistoryNoteDao.getAllMedicalHistoryNotes();

    }

    public LiveData<List<MedicalHistory>> getAllMedicalHistoryNotes() {
        return mAllMedicalHistoryNotes;
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
