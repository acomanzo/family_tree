package com.example.family_tree_temp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.family_tree_temp.Database.FamilyTreeRoomDatabase;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.DatabaseAccessObjects.AddressDao;
import com.example.family_tree_temp.Models.Address;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class AddressRepository {

    private final AddressDao mAddressDao;
    private final LiveData<List<Address>> mAllAddresses;

    public AddressRepository(Application application) {
        FamilyTreeRoomDatabase db = FamilyTreeRoomDatabase.getDatabase(application);
        mAddressDao = db.addressDao();
        mAllAddresses = mAddressDao.getAllAddresses();
    }

    public LiveData<List<Address>> getAllAddresses() {
        return mAllAddresses;
    }

    public void insertAddress(Address address) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            Address newAddress = familyTreeSqlDatabase.insertAddress(address);
            handler.post(() -> new AddressRepository.InsertAddressAsyncTask(mAddressDao).execute(newAddress));
        });
    }

    public void updateAddress(Address address) {
        // TODO
    }

    public void deleteAddress(Address address) {
        // TODO
    }

    private static class InsertAddressAsyncTask extends AsyncTask<Address, Void, Void> {
        private final AddressDao mAsyncTaskDao;

        InsertAddressAsyncTask(AddressDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Address... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
