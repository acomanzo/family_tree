package com.example.family_tree_temp.Fragments.Welcome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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