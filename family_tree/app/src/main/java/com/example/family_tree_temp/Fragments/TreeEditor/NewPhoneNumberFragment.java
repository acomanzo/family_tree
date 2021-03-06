package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Models.PhoneNumber;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.PhoneNumberViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPhoneNumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPhoneNumberFragment extends Fragment {

    private int contactInformationServerId;
    private int contactInformationId;

    public NewPhoneNumberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewPhoneNumberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPhoneNumberFragment newInstance(String param1, String param2) {
        NewPhoneNumberFragment fragment = new NewPhoneNumberFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            contactInformationServerId = getArguments().getInt("contactInformationServerId");
            contactInformationId = getArguments().getInt("contactInformationId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_phone_number, container, false);

        Button emailButton = view.findViewById(R.id.new_phone_number_button);
        emailButton.setOnClickListener(v -> {
            String input = ((TextInputEditText) view.findViewById(R.id.new_family_member_phone_number)).getText().toString();
            PhoneNumber phoneNumber = new PhoneNumber(contactInformationId, input, contactInformationServerId);
            PhoneNumberViewModel mPhoneNumberViewModel = ViewModelProviders.of(getActivity()).get(PhoneNumberViewModel.class);
            mPhoneNumberViewModel.insert(phoneNumber);

            getParentFragmentManager().popBackStack();
        });
        return view;
    }
}