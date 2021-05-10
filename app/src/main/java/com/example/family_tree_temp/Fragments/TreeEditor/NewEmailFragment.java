package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Models.Email;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.EmailViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewEmailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int contactInformationServerId;
    private int contactInformationId;

    public NewEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewEmailFragment newInstance(String param1, String param2) {
        NewEmailFragment fragment = new NewEmailFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_email, container, false);

        Button emailButton = view.findViewById(R.id.new_email_button);
        emailButton.setOnClickListener(v -> {
            String input = ((TextInputEditText) view.findViewById(R.id.new_family_member_email)).getText().toString();
            Email email = new Email(contactInformationId, input, contactInformationServerId);
            EmailViewModel mEmailViewModel = ViewModelProviders.of(getActivity()).get(EmailViewModel.class);
            mEmailViewModel.insert(email);

            getParentFragmentManager().popBackStack();
        });
        return view;
    }
}