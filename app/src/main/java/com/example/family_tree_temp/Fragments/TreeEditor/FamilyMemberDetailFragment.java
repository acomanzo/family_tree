package com.example.family_tree_temp.Fragments.TreeEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.family_tree_temp.Activities.TreeEditorActivity;
import com.example.family_tree_temp.Adaptors.MedicalHistoryAdapter;
import com.example.family_tree_temp.Models.ContactInformation;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.ContactInformationViewModel;
import com.example.family_tree_temp.ViewModels.EmailViewModel;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.example.family_tree_temp.ViewModels.MedicalHistoryViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyMemberDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyMemberDetailFragment extends Fragment {

    private FamilyMemberViewModel mFamilyMemberViewModel;
    private ContactInformationViewModel mContactInformationViewModel;
    private EmailViewModel mEmailViewModel;
    private MedicalHistoryViewModel medicalHistoryViewModel;

    private int position;
    private FamilyMember familyMember;

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText houseNumber;
    private TextInputEditText streetName;
    private TextInputEditText town;
    private TextInputEditText state;
    private TextInputEditText zipcode;
    private TextInputEditText gender;
    private TextInputEditText birthDate;
    private TextView descendants;

    private RecyclerView medicalHistoryRecyclerView;
    private MedicalHistoryAdapter mMedicalHistoryAdapter;
    private RecyclerView.LayoutManager layoutManager;

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
            position = getArguments().getInt("familyMemberPosition");
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            int familyTreeId = sharedPreferences.getInt(getString(R.string.family_tree_id), -1);
            familyMember = mFamilyMemberViewModel.getFamilyMemberAtIndex(position, familyTreeId);
        }

        getParentFragmentManager().setFragmentResultListener("familyMemberPosition", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int position = (Integer) result.get("familyMemberPosition");
                familyMember = mFamilyMemberViewModel.getFamilyMemberAtIndex(position);
            }
        });

        setHasOptionsMenu(true);
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
        birthDate = view.findViewById(R.id.family_member_detail_birth_date);
        descendants = view.findViewById(R.id.family_member_detail_descendants);

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
//        gender.setText(String.valueOf(familyMember.getGenderId()));
        gender.setText(familyMember.getGender());

        birthDate.setText(familyMember.getBirthDate());
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

//        mAdaptor = new FamilyMemberAdaptor((MainActivity) getActivity(), new HomeFragment.OnFamilyMemberItemClickedListener());

        mContactInformationViewModel = ViewModelProviders.of(getActivity()).get(ContactInformationViewModel.class);

        // listen for changes in mAllContactInformation in the ViewModel
        mContactInformationViewModel.getAllContactInformation().observe(getViewLifecycleOwner(), new Observer<List<ContactInformation>>() {
            @Override
            public void onChanged(List<ContactInformation> contactInformations) {
                Log.d("please", "please");

            }
        });

        medicalHistoryRecyclerView = view.findViewById(R.id.medical_history_recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        medicalHistoryRecyclerView.setLayoutManager(layoutManager);
//        mMedicalHistoryAdapter = new MedicalHistoryAdapter((MainActivity) getActivity(), new OnMedicalHistoryItemClickedListener());
        mMedicalHistoryAdapter = new MedicalHistoryAdapter((TreeEditorActivity) getActivity(), new OnMedicalHistoryItemClickedListener());

        // listen for changes in mAllMedicalHistories in the ViewModel
        medicalHistoryViewModel = ViewModelProviders.of(getActivity()).get(MedicalHistoryViewModel.class);
        medicalHistoryViewModel.getAllMedicalHistories().observe(getViewLifecycleOwner(), new Observer<List<MedicalHistory>>() {
            @Override
            public void onChanged(List<MedicalHistory> medicalHistories) {
                List<MedicalHistory> dataSet = new ArrayList<>();
                for (MedicalHistory medicalHistory : medicalHistories) {
                    if (medicalHistory.getFamilyMemberId() == familyMember.getFamilyMemberId())
                        dataSet.add(medicalHistory);
                }
                mMedicalHistoryAdapter.setDataset(dataSet);
            }
        });
        medicalHistoryRecyclerView.setAdapter(mMedicalHistoryAdapter);

        //recyclerView.setAdapter(mAdaptor);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.family_member_detail_top_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.family_member_dropdown_update:
                update();
                return true;
            case R.id.family_member_dropdown_delete:
                delete();
                return true;
            case R.id.family_member_dropdown_add_ancestor:
                addAncestor();
                return true;
            case R.id.family_member_dropdown_add_descendant:
                addDescendant();
                return true;
            case R.id.family_member_dropdown_add_email:
                addEmail();
                return true;
            case R.id.family_member_dropdown_add_phone_number:
                addPhoneNumber();
                return true;
            case R.id.family_member_dropdown_add_address:
                addAddress();
                return true;
            case R.id.family_member_dropdown_add_medical_history:
                addMedicalHistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void update() {
        // need to add code to validate input
        String updatedFirstName = firstName.getText().toString();
        String updatedLastName = lastName.getText().toString();
        String updatedHouseNumber = houseNumber.getText().toString();
        String updatedStreetName = streetName.getText().toString();
        String updatedTown = town.getText().toString();
        String updatedState = state.getText().toString();
        String updatedZipcode = zipcode.getText().toString();
        String updatedGender = gender.getText().toString();
        String updatedBirthDate = birthDate.getText().toString();

        int id = familyMember.getFamilyMemberId();
        int serverId = familyMember.getServerId();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int familyTreeId = sharedPreferences.getInt(getString(R.string.family_tree_id), -1);

        FamilyMember updatedFamilyMember = new FamilyMember(id, updatedFirstName, updatedLastName, updatedBirthDate, updatedGender, familyTreeId, serverId);
        mFamilyMemberViewModel.update(updatedFamilyMember);

//        ((MainActivity) getActivity()).transitionToHomeFromSomeView();
        ((TreeEditorActivity) getActivity()).transitionToHomeFromSomeView();
    }

    private void delete() {
        mFamilyMemberViewModel.delete(familyMember);
//        ((MainActivity) getActivity()).transitionToHomeFromSomeView();
        ((TreeEditorActivity) getActivity()).transitionToHomeFromSomeView();
    }

    private void addAncestor() {
        Bundle bundle = new Bundle();
//        bundle.putInt("familyMemberId", familyMember.getFamilyMemberId());
        bundle.putInt("familyMemberPosition", position);

//        ((MainActivity) getActivity()).transitionFromFamilyMemberDetailToAddAncestor(bundle);
        ((TreeEditorActivity) getActivity()).transitionFromFamilyMemberDetailToAddAncestor(bundle);
    }

    private void addDescendant() {
        Bundle bundle = new Bundle();
//        bundle.putInt("familyMemberId", familyMember.getFamilyMemberId());
        bundle.putInt("familyMemberPosition", position);

//        ((MainActivity) getActivity()).transitionFromFamilyMemberDetailToAddDescendant(bundle);
        ((TreeEditorActivity) getActivity()).transitionFromFamilyMemberDetailToAddDescendant(bundle);
    }

    private void addEmail() {
        ContactInformationViewModel contactInformationViewModel = ViewModelProviders.of(getActivity()).get(ContactInformationViewModel.class);
        ContactInformation contactInformation = contactInformationViewModel.getContactInformationFor(familyMember);

        Bundle bundle = new Bundle();
        bundle.putInt("contactInformationServerId", contactInformation.getServerId());
        bundle.putInt("contactInformationId", contactInformation.getContactInformationId());
        getParentFragmentManager().setFragmentResult("contactInformation", bundle);

//        ((MainActivity) getActivity()).transitionFromDetailToAddEmail(bundle);
        ((TreeEditorActivity) getActivity()).transitionFromDetailToAddEmail(bundle);
    }

    private void addPhoneNumber() {
        ContactInformationViewModel contactInformationViewModel = ViewModelProviders.of(getActivity()).get(ContactInformationViewModel.class);
        ContactInformation contactInformation = contactInformationViewModel.getContactInformationFor(familyMember);

        Bundle bundle = new Bundle();
        bundle.putInt("contactInformationServerId", contactInformation.getServerId());
        bundle.putInt("contactInformationId", contactInformation.getContactInformationId());
        getParentFragmentManager().setFragmentResult("contactInformation", bundle);

//        ((MainActivity) getActivity()).transitionFromDetailToAddPhoneNumber(bundle);
        ((TreeEditorActivity) getActivity()).transitionFromDetailToAddPhoneNumber(bundle);
    }

    public void addAddress() {
        ContactInformationViewModel contactInformationViewModel = ViewModelProviders.of(getActivity()).get(ContactInformationViewModel.class);
        ContactInformation contactInformation = contactInformationViewModel.getContactInformationFor(familyMember);

        Bundle bundle = new Bundle();
        bundle.putInt("contactInformationServerId", contactInformation.getServerId());
        bundle.putInt("contactInformationId", contactInformation.getContactInformationId());
        getParentFragmentManager().setFragmentResult("contactInformation", bundle);

//        ((MainActivity) getActivity()).transitionFromDetailToAddAddress(bundle);
        ((TreeEditorActivity) getActivity()).transitionFromDetailToAddAddress(bundle);
    }

    public void addMedicalHistory() {

        Bundle bundle = new Bundle();
        bundle.putInt("familyMemberServerId", familyMember.getServerId());
        bundle.putInt("familyMemberId", familyMember.getFamilyMemberId());
        getParentFragmentManager().setFragmentResult("familyMember", bundle);

//        ((MainActivity) getActivity()).transitionFromDetailToAddMedicalHistory(bundle);
        ((TreeEditorActivity) getActivity()).transitionFromDetailToAddMedicalHistory(bundle);
    }

    public class OnMedicalHistoryItemClickedListener {
        public void onClick(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("medicalHistoryPosition", position);
            getParentFragmentManager().setFragmentResult("medicalHistoryPosition", bundle);

//            ((MainActivity) getActivity()).transitionFromFamilyMemberToMedicalHistoryDetail(bundle);
            ((TreeEditorActivity) getActivity()).transitionFromFamilyMemberToMedicalHistoryDetail(bundle);
        }
    }
}