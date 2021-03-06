package com.example.family_tree_temp.Fragments.TreeEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import com.example.family_tree_temp.Adapters.FamilyMemberAdapter;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Adapters.Person;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.Models.AncestorDescendantBundle;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private FamilyMemberAdapter mAdaptor;
    private RecyclerView.LayoutManager layoutManager;
    private FamilyMemberViewModel mFamilyMemberViewModel;

    private int familyTreeId;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (getArguments() != null) {

            familyTreeId = getArguments().getInt(getString(R.string.family_tree_id));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(getString(R.string.family_tree_id), familyTreeId);
            editor.commit();
        }

        familyTreeId = preferences.getInt(getString(R.string.family_tree_id), -1);

        getParentFragmentManager().setFragmentResultListener("newPersonKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Person person = (Person) result.get("newPersonKey");
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                FamilyMember familyMember = new FamilyMember(person.getFirstName(), person.getLastName(), person.getBirthDate(), person.getGender(), familyTreeId);
                mFamilyMemberViewModel.insert(familyMember);
            }
        });

        getParentFragmentManager().setFragmentResultListener("newDescendantKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                AncestorDescendantBundle ancestorDescendantBundle = (AncestorDescendantBundle) result.get("newDescendantKey");
                mFamilyMemberViewModel.insertDescendant(ancestorDescendantBundle);
            }
        });

        getParentFragmentManager().setFragmentResultListener("newAncestorKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                AncestorDescendantBundle ancestorDescendantBundle = (AncestorDescendantBundle) result.get("newAncestorKey");
                mFamilyMemberViewModel.insertAncestor(ancestorDescendantBundle);
            }
        });

        setHasOptionsMenu(true);

        ((BottomAppBar) getActivity().findViewById(R.id.bottomAppBar)).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.bottom_bar_profile:
                        // handle search icon press
                        return true;
                    case R.id.bottom_bar_overflow:
                        Bundle bundle = new Bundle();
                        bundle.putInt(getString(R.string.family_tree_id), familyTreeId);
                        ((TreeEditorActivity) getActivity()).transitionFromHomeToTreeView(bundle);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_recycler_view);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdaptor = new FamilyMemberAdapter((TreeEditorActivity) getActivity(), new OnFamilyMemberItemClickedListener());

        mFamilyMemberViewModel = ViewModelProviders.of(getActivity()).get(FamilyMemberViewModel.class);

        // listen for changes in mAllFamilyMembers in the ViewModel
        mFamilyMemberViewModel.getAllFamilyMembers().observe(getViewLifecycleOwner(), new Observer<List<FamilyMember>>() {
            @Override
            public void onChanged(@Nullable final List<FamilyMember> familyMembers) {
                List<FamilyMember> dataSet = new ArrayList<>();
                for (FamilyMember familyMember : familyMembers) {
                    if (familyMember.getFamilyTreeId() == familyTreeId) {
                        dataSet.add(familyMember);
                    }
                }
                mAdaptor.setDataset(dataSet);
            }
        });

        recyclerView.setAdapter(mAdaptor);

        /*
        TODO: this is a kinda scuffed solution to prevent something being inserted into the
            local db more than once. It would probably be better to instead have it sync every time
            it comes back to home fragment.
            Source of problem was trying to make a new family member. New members were inserted
            twice for some reason; I think it was because mAllFamilyMembers in the repo was
            null, so sync was performing before the value wasn't null
         */
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean shouldSyncWithServer = preferences.getBoolean(getString(R.string.should_sync), false);
        if (shouldSyncWithServer) {
            mFamilyMemberViewModel.sync(familyTreeId);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.should_sync), false);
            editor.commit();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.app_top_bar, menu);

        MenuItem searchBar = menu.findItem(R.id.top_bar_search);
        SearchView searchBarView = (SearchView) searchBar.getActionView();
        searchBarView.setQueryHint("Enter a name...");
        searchBarView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_bar_share:
                shareTree();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareTree() {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.family_tree_id), familyTreeId);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int appUserId = preferences.getInt(getString(R.string.app_user_id), -1);
        bundle.putInt(getString(R.string.app_user_id), appUserId);
        ((TreeEditorActivity) getActivity()).transitionFromHomeToShareTree(bundle);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdaptor.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdaptor.getFilter().filter(newText);
        return false;
    }

    public class OnFamilyMemberItemClickedListener {
        public void onClick(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("familyMemberPosition", position);
            getParentFragmentManager().setFragmentResult("familyMemberPosition", bundle);

            ((TreeEditorActivity) getActivity()).transitionFromHomeToFamilyMemberDetail(bundle);
        }
    }
}