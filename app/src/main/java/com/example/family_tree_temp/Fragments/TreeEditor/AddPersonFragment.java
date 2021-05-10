package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPersonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPersonItemAddedListener callback;

    public AddPersonFragment() {
        // Required empty public constructor
    }

    public void setOnPersonItemAddedListener(OnPersonItemAddedListener callback) {
        this.callback = callback;
    }

    public interface OnPersonItemAddedListener {
        public void onPersonItemAdded(int position);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPersonFragment newInstance(String param1, String param2) {
        AddPersonFragment fragment = new AddPersonFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_person, container, false);

        Bundle args = getArguments();
//        Spinner spinner = view.findViewById(R.id.relationship_spinner);
        int oldRelativePosition = -1;
//        if (args != null) {
//            boolean isAddingRelative = args.getBoolean("isAddingRelative", false);
//            oldRelativePosition = args.getInt("oldRelativePosition", -1);
//
//            if (isAddingRelative) {
//                String[] items = new String[]{"Parent", "Child"};
//                ArrayAdapter<String>  adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, items);
//                spinner.setAdapter(adapter);
//                spinner.setVisibility(View.VISIBLE);
//            } else {
//                spinner.setVisibility(View.GONE);
//            }
//        } else {
//            spinner.setVisibility(View.GONE);
//        }

//        Spinner genderSpinner = view.findViewById(R.id.add_family_member_gender_spinner);
//        String[] genders = {"Agender", "Androgyne", "Androgynous", "Bigender", "Cis", "Cisgender", "Cis Female", "Cis Male", "Cis Man", "Cis Woman", "Cisgender Female", "Cisgender Male", "Cisgender Man", "Cisgender Woman", "Male", "Female"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, genders);
//        genderSpinner.setAdapter(adapter);

        Button finishButton = view.findViewById(R.id.finish_button);
        int finalOldRelativePosition = oldRelativePosition;
        finishButton.setOnClickListener(v -> {

            String firstName = ((TextInputEditText) view.findViewById(R.id.new_person_first_name)).getText().toString();
            String lastName = ((TextInputEditText) view.findViewById(R.id.new_person_last_name)).getText().toString();
//            String age = ((TextInputEditText) view.findViewById(R.id.new_person_age)).getText().toString();
            String streetNumber = ((TextInputEditText) view.findViewById(R.id.new_person_house_number)).getText().toString();
            String streetName = ((TextInputEditText) view.findViewById(R.id.new_person_street_name)).getText().toString();
            String townCity = ((TextInputEditText) view.findViewById(R.id.new_person_town)).getText().toString();
            String state = ((TextInputEditText) view.findViewById(R.id.new_person_state)).getText().toString();
            String country = ((TextInputEditText) view.findViewById(R.id.new_person_country)).getText().toString();
            String zipcode = ((TextInputEditText) view.findViewById(R.id.new_person_zipcode)).getText().toString();

            //Address address = new Address(streetNumber, streetName, townCity, state, country, zipcode);

            String birthDate = ((TextInputEditText) view.findViewById(R.id.new_person_birthdate)).getText().toString();

            String gender = ((TextInputEditText) view.findViewById(R.id.new_person_gender)).getText().toString();
//            gender = genderSpinner.getSelectedItem().toString();

//            int genderId;
//            switch (gender) {
//                default:
//                    genderId = 1;
//                    break;
//            }
//            switch (gender.toLowerCase()) {
//                case "agender":
//                    genderId = 1;
//                    break;
//                case "androgyne":
//                    genderId = 2;
//                    break;
//                case "androgynous":
//                    genderId = 3;
//                    break;
//                case "bigender":
//                    genderId = 4;
//                    break;
//                case "cis":
//                    genderId = 5;
//                    break;
//                case "cisgender":
//                    genderId = 6;
//                    break;
//                case "cis female":
//                    genderId = 7;
//                    break;
//                case "cis male":
//                    genderId = 8;
//                    break;
//                case "cis man":
//                    genderId = 9;
//                    break;
//                case "cis woman":
//                    genderId = 10;
//                    break;
//                case "cisgender female":
//                    genderId = 11;
//                    break;
//                case "cisgender male":
//                    genderId = 12;
//                    break;
//                case "cisgender man":
//                    genderId = 13;
//                    break;
//                case "cisgender woman":
//                    genderId = 14;
//                    break;
//                case "male":
//                    genderId = 15;
//                    break;
//                case "woman":
//                    genderId = 16;
//                    break;
//                default:
//                    genderId = 1;
//                    break;
//            }

            String relationship = null;
//            if (spinner.getVisibility() == View.VISIBLE) {
//                relationship = spinner.getSelectedItem().toString();
//            }
            //Person person = new Person(firstName, lastName, age, address, finalOldRelativePosition, relationship);

            Person person = new Person(firstName, lastName, birthDate, gender, finalOldRelativePosition, relationship);

            // if you wanted to communicate to the HomeFragment that we updated the adaptor,
            // who would then add the person to the adaptor, then you should do this.
            // But, since DetailDump's data is static, we can just add it here.
            // This is also an example of how to communicate between fragments
            Bundle bundle = new Bundle();
            bundle.putParcelable("newPersonKey", person);
            getParentFragmentManager().setFragmentResult("newPersonKey", bundle);

            // tell MainActivity to switch to the home fragment
            callback.onPersonItemAdded(0);
        });

        return view;
    }
}