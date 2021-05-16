package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_tree_temp.Adaptors.TreeViewAdapter;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TreeFragment newInstance(String param1, String param2) {
        TreeFragment fragment = new TreeFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tree, container, false);

        mFamilyMemberViewModel = ViewModelProviders.of(getActivity()).get(FamilyMemberViewModel.class);
//        mFamilyMemberViewModel.getAllAncestorDescendants().observe(getViewLifecycleOwner(), new Observer<List<AncestorDescendant>>() {
//            @Override
//            public void onChanged(List<AncestorDescendant> ancestorDescendants) {
//                FamilyMember root = mFamilyMemberViewModel.getRoot();
//                List<FamilyMember> list = mFamilyMemberViewModel.makeFamilyTree(root);
//                mAdaptor.setDataSet(list);
//            }
//        });

//        FamilyMember tony = new FamilyMember("tony", "comanzo", "2/7/2000", "male");
//        FamilyMember sophie = new FamilyMember("Sophie", "Li", "3/4/1999", "female");
//        sophie.addChild(tony);
//        List<FamilyMember> familyMembers = new ArrayList<>();
//        familyMembers.add(sophie);


        List<FamilyMember> rootList = new ArrayList<>();
        List<FamilyMember> roots = mFamilyMemberViewModel.getRoots();
        for (FamilyMember familyMember : roots) {
            mFamilyMemberViewModel.makeFamilyTreeFor(familyMember);
            rootList.add(familyMember);
        }
//        List<FamilyMember> familyMembers = mFamilyMemberViewModel.makeFamilyTree(root);
//        root.setChildren(familyMembers);


//        rootList.add(root);
        mAdaptor = new TreeViewAdapter(rootList);

        recyclerView = view.findViewById(R.id.tree_recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdaptor);

        return view;
    }
}