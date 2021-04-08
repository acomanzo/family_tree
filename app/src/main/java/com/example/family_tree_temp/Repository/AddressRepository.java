package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.AddressDao;
import com.example.family_tree_temp.Models.Address;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AddressRepository {

    private AddressDao mAddressDao;
    private LiveData<List<Address>> mAllAddresses;

    public AddressRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mAddressDao = db.addressDao();
        mAllAddresses = mAddressDao.getAllAddresses();
    }

    public LiveData<List<Address>> getAllAddresses() {
        return mAllAddresses;
    }

    private static class insertAddressAsyncTask extends AsyncTask<Address, Void, Void> {
        private AddressDao mAsyncTaskDao;

        insertAddressAsyncTask(AddressDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Address... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
