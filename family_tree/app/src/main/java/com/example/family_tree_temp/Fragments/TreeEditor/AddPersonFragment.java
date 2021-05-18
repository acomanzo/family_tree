package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Adapters.Person;
import com.example.family_tree_temp.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPersonFragment extends Fragment {

    private OnPersonItemAddedListener callback;

    public AddPersonFragment() {
        // Required empty public constructor
    }

    public void setOnPersonItemAddedListener(OnPersonItemAddedListener callback) {
        this.callback = callback;
    }

    public interface OnPersonItemAddedListener {
        void onPersonItemAdded(int position);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddPersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPersonFragment newInstance() {
        AddPersonFragment fragment = new AddPersonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_person, container, false);

        int oldRelativePosition = -1;

        Button finishButton = view.findViewById(R.id.finish_button);
        int finalOldRelativePosition = oldRelativePosition;
        finishButton.setOnClickListener(v -> {

            String firstName = ((TextInputEditText) view.findViewById(R.id.new_person_first_name)).getText().toString();
            String lastName = ((TextInputEditText) view.findViewById(R.id.new_person_last_name)).getText().toString();
            String birthDate = ((TextInputEditText) view.findViewById(R.id.new_person_birthdate)).getText().toString();
            String gender = ((TextInputEditText) view.findViewById(R.id.new_person_gender)).getText().toString();

            String relationship = null;
            Person person = new Person(firstName, lastName, birthDate, gender, finalOldRelativePosition, relationship);

            Bundle bundle = new Bundle();
            bundle.putParcelable("newPersonKey", person);
            getParentFragmentManager().setFragmentResult("newPersonKey", bundle);

            // tell parent activity to switch to the home fragment
            callback.onPersonItemAdded(0);
        });

        return view;
    }
}