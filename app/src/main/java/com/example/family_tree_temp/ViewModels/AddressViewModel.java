package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Models.Address;
import com.example.family_tree_temp.Repository.AddressRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AddressViewModel extends AndroidViewModel {
    private final AddressRepository mRepository;

    private final LiveData<List<Address>> mAllAddresses;

    public AddressViewModel (Application application) {
        super(application);
        mRepository = new AddressRepository(application);
        mAllAddresses = mRepository.getAllAddresses();
        Log.i("FamilyMemberViewModel", "Created FamilyMemberViewModel");
    }

    public LiveData<List<Address>> getAllAddresses() {
        return mAllAddresses;
    }

    public void insert(Address address) {
        mRepository.insertAddress(address);
    }

    public void update(Address address) {
        mRepository.updateAddress(address);
    }

    public void delete(Address address) {
        mRepository.deleteAddress(address);
    }

    public Address getAddressAtIndex(int position) {
        return mAllAddresses.getValue().get(position);
    }
}
