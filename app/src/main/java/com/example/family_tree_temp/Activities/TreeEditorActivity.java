package com.example.family_tree_temp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.family_tree_temp.Fragments.TreeEditor.AddAncestorFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.AddDescendantFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.AddPersonFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.CreateTreeFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.FamilyMemberDetailFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.HomeFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.MedicalHistoryDetailFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.NewAddressFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.NewEmailFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.NewMedicalHistoryFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.NewPhoneNumberFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.SelectTreeFragment;
import com.example.family_tree_temp.Fragments.TreeEditor.TreeFragment;
import com.example.family_tree_temp.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class TreeEditorActivity extends AppCompatActivity implements AddPersonFragment.OnPersonItemAddedListener {

    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;

    public final String HOME_FRAGMENT = "home_fragment";
    public final String NEW_FAMILY_MEMBER_FRAGMENT = "new_family_member_fragment";
    public final String FAMILY_MEMBER_DETAIL_FRAGMENT = "family_member_detail_fragment";
    public final String ADD_ANCESTOR_FRAGMENT = "add_ancestor_fragment";
    public final String ADD_DESCENDANT_FRAGMENT = "add_descendant_fragment";
    public final String ADD_EMAIL_FRAGMENT = "add_email_fragment";
    public final String ADD_PHONE_NUMBER_FRAGMENT = "add_phone_number_fragment";
    public final String ADD_ADDRESS_FRAGMENT = "add_address_fragment";
    public final String ADD_MEDICAL_HISTORY_FRAGMENT = "add_medical_history_fragment";
    public final String MEDICAL_HISTORY_DETAIL_FRAGMENT = "medical_history_detail_fragment";
    public final String TREE_VIEW = "tree_view";
    public final String SELECT_TREE_FRAGMENT = "select_tree_fragment";
    public final String CREATE_TREE_FRAGMENT = "create_tree_fragment";

    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_editor);

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
                        //Toast.makeText(context, "overflow", Toast.LENGTH_SHORT).show();
                        transitionFromHomeToTreeView();
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
                    case MEDICAL_HISTORY_DETAIL_FRAGMENT:
                    case TREE_VIEW:
                    case SELECT_TREE_FRAGMENT:
                    case CREATE_TREE_FRAGMENT:
                        fabBackPressed();
                        break;
                }
            }
        });

        setupInitialFragment();
    }

    private void setupInitialFragment() {
        // commit family tree id
        Intent intent = getIntent();
        String appUser = intent.getStringExtra(getString(R.string.app_user));
        try {
            int appUserId = new JSONObject(appUser).getInt("AppUserId");
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.app_user_id), appUserId);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // make the select tree fragment our initial fragment
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.app_user), appUser);
        SelectTreeFragment selectTreeFragment = new SelectTreeFragment();
        selectTreeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.tree_editor_host_fragment, selectTreeFragment, SELECT_TREE_FRAGMENT).commit();
        currentFragmentTag = SELECT_TREE_FRAGMENT;

        bottomAppBar.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    public void transitionFromHomeToAddFamilyMember() {
        if (currentFragmentTag.equals(HOME_FRAGMENT)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            AddPersonFragment addPersonFragment = new AddPersonFragment();

            fragmentTransaction.replace(R.id.tree_editor_host_fragment, addPersonFragment, NEW_FAMILY_MEMBER_FRAGMENT);
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

        fragmentTransaction.replace(R.id.tree_editor_host_fragment, homeFragment);
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

        fragmentTransaction.replace(R.id.tree_editor_host_fragment, homeFragment);
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
                .replace(R.id.tree_editor_host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromFamilyMemberDetailToAddAncestor(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_ANCESTOR_FRAGMENT;

        Fragment addAncestorFragment = new AddAncestorFragment();
        addAncestorFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, addAncestorFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromFamilyMemberDetailToAddDescendant(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_DESCENDANT_FRAGMENT;

        Fragment addDescendantFragment = new AddDescendantFragment();
        addDescendantFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, addDescendantFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromDetailToAddEmail(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_EMAIL_FRAGMENT;

        Fragment addEmailFragment = new NewEmailFragment();
        addEmailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, addEmailFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromDetailToAddPhoneNumber(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_PHONE_NUMBER_FRAGMENT;

        Fragment addPhoneNumberFragment = new NewPhoneNumberFragment();
        addPhoneNumberFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, addPhoneNumberFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromDetailToAddAddress(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_ADDRESS_FRAGMENT;

        Fragment addAddressFragment = new NewAddressFragment();
        addAddressFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, addAddressFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromDetailToAddMedicalHistory(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = ADD_MEDICAL_HISTORY_FRAGMENT;

        Fragment addMedicalHistoryFragment = new NewMedicalHistoryFragment();
        addMedicalHistoryFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, addMedicalHistoryFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromFamilyMemberToMedicalHistoryDetail(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = MEDICAL_HISTORY_DETAIL_FRAGMENT;

        //bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        //bottomAppBar.performHide();
        //floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
        currentFragmentTag = newTag;

        MedicalHistoryDetailFragment nextFragment = new MedicalHistoryDetailFragment();
        nextFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromHomeToTreeView() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = TREE_VIEW;

        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        bottomAppBar.performHide();
        floatingActionButton.setImageResource(R.drawable.ic_baseline_reply_24);
        currentFragmentTag = newTag;

        TreeFragment nextFragment = new TreeFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromSelectTreeToCreateTree() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = CREATE_TREE_FRAGMENT;

        CreateTreeFragment nextFragment = new CreateTreeFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromCreateTreeToSelectTree(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = SELECT_TREE_FRAGMENT;

        SelectTreeFragment nextFragment = new SelectTreeFragment();
        nextFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void transitionFromSelectTreeToHome(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String newTag = HOME_FRAGMENT;

        HomeFragment nextFragment = new HomeFragment();
        nextFragment.setArguments(bundle);

        bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        bottomAppBar.performShow();
        bottomAppBar.setVisibility(View.VISIBLE);
        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
        floatingActionButton.setVisibility(View.VISIBLE);

        fragmentManager.beginTransaction()
                .replace(R.id.tree_editor_host_fragment, nextFragment, newTag)
                .addToBackStack(newTag)
                .commit();
    }

    public void fabBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        int size = fragmentManager.getBackStackEntryCount();

        // 2 because select tree > home view > x > ....
        if (size > 2) {

            // peek second to last
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(size - 2);
            String name = backStackEntry.getName();
            switch (name) {
                case HOME_FRAGMENT:
                    transitionToHomeFromSomeView();
                default:
                    Fragment fragment = fragmentManager.findFragmentByTag(name);
                    fragmentTransaction.replace(R.id.tree_editor_host_fragment, fragment);
                    currentFragmentTag = fragment.getTag();
                    fragmentManager.popBackStack();
                    fragmentTransaction.commit();
                    break;
            }
        } else if (size == 2) {
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
                    fragmentTransaction.add(R.id.tree_editor_host_fragment, new HomeFragment(), HOME_FRAGMENT);
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
                    fragmentTransaction.add(R.id.tree_editor_host_fragment, new AddPersonFragment(), NEW_FAMILY_MEMBER_FRAGMENT);
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

        if (size == 2) {
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