package com.example.family_tree_temp.Fragments.TreeEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.Models.AncestorDescendantBundle;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAncestorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAncestorFragment extends Fragment {

    private FamilyMemberViewModel mFamilyMemberViewModel;
    private FamilyMember descendant;

    private AddPersonFragment.OnPersonItemAddedListener callback;

    public AddAncestorFragment() {
        // Required empty public constructor
    }

    public void setOnPersonItemAddedListener(AddPersonFragment.OnPersonItemAddedListener callback) {
        this.callback = callback;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddAncestorFragment.
     */
    public static AddAncestorFragment newInstance() {
        AddAncestorFragment fragment = new AddAncestorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFamilyMemberViewModel = ViewModelProviders.of(getActivity()).get(FamilyMemberViewModel.class);

        if (getArguments() != null) {
            int familyMemberServerId = getArguments().getInt("familyMemberServerId", -1);
            descendant = mFamilyMemberViewModel.getFamilyMemberByServerId(familyMemberServerId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_ancestor, container, false);

        TextView prompt = view.findViewById(R.id.add_ancestor_relationship_prompt);
        prompt.setText("What kind of ancestor?");
        Spinner spinner = view.findViewById(R.id.add_ancestor_relationship_spinner);
        String[] items = new String[]{"Parent", "Grandparent", "Aunt", "Uncle"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        Button finishButton = view.findViewById(R.id.add_ancestor_finish_button);
        finishButton.setOnClickListener(v -> {

            String firstName = ((TextInputEditText) view.findViewById(R.id.add_ancestor_first_name)).getText().toString();
            String lastName = ((TextInputEditText) view.findViewById(R.id.add_ancestor_last_name)).getText().toString();
            String birthDate = ((TextInputEditText) view.findViewById(R.id.add_ancestor_birth_date)).getText().toString();
            String gender = ((TextInputEditText) view.findViewById(R.id.add_ancestor_gender)).getText().toString();

            String relationship = null;
            relationship = spinner.getSelectedItem().toString();
            int depth;
            switch (relationship.toLowerCase()) {
                case "grandparent":
                    depth = 2;
                    break;
                default:
                    depth = 1;
                    break;
            }

            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            int familyTreeId = sharedPreferences.getInt(getString(R.string.family_tree_id), -1);
            FamilyMember ancestor = new FamilyMember(firstName, lastName, birthDate, gender, familyTreeId);

            AncestorDescendantBundle ancestorDescendantBundle = new AncestorDescendantBundle(ancestor, descendant, depth);

            Bundle bundle = new Bundle();
            bundle.putParcelable("newAncestorKey", ancestorDescendantBundle);
            getParentFragmentManager().setFragmentResult("newAncestorKey", bundle);

            // tell parent activity to switch to the home fragment
            getParentFragmentManager().popBackStack(); // since we're two away from home
            callback.onPersonItemAdded(0);
        });

        return view;
    }
}