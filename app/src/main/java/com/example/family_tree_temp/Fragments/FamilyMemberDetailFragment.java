package com.example.family_tree_temp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.family_tree_temp.Activities.MainActivity;
import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyMemberDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyMemberDetailFragment extends Fragment {

    private FamilyMemberViewModel mFamilyMemberViewModel;

    private FamilyMember familyMember;

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText houseNumber;
    private TextInputEditText streetName;
    private TextInputEditText town;
    private TextInputEditText state;
    private TextInputEditText zipcode;
    private TextInputEditText gender;
    private TextView descendants;

    private Button addAncestorButton;
    private Button addDescendantButton;
    private Button updateButton;
    private Button deleteButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnUpdateListener updateCallback;
    private OnDeleteListener deleteCallback;

    public void setOnUpdateListener(OnUpdateListener updateCallback) {
        this.updateCallback = updateCallback;
    }

    public interface OnUpdateListener {
        void onFamilyMemberUpdated();
    }

    public void setOnDeleteListener(OnDeleteListener deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    public interface OnDeleteListener {
        void onFamilyMemberDeleted();
    }

    public FamilyMemberDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyMemberDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyMemberDetailFragment newInstance(String param1, String param2) {
        FamilyMemberDetailFragment fragment = new FamilyMemberDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFamilyMemberViewModel = ViewModelProviders.of(getActivity()).get(FamilyMemberViewModel.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            familyMember = mFamilyMemberViewModel.getFamilyMemberAtIndex(getArguments().getInt("familyMemberPosition"));
        }

        getParentFragmentManager().setFragmentResultListener("familyMemberPosition", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int position = (Integer) result.get("familyMemberPosition");
                familyMember = mFamilyMemberViewModel.getFamilyMemberAtIndex(position);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_member_detail, container, false);

        firstName = view.findViewById(R.id.family_member_detail_first_name);
        lastName = view.findViewById(R.id.family_member_detail_last_name);
        houseNumber = view.findViewById(R.id.family_member_detail_house_number);
        streetName = view.findViewById(R.id.family_member_detail_street_name);
        town = view.findViewById(R.id.family_member_detail_town);
        state = view.findViewById(R.id.family_member_detail_state);
        zipcode = view.findViewById(R.id.family_member_detail_zipcode);
        gender = view.findViewById(R.id.family_member_detail_gender);
        descendants = view.findViewById(R.id.family_member_detail_descendants);

        addAncestorButton = view.findViewById(R.id.family_member_detail_add_ancestor_button);
        addDescendantButton = view.findViewById(R.id.family_member_detail_add_descendant_button);
        updateButton = view.findViewById(R.id.family_member_detail_update_button);
        deleteButton = view.findViewById(R.id.family_member_detail_delete_button);

        mFamilyMemberViewModel = ViewModelProviders.of(getActivity()).get(FamilyMemberViewModel.class);

        // listen for changes in mAllFamilyMembers in the ViewModel
        mFamilyMemberViewModel.getAllFamilyMembers();

        firstName.setText(familyMember.getFirstName());
        lastName.setText(familyMember.getLastName());
        //Address address = person.getAddress();
//        houseNumber.setText(address.getStreetNumber());
//        streetName.setText(address.getStreetName());
//        town.setText(address.getTownCity());
//        state.setText(address.getState());
//        zipcode.setText(address.getZipcode());
        gender.setText(String.valueOf(familyMember.getGenderId()));

//        ArrayList<Person> children = person.getChildren();
//        if (children.size() > 0) {
//            String descendantsStr = "";
//            for (Person p : children) {
//                descendantsStr = descendantsStr + p.toString() + ", ";
//            }
//            descendantsStr = descendantsStr.substring(0, descendantsStr.length() - 1);
//            descendants.setText(descendantsStr);
//        } else {
//            descendants.setText("No children.");
//        }

        updateButton.setOnClickListener(v -> {

            // need to add code to validate input
            String updatedFirstName = firstName.getText().toString();
            String updatedLastName = lastName.getText().toString();
            String updatedHouseNumber = houseNumber.getText().toString();
            String updatedStreetName = streetName.getText().toString();
            String updatedTown = town.getText().toString();
            String updatedState = state.getText().toString();
            String updatedZipcode = zipcode.getText().toString();
            String updatedGender = gender.getText().toString();

            int id = familyMember.getFamilyMemberId();

            FamilyMember updatedFamilyMember = new FamilyMember(id, updatedFirstName, updatedLastName, 10, Integer.valueOf(updatedGender));
            mFamilyMemberViewModel.update(updatedFamilyMember);

            ((MainActivity) getActivity()).transitionToHomeFromSomeView();
        });

        deleteButton.setOnClickListener(v -> {
            mFamilyMemberViewModel.delete(familyMember);
            ((MainActivity) getActivity()).transitionToHomeFromSomeView();
        });

        addAncestorButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("familyMemberId", familyMember.getFamilyMemberId());

            ((MainActivity) getActivity()).transitionFromFamilyMemberDetailToAddAncestor(bundle);
        });

        addDescendantButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("familyMemberId", familyMember.getFamilyMemberId());

            ((MainActivity) getActivity()).transitionFromFamilyMemberDetailToAddDescendant(bundle);
        });

        return view;
    }
}