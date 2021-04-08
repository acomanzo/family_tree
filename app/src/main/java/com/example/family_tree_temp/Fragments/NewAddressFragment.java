package com.example.family_tree_temp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Models.Address;
import com.example.family_tree_temp.Models.Email;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.AddressViewModel;
import com.example.family_tree_temp.ViewModels.EmailViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAddressFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int contactInformationServerId;
    private int contactInformationId;

    public NewAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewAddressFragment newInstance(String param1, String param2) {
        NewAddressFragment fragment = new NewAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            contactInformationServerId = getArguments().getInt("contactInformationServerId");
            contactInformationId = getArguments().getInt("contactInformationId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_address, container, false);

        Button addressButton = view.findViewById(R.id.new_address_button);
        addressButton.setOnClickListener(v -> {
            String number = ((TextInputEditText) view.findViewById(R.id.new_house_number)).getText().toString();
            String street = ((TextInputEditText) view.findViewById(R.id.new_street)).getText().toString();
            String city = ((TextInputEditText) view.findViewById(R.id.new_city)).getText().toString();
            String state = ((TextInputEditText) view.findViewById(R.id.new_state)).getText().toString();
            String zipcode = ((TextInputEditText) view.findViewById(R.id.new_zipcode)).getText().toString();
            Address address = new Address(contactInformationId, Integer.valueOf(number), street, city, state, zipcode, contactInformationServerId);

            AddressViewModel mAddressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);
            mAddressViewModel.insert(address);

            getParentFragmentManager().popBackStack();
        });
        return view;
    }
}