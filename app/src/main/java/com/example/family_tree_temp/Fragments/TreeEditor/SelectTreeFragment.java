package com.example.family_tree_temp.Fragments.TreeEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.family_tree_temp.Activities.TreeEditorActivity;
import com.example.family_tree_temp.Adaptors.FamilyMemberAdaptor;
import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.Adaptors.TreeListAdapter;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Models.FamilyTree;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.example.family_tree_temp.ViewModels.FamilyTreeViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTreeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int appUserId;
    private RecyclerView recyclerView;
    private TreeListAdapter mAdaptor;
    private RecyclerView.LayoutManager layoutManager;
    private FamilyTreeViewModel mFamilyTreeViewModel;

    public SelectTreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTreeFragment newInstance(String param1, String param2) {
        SelectTreeFragment fragment = new SelectTreeFragment();
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


            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            appUserId = preferences.getInt(getString(R.string.app_user_id), -1);
//            String appUser = getArguments().getString(getString(R.string.app_user));
//            try {
//                appUserId = new JSONObject(appUser).getInt("AppUserId");
//            } catch (JSONException e) {
//                e.printStackTrace();
//                appUserId = -1;
//            }
        }

        setHasOptionsMenu(true);

        getParentFragmentManager().setFragmentResultListener("newTreeKey", this, (requestKey, result) -> {
            String name = result.getString("newTreeName");
            FamilyTree familyTree = new FamilyTree(appUserId, name);
            mFamilyTreeViewModel.insert(familyTree);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_tree, container, false);

        // listen for changes in mAllFamilyMembers in the ViewModel
        mFamilyTreeViewModel = ViewModelProviders.of(getActivity()).get(FamilyTreeViewModel.class);
        mFamilyTreeViewModel.getmAllFamilyTrees().observe(getViewLifecycleOwner(), new Observer<List<FamilyTree>>() {

            @Override
            public void onChanged(List<FamilyTree> familyTrees) {
                List<FamilyTree> ft = mFamilyTreeViewModel.getFamilyTreesByAppUserId(appUserId);
                mAdaptor.setDataset(ft);
            }
        });

//        mFamilyTreeViewModel.getFamilyTreesByAppUserId(appUserId).observe(getViewLifecycleOwner(), new Observer<List<FamilyTree>>() {
//
//            @Override
//            public void onChanged(List<FamilyTree> familyTrees) {
//                mAdaptor.setDataset(familyTrees);
//            }
//        });

        recyclerView = view.findViewById(R.id.select_tree_recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdaptor = new TreeListAdapter(new OnFamilyTreeItemClickedListener());
        recyclerView.setAdapter(mAdaptor);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.select_tree_action_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_tree_add:
                createNewTree();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewTree() {
        ((TreeEditorActivity) getActivity()).transitionFromSelectTreeToCreateTree();
    }

    public class OnFamilyTreeItemClickedListener {
        public void onClick(int position) {

            FamilyTree familyTree = mFamilyTreeViewModel.getFamilyTreeAtIndex(position, appUserId);
            int familyTreeId = familyTree.getFamilyTreeId();

            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.family_tree_id), familyTreeId);
            getParentFragmentManager().setFragmentResult(getString(R.string.family_tree_id), bundle);

            ((TreeEditorActivity) getActivity()).transitionFromSelectTreeToHome(bundle);
        }
    }
}