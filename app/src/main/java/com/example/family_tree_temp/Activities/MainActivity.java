package com.example.family_tree_temp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.family_tree_temp.Fragments.AddDescendantFragment;
import com.example.family_tree_temp.Fragments.AddPersonFragment;
import com.example.family_tree_temp.Fragments.FamilyMemberDetailFragment;
import com.example.family_tree_temp.Fragments.HomeFragment;
import com.example.family_tree_temp.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class MainActivity extends AppCompatActivity implements AddPersonFragment.OnPersonItemAddedListener {

    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;

    private final String HOME_FRAG_TAG = "homeFragTag";
    private final String ADD_PERSON_FRAG_TAG = "addPersonFragTag";

    private String currentFragTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // make the home fragment our initial fragment
        getSupportFragmentManager().beginTransaction().add(R.id.host_fragment, new HomeFragment(), HOME_FRAG_TAG).commit();
        currentFragTag = HOME_FRAG_TAG;

        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationDrawerFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Context context = getApplicationContext();
                switch(item.getItemId()) {
                    case R.id.bottom_bar_profile:
                        // handle search icon press
                        Toast.makeText(context, "profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottom_bar_overflow:
                        // handle overflow icon press
                        Toast.makeText(context, "overflow", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                switch (currentFragTag) {
                    case HOME_FRAG_TAG:
                        fragmentTransaction.replace(R.id.host_fragment, new AddPersonFragment());
                        //bottomAppBar.setNavigationIcon(null);
                        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                        bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu_secondary);
                        floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
                        currentFragTag = ADD_PERSON_FRAG_TAG;
                        fragmentTransaction.addToBackStack(null);
                        break;
                    case ADD_PERSON_FRAG_TAG:
                        fragmentTransaction.replace(R.id.host_fragment, new HomeFragment());
                        bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                        bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu);
                        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                        currentFragTag = HOME_FRAG_TAG;
                        fragmentManager.popBackStack();
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    public void toggleHomeFragmentToAddPersonFragment(boolean isAddingRelative, int position) {
        if (currentFragTag.equals(HOME_FRAG_TAG)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            AddPersonFragment addPersonFragment = new AddPersonFragment();
            Bundle args = new Bundle();
            args.putBoolean("isAddingRelative", isAddingRelative);
            args.putInt("oldRelativePosition", position);
            addPersonFragment.setArguments(args);

            fragmentTransaction.replace(R.id.host_fragment, addPersonFragment);
            //bottomAppBar.setNavigationIcon(null);
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu_secondary);
            floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
            currentFragTag = ADD_PERSON_FRAG_TAG;
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPersonItemAdded(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.host_fragment, new HomeFragment());
        bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
        currentFragTag = HOME_FRAG_TAG;
        fragmentTransaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AddPersonFragment) {
            AddPersonFragment addPersonFragment = (AddPersonFragment) fragment;
            addPersonFragment.setOnPersonItemAddedListener(this);
        }
        if (fragment instanceof FamilyMemberDetailFragment) {

        }
        if (fragment instanceof AddDescendantFragment) {
            AddDescendantFragment addDescendantFragment = (AddDescendantFragment) fragment;
            addDescendantFragment.setOnPersonItemAddedListener(this);
        }
    }

    public static class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
            NavigationView navigationView = view.findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_settings:
                            //Toast.makeText(context, "settings", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.nav_about:
                            //Toast.makeText(context, "about", Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }
                }
            });
            return view;
        }
    }

    private void manageFragmentTransaction(String selectedFrag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch(selectedFrag) {
            case HOME_FRAG_TAG:
                if (fragmentManager.findFragmentByTag(HOME_FRAG_TAG) != null) {
                    // if the fragment exists, show it
                    fragmentTransaction.show(fragmentManager.findFragmentByTag(HOME_FRAG_TAG));
                } else {
                    // if the fragment does not exist, add it to the fragment manager
                    fragmentTransaction.add(R.id.host_fragment, new HomeFragment(), HOME_FRAG_TAG);
                }
                if (fragmentManager.findFragmentByTag(ADD_PERSON_FRAG_TAG) != null) {
                    // if the other fragment is visible, hide it
                    fragmentTransaction.hide(fragmentManager.findFragmentByTag(ADD_PERSON_FRAG_TAG));
                }
                fragmentTransaction.commit();
                bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                currentFragTag = HOME_FRAG_TAG;
                fragmentManager.popBackStack();
                break;
            case ADD_PERSON_FRAG_TAG:
                if (fragmentManager.findFragmentByTag(ADD_PERSON_FRAG_TAG) != null) {
                    fragmentTransaction.show(fragmentManager.findFragmentByTag(ADD_PERSON_FRAG_TAG));
                } else {
                    fragmentTransaction.add(R.id.host_fragment, new AddPersonFragment(), ADD_PERSON_FRAG_TAG);
                }
                if (fragmentManager.findFragmentByTag(HOME_FRAG_TAG) != null) {
                    fragmentTransaction.hide(fragmentManager.findFragmentByTag(HOME_FRAG_TAG));
                }
                fragmentTransaction.commit();
                bottomAppBar.setNavigationIcon(null);
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
                currentFragTag = ADD_PERSON_FRAG_TAG;
                fragmentTransaction.addToBackStack(null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (currentFragTag) {
            case ADD_PERSON_FRAG_TAG:
                fragmentTransaction.replace(R.id.host_fragment, new HomeFragment());
                bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu);
                floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                currentFragTag = HOME_FRAG_TAG;
                break;
        }
        fragmentTransaction.commit();
        super.onBackPressed();
    }
}