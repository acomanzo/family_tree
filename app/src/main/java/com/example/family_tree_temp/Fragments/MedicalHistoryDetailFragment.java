package com.example.family_tree_temp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.example.family_tree_temp.ViewModels.MedicalHistoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalHistoryDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalHistoryDetailFragment extends Fragment {

    private MedicalHistoryViewModel mMedicalHistoryViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MedicalHistory medicalHistory;

    private TextView diagnosisName;
    private TextView diagnosisDate;

    public MedicalHistoryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicalHistoryDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicalHistoryDetailFragment newInstance(String param1, String param2) {
        MedicalHistoryDetailFragment fragment = new MedicalHistoryDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMedicalHistoryViewModel = ViewModelProviders.of(getActivity()).get(MedicalHistoryViewModel.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            int index = getArguments().getInt("medicalHistoryPosition");
            medicalHistory = mMedicalHistoryViewModel.getMedicalHistoryAtIndex(index);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_history_detail, container, false);

        diagnosisName = view.findViewById(R.id.mh_detail_diagnosis_name);
        diagnosisDate = view.findViewById(R.id.mh_detail_diagnosis_date);

        diagnosisName.setText(medicalHistory.getDiagnosis());
        diagnosisDate.setText(medicalHistory.getDateDiagnosed());

        return view;
    }
}