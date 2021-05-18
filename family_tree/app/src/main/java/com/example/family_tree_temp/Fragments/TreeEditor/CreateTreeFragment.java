package com.example.family_tree_temp.Fragments.TreeEditor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.family_tree_temp.Activities.TreeEditorActivity;
import com.example.family_tree_temp.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTreeFragment extends Fragment {

    private TextInputEditText nameInput;
    private Button createButton;

    public CreateTreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateTreeFragment.
     */
    public static CreateTreeFragment newInstance() {
        CreateTreeFragment fragment = new CreateTreeFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_tree, container, false);

        nameInput = view.findViewById(R.id.create_tree_name);
        createButton = view.findViewById(R.id.create_tree_create_button);

        createButton.setOnClickListener(v -> createTree());

        return view;
    }

    private void createTree() {

        String name = nameInput.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("newTreeName", name);
        getParentFragmentManager().setFragmentResult("newTreeKey", bundle);

        // tell parent activity to switch to the home fragment
        ((TreeEditorActivity) getActivity()).transitionFromCreateTreeToSelectTree(bundle);
    }
}