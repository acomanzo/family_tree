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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.family_tree_temp.Fragments.AddAncestorFragment;
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
    
    public final String HOME_FRAGMENT = "home_fragment";
    public final String NEW_FAMILY_MEMBER_FRAGMENT = "new_family_member_fragment";
    public final String FAMILY_MEMBER_DETAIL_FRAGMENT = "family_member_detail_fragment";
    public final String ADD_ANCESTOR_FRAGMENT = "add_ancestor_fragment";
    public final String ADD_DESCENDANT_FRAGMENT = "add_descendant_fragment";
    
    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // make the home fragment our initial fragment
        getSupportFragmentManager().beginTransaction().add(R.id.host_fragment, new HomeFragment(), HOME_FRAGMENT).commit();
        currentFragmentTag = HOME_FRAGMENT;

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
                switch (currentFragmentTag) {
                    case HOME_FRAGMENT:
                        transitionFromHomeToAddFamilyMember();
                        break;
                    case NEW_FAMILY_MEMBER_FRAGMENT:
                    case FAMILY_MEMBER_DETAIL_FRAGMENT:
                    case ADD_ANCESTOR_FRAGMENT:
                    case ADD_DESCENDANT_FRAGMENT:
                        fabBackPressed();
                        break;
                }
            }
        });
    }

    public void transitionFromHomeToAddFamilyMember() {
        if (currentFragmentTag.equals(HOME_FRAGMENT)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            AddPersonFragment addPersonFragment = new AddPersonFragment();

            fragmentTransaction.replace(R.id.host_fragment, addPersonFragment, NEW_FAMILY_MEMBER_FRAGMENT);
            //bottomAppBar.setNavigationIcon(null);
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            //bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu_secondary);
            bottomAppBar.performHide();
            bottomAppBar.setVisibility(View.GONE);
            floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
            currentFragmentTag = NEW_FAMILY_MEMBER_FRAGMENT;
            fragmentTransaction.addToBackStack(NEW_FAMILY_MEMBER_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    public void transitionToHomeFromSomeView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        HomeFragment homeFragment = new HomeFragment();

        fragmentTransaction.replace(R.id.host_fragment, homeFragment);
        bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        //bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu);
        bottomAppBar.performShow();
        bottomAppBar.setVisibility(View.VISIBLE);
        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
        currentFragmentTag = HOME_FRAGMENT;
        fragmentManager.popBackStack();
        fragmentTransaction.commit();
    }

    public void transitionToHomeFromSomeViewAndDontPop() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        HomeFragment homeFragment = new HomeFragment();

        fragmentTransaction.replace(R.id.host_fragment, homeFragment);
        bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        //bottomAppBar.replaceMenu(R.menu.app_bottom_bar_menu);
        bottomAppBar.performShow();
        bottomAppBar.setVisibility(View.VISIBLE);
        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
        currentFragmentTag = HOME_FRAGMENT;
        fragmentTransaction.commit();
    }

    public void transitionFromHomeToFamilyMemberDetail(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = FAMILY_MEMBER_DETAIL_FRAGMENT;

        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        bottomAppBar.performHide();
        floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
        currentFragmentTag = newTag;

        FamilyMemberDetailFragment nextFragment = new FamilyMemberDetailFragment();
        nextFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromFamilyMemberDetailToAddAncestor(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_ANCESTOR_FRAGMENT;

        Fragment addAncestorFragment = new AddAncestorFragment();
        addAncestorFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.host_fragment, addAncestorFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromFamilyMemberDetailToAddDescendant(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_DESCENDANT_FRAGMENT;

        Fragment addDescendantFragment = new AddDescendantFragment();
        addDescendantFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.host_fragment, addDescendantFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }



    public void fabBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        int size = fragmentManager.getBackStackEntryCount();
        if (size > 1) {

            // peek second to last
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(size - 2);
            String name = backStackEntry.getName();
            switch (name) {
                case HOME_FRAGMENT:
                    transitionToHomeFromSomeView();
                default:
                    Fragment fragment = fragmentManager.findFragmentByTag(name);
                    fragmentTransaction.replace(R.id.host_fragment, fragment);
                    currentFragmentTag = fragment.getTag();
                    fragmentManager.popBackStack();
                    fragmentTransaction.commit();
                    break;
            }
        } else if (size == 1) {
            transitionToHomeFromSomeView();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AddPersonFragment) {
            AddPersonFragment addPersonFragment = (AddPersonFragment) fragment;
            addPersonFragment.setOnPersonItemAddedListener(this);
        }
        if (fragment instanceof FamilyMemberDetailFragment) {
//            FamilyMemberDetailFragment familyMemberDetailFragment = (FamilyMemberDetailFragment) fragment;
//            familyMemberDetailFragment.setOnDeleteListener(this);
//            familyMemberDetailFragment.setOnUpdateListener(this);
        }
        if (fragment instanceof AddDescendantFragment) {
            AddDescendantFragment addDescendantFragment = (AddDescendantFragment) fragment;
            addDescendantFragment.setOnPersonItemAddedListener(this);
        }
        if (fragment instanceof AddAncestorFragment) {
            AddAncestorFragment addAncestorFragment = (AddAncestorFragment) fragment;
            addAncestorFragment.setOnPersonItemAddedListener(this);
        }
    }

    @Override
    public void onPersonItemAdded(int position) {
        transitionToHomeFromSomeView();
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
            case HOME_FRAGMENT:
                if (fragmentManager.findFragmentByTag(HOME_FRAGMENT) != null) {
                    // if the fragment exists, show it
                    fragmentTransaction.show(fragmentManager.findFragmentByTag(HOME_FRAGMENT));
                } else {
                    // if the fragment does not exist, add it to the fragment manager
                    fragmentTransaction.add(R.id.host_fragment, new HomeFragment(), HOME_FRAGMENT);
                }
                if (fragmentManager.findFragmentByTag(NEW_FAMILY_MEMBER_FRAGMENT) != null) {
                    // if the other fragment is visible, hide it
                    fragmentTransaction.hide(fragmentManager.findFragmentByTag(NEW_FAMILY_MEMBER_FRAGMENT));
                }
                fragmentTransaction.commit();
                bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                currentFragmentTag = HOME_FRAGMENT;
                fragmentManager.popBackStack();
                break;
            case NEW_FAMILY_MEMBER_FRAGMENT:
                if (fragmentManager.findFragmentByTag(NEW_FAMILY_MEMBER_FRAGMENT) != null) {
                    fragmentTransaction.show(fragmentManager.findFragmentByTag(NEW_FAMILY_MEMBER_FRAGMENT));
                } else {
                    fragmentTransaction.add(R.id.host_fragment, new AddPersonFragment(), NEW_FAMILY_MEMBER_FRAGMENT);
                }
                if (fragmentManager.findFragmentByTag(HOME_FRAGMENT) != null) {
                    fragmentTransaction.hide(fragmentManager.findFragmentByTag(HOME_FRAGMENT));
                }
                fragmentTransaction.commit();
                bottomAppBar.setNavigationIcon(null);
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
                currentFragmentTag = NEW_FAMILY_MEMBER_FRAGMENT;
                fragmentTransaction.addToBackStack(null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        int size = fragmentManager.getBackStackEntryCount();

        if (size == 1) {
            bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            bottomAppBar.performShow();
            bottomAppBar.setVisibility(View.VISIBLE);
            floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
            currentFragmentTag = HOME_FRAGMENT;
        }

        super.onBackPressed();
    }
}