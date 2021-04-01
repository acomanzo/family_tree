package com.example.family_tree_temp.Fragments;

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

import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;
import com.example.family_tree_temp.ViewModels.AncestorDescendantBundle;
import com.example.family_tree_temp.ViewModels.FamilyMemberViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDescendantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDescendantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int ancestorId;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDescendantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDescendantFragment newInstance(String param1, String param2) {
        AddDescendantFragment fragment = new AddDescendantFragment();
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
            ancestorId = getArguments().getInt("familyMemberId");
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
            String age = ((TextInputEditText) view.findViewById(R.id.add_descendant_age)).getText().toString();
            String streetNumber = ((TextInputEditText) view.findViewById(R.id.add_descendant_street_number)).getText().toString();
            String streetName = ((TextInputEditText) view.findViewById(R.id.add_descendant_street_name)).getText().toString();
            String townCity = ((TextInputEditText) view.findViewById(R.id.add_descendant_town)).getText().toString();
            String state = ((TextInputEditText) view.findViewById(R.id.add_descendant_state)).getText().toString();
            String country = ((TextInputEditText) view.findViewById(R.id.add_descendant_country)).getText().toString();
            String zipcode = ((TextInputEditText) view.findViewById(R.id.add_descendant_zipcode)).getText().toString();

            //Address address = new Address(streetNumber, streetName, townCity, state, country, zipcode);

            String gender = ((TextInputEditText) view.findViewById(R.id.add_descendant_gender)).getText().toString();
            int genderId;
            switch (gender) {
                default:
                    genderId = 1;
                    break;
            }
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

            FamilyMember descendant = new FamilyMember(firstName, lastName, 10, genderId);

            AncestorDescendantBundle ancestorDescendantBundle = new AncestorDescendantBundle(descendant, ancestorId, depth);

            // if you wanted to communicate to the HomeFragment that we updated the adaptor,
            // who would then add the person to the adaptor, then you should do this.
            // But, since DetailDump's data is static, we can just add it here.
            // This is also an example of how to communicate between fragments
            Bundle bundle = new Bundle();
            bundle.putParcelable("newDescendantKey", ancestorDescendantBundle);
            getParentFragmentManager().setFragmentResult("newDescendantKey", bundle);

            // tell MainActivity to switch to the home fragment
            getParentFragmentManager().popBackStack(); // since we're two away from home
            callback.onPersonItemAdded(0);
        });

        return view;
    }
}