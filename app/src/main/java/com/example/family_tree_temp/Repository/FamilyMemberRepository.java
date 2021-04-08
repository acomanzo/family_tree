package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.AncestorDescendantDao;
import com.example.family_tree_temp.DatabaseAccessObjects.FamilyMemberDao;
import com.example.family_tree_temp.Models.AncestorDescendant;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.ViewModels.AncestorDescendantBundle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class FamilyMemberRepository {

    private FamilyMemberDao mFamilyMemberDao;
    private AncestorDescendantDao mAncestorDescendantDao;

    private LiveData<List<FamilyMember>> mAllFamilyMembers;

    public FamilyMemberRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mFamilyMemberDao = db.familyMemberDao();
        mAllFamilyMembers = mFamilyMemberDao.getAllFamilyMembers();

        mAncestorDescendantDao = db.ancestorDescendantDao();
    }

    public LiveData<List<FamilyMember>> getAllFamilyMembers() {
        return mAllFamilyMembers;
    }

    public void insertFamilyMember(FamilyMember familyMember) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            String id = familyTreeSqlDatabase.insertFamilyMember(familyMember);
            familyMember.setServerId(Integer.valueOf(id));
            handler.post(() -> {
                new insertFamilyMemberAsyncTask(mFamilyMemberDao).execute(familyMember);
            });
        });
    }

    public void updateFamilyMember(FamilyMember familyMember) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            familyTreeSqlDatabase.updateFamilyMember(familyMember);
            handler.post(() -> {
                new UpdateFamilyMemberAsyncTask(mFamilyMemberDao).execute(familyMember);            });
        });
    }

    public void deleteFamilyMember(FamilyMember familyMember) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            familyTreeSqlDatabase.deleteFamilyMember(familyMember);
            handler.post(() -> {
                new DeleteFamilyMemberAsyncTask(mFamilyMemberDao).execute(familyMember);            });
        });
    }

    public void insertDescendant(AncestorDescendantBundle ancestorDescendantBundle) {
        new InsertDescendantAsyncTask(mAncestorDescendantDao, mFamilyMemberDao).execute(ancestorDescendantBundle);
    }

    public void insertAncestor(AncestorDescendantBundle ancestorDescendantBundle) {
        new InsertAncestorAsyncTask(mAncestorDescendantDao, mFamilyMemberDao).execute(ancestorDescendantBundle);
    }

    private static class insertFamilyMemberAsyncTask extends AsyncTask<FamilyMember, Void, Void> {

        private FamilyMemberDao mAsyncTaskDao;

        insertFamilyMemberAsyncTask(FamilyMemberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FamilyMember... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class UpdateFamilyMemberAsyncTask extends AsyncTask<FamilyMember, Void, Void> {
        private FamilyMemberDao mAsyncTaskDao;

        UpdateFamilyMemberAsyncTask(FamilyMemberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FamilyMember... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class DeleteFamilyMemberAsyncTask extends AsyncTask<FamilyMember, Void, Void> {
        private FamilyMemberDao mAsyncTaskDao;

        DeleteFamilyMemberAsyncTask(FamilyMemberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FamilyMember... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class InsertDescendantAsyncTask extends AsyncTask<AncestorDescendantBundle, Void, Void> {
        private AncestorDescendantDao ancestorDescendantDao;
        private FamilyMemberDao familyMemberDao;

        InsertDescendantAsyncTask(AncestorDescendantDao ancestorDescendantDao, FamilyMemberDao familyMemberDao) {
            this.ancestorDescendantDao = ancestorDescendantDao;
            this.familyMemberDao = familyMemberDao;
        }

        @Override
        protected Void doInBackground(AncestorDescendantBundle... ancestorDescendantBundles) {
            AncestorDescendantBundle ancestorDescendantBundle = ancestorDescendantBundles[0];
            FamilyMember descendant = ancestorDescendantBundle.getNewFamilyMember();
            int ancestorId = ancestorDescendantBundle.getExistingFamilyMemberId();
            int depth = ancestorDescendantBundle.getDepth();

            int descendantId = (int) familyMemberDao.insert(descendant);
            AncestorDescendant ancestorDescendant = new AncestorDescendant(ancestorId, descendantId, depth);
            ancestorDescendantDao.insert(ancestorDescendant);

            return null;
        }
    }

    private static class InsertAncestorAsyncTask extends AsyncTask<AncestorDescendantBundle, Void, Void> {
        private AncestorDescendantDao ancestorDescendantDao;
        private FamilyMemberDao familyMemberDao;

        InsertAncestorAsyncTask(AncestorDescendantDao ancestorDescendantDao, FamilyMemberDao familyMemberDao) {
            this.ancestorDescendantDao = ancestorDescendantDao;
            this.familyMemberDao = familyMemberDao;
        }

        @Override
        protected Void doInBackground(AncestorDescendantBundle... ancestorDescendantBundles) {
            AncestorDescendantBundle ancestorDescendantBundle = ancestorDescendantBundles[0];
            FamilyMember ancestor = ancestorDescendantBundle.getNewFamilyMember();
            int descendantId = ancestorDescendantBundle.getExistingFamilyMemberId();
            int depth = ancestorDescendantBundle.getDepth();

            int ancestorId = (int) familyMemberDao.insert(ancestor);
            AncestorDescendant ancestorDescendant = new AncestorDescendant(ancestorId, descendantId, depth);
            ancestorDescendantDao.insert(ancestorDescendant);

            return null;
        }
    }
}
