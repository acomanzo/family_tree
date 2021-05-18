package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_tree_temp.Adapters.TreeViewAdapter;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TreeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TreeViewAdapter mAdaptor;
    private RecyclerView.LayoutManager layoutManager;

    private FamilyMemberViewModel mFamilyMemberViewModel;

    private int familyTreeId;

    public TreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TreeFragment newInstance(String param1, String param2) {
        TreeFragment fragment = new TreeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            familyTreeId = getArguments().getInt(getString(R.string.family_tree_id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tree, container, false);

        mFamilyMemberViewModel = ViewModelProviders.of(getActivity()).get(FamilyMemberViewModel.class);

        List<FamilyMember> rootList = new ArrayList<>();
        List<FamilyMember> roots = mFamilyMemberViewModel.getRoots(familyTreeId);
        for (FamilyMember familyMember : roots) {
            mFamilyMemberViewModel.makeFamilyTreeFor(familyMember);
            rootList.add(familyMember);
        }
        mAdaptor = new TreeViewAdapter(rootList);

        recyclerView = view.findViewById(R.id.tree_recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdaptor);

        return view;
    }
}