package com.example.family_tree_temp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.family_tree_temp.Fragments.TreeEditor.HomeFragment;
import com.example.family_tree_temp.Fragments.Welcome.LoginFragment;
import com.example.family_tree_temp.Fragments.Welcome.SignUpFragment;
import com.example.family_tree_temp.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class WelcomeActivity extends AppCompatActivity {

    public final String LOGIN_FRAGMENT = "login_fragment";
    public final String SIGN_UP_FRAGMENT = "sign_up_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // initial fragment
        getSupportFragmentManager().beginTransaction().add(R.id.welcome_host_fragment, new LoginFragment(), LOGIN_FRAGMENT).commit();
    }

    public void transitionToSignUpScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        SignUpFragment signUpFragment = new SignUpFragment();

        fragmentTransaction.replace(R.id.welcome_host_fragment, signUpFragment);
        fragmentTransaction.addToBackStack(SIGN_UP_FRAGMENT);
        fragmentTransaction.commit();
    }

    public void transitionToLoginScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        LoginFragment loginFragment = new LoginFragment();

        fragmentTransaction.replace(R.id.welcome_host_fragment, loginFragment);
        fragmentTransaction.addToBackStack(SIGN_UP_FRAGMENT);
        fragmentTransaction.commit();
    }

    public void login(Bundle bundle) {
        Intent intent = new Intent(WelcomeActivity.this, TreeEditorActivity.class);

        // id from server of user that logged in
        String appUser = bundle.getString("appUser");

        intent.putExtra(getString(R.string.app_user), appUser);
        startActivity(intent);
    }
}