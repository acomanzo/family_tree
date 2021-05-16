package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Activities.TreeEditorActivity;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.FamilyTree.FamilyTreeViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareTreeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button shareButton;
    private int familyTreeId;
    private int appUserId;
    private FamilyTreeViewModel mFamilyTreeViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShareTreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareTreeFragment newInstance(String param1, String param2) {
        ShareTreeFragment fragment = new ShareTreeFragment();
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

            familyTreeId = getArguments().getInt(getString(R.string.family_tree_id), -1);
            appUserId = getArguments().getInt(getString(R.string.app_user_id), -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_tree, container, false);

        mFamilyTreeViewModel = ViewModelProviders.of(getActivity()).get(FamilyTreeViewModel.class);

        shareButton = view.findViewById(R.id.share_tree_share_button);
        shareButton.setOnClickListener(v -> {
            String email = ((TextInputEditText) view.findViewById(R.id.share_tree_email)).getText().toString();
            mFamilyTreeViewModel.shareFamilyTree(appUserId, familyTreeId, email);
            ((TreeEditorActivity) getActivity()).transitionToHomeFromSomeView();
        });

        return view;
    }
}