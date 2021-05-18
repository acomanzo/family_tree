package com.example.family_tree_temp.Fragments.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_tree_temp.Activities.WelcomeActivity;
import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        emailInput = view.findViewById(R.id.sign_up_email);
        passwordInput = view.findViewById(R.id.sign_up_password);

        view.findViewById(R.id.sign_up_finish_button).setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            signUp(email, password);
        });

        return view;
    }

    private void signUp(String email, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            FamilyTreeSqlDatabase familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
            String response = familyTreeSqlDatabase.insertAppUser(email, password);
            try {
                if (response != null) {
                    // switch to login
                    JSONObject object = new JSONObject(response);
                    int appUserId = object.getInt("AppUserId");
                    ((WelcomeActivity) getActivity()).transitionToLoginScreen();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.post(() -> {

            });
        });
    }
}