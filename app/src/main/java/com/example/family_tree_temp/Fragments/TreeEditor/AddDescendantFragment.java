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
 * Use the {@link AddDescendantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDescendantFragment extends Fragment {

    private FamilyMemberViewModel mFamilyMemberViewModel;
    private FamilyMember ancestor;

    private AddPersonFragment.OnPersonItemAddedListener callback;

    public AddDescendantFragment() {
        // Required empty public constructor
    }

    public void setOnPersonItemAddedListener(AddPersonFragment.OnPersonItemAddedListener callback) {
        this.callback = callback;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddDescendantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDescendantFragment newInstance() {
        AddDescendantFragment fragment = new AddDescendantFragment();
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
            ancestor = mFamilyMemberViewModel.getFamilyMemberByServerId(familyMemberServerId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_descendant, container, false);

        TextView prompt = view.findViewById(R.id.add_descendant_relationship_prompt);
        prompt.setText("What kind of descendant?");
        Spinner spinner = view.findViewById(R.id.add_descendant_relationship_spinner);
        String[] items = new String[]{"Child", "Grandchild", "Niece", "Nephew"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        Button finishButton = view.findViewById(R.id.add_descendant_finish_button);
        finishButton.setOnClickListener(v -> {

            String firstName = ((TextInputEditText) view.findViewById(R.id.add_descendant_first_name)).getText().toString();
            String lastName = ((TextInputEditText) view.findViewById(R.id.add_descendant_last_name)).getText().toString();
            String gender = ((TextInputEditText) view.findViewById(R.id.add_descendant_gender)).getText().toString();
            String birthDate = ((TextInputEditText) view.findViewById(R.id.add_descendant_birth_date)).getText().toString();

            String relationship = null;
            relationship = spinner.getSelectedItem().toString();
            int depth;
            switch (relationship.toLowerCase()) {
                case "child":
                    depth = 1;
                case "niece":
                    depth = 1;
                case "nephew":
                    depth = 1;
                    break;
                case "grandchild":
                    depth = 2;
                    break;
                default:
                    depth = 1;
                    break;
            }

            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            int familyTreeId = sharedPreferences.getInt(getString(R.string.family_tree_id), -1);

            FamilyMember descendant = new FamilyMember(firstName, lastName, birthDate, gender, familyTreeId);

            AncestorDescendantBundle ancestorDescendantBundle = new AncestorDescendantBundle(descendant, ancestor, depth);

            Bundle bundle = new Bundle();
            bundle.putParcelable("newDescendantKey", ancestorDescendantBundle);
            getParentFragmentManager().setFragmentResult("newDescendantKey", bundle);

            // tell parent activity to switch to the home fragment
            getParentFragmentManager().popBackStack(); // since we're two away from home
            callback.onPersonItemAdded(0);
        });

        return view;
    }
}