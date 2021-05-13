package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Models.AncestorDescendant;
import com.example.family_tree_temp.Models.AncestorDescendantBundle;
import com.example.family_tree_temp.Models.FamilyTree;
import com.example.family_tree_temp.Repository.FamilyMemberRepository;
import com.example.family_tree_temp.Models.FamilyMember;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FamilyMemberViewModel extends AndroidViewModel {

    private FamilyMemberRepository mRepository;

    private LiveData<List<FamilyMember>> mAllFamilyMembers;
    private LiveData<List<AncestorDescendant>> mAllAncestorDescendants;

    public FamilyMemberViewModel (Application application) {
        super(application);
        mRepository = new FamilyMemberRepository(application);
        mAllFamilyMembers = mRepository.getAllFamilyMembers();
        mAllAncestorDescendants = mRepository.getAllAncestorDescendants();
        Log.i("FamilyMemberViewModel", "Created FamilyMemberViewModel");
    }

    public LiveData<List<FamilyMember>> getAllFamilyMembers() {
        return mAllFamilyMembers;
    }

    public LiveData<List<AncestorDescendant>> getAllAncestorDescendants() {
        return mAllAncestorDescendants;
    }

    public void insert(FamilyMember familyMember) {
        mRepository.insertFamilyMember(familyMember);
    }

    public void update(FamilyMember familyMember) {
        mRepository.updateFamilyMember(familyMember);
    }

    public void delete(FamilyMember familyMember) {
        mRepository.deleteFamilyMember(familyMember);
    }

    public FamilyMember getFamilyMemberAtIndex(int position) {
        return mAllFamilyMembers.getValue().get(position);
    }

    public void insertDescendant(AncestorDescendantBundle ancestorDescendantBundle) {
        mRepository.insertDescendant(ancestorDescendantBundle);
    }

    public void insertAncestor(AncestorDescendantBundle ancestorDescendantBundle) {
        mRepository.insertAncestor(ancestorDescendantBundle);
    }

    public void getContactInformationId(FamilyMember familyMember) {

    }

    public FamilyMember getFamilyMemberById(int id) {
//        return mRepository.getFamilyMemberById(id).getValue().get(0);
        return mRepository.getFamilyMemberById(id).get(0);
    }

    public FamilyMember getRoot() {
        List<AncestorDescendant> test = mRepository.test(1);
        List<AncestorDescendant> ancestorDescendants = mRepository.anotherTest();
//        new Thread() {
//            public void run() {
//                List<AncestorDescendant> ancestorDescendants = mRepository.test(1);
//                List<AncestorDescendant> anotherTest = mRepository.anotherTest();
//            }
//        }.start();

//        List<AncestorDescendant> ancestorDescendants = mRepository.test(1);
//        List<AncestorDescendant> anotherTest = mRepository.anotherTest();
//        List<AncestorDescendant> ancestorDescendants = mAllAncestorDescendants.getValue();
//        List<AncestorDescendant> ancestorDescendants = mRepository.getAllAncestorDescendants().getValue();
//        List<AncestorDescendant> ancestorDescendants = mRepository.getAllAncestorDescendants();

        AncestorDescendant root = ancestorDescendants.get(0);
        for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
            if (ancestorDescendant.getDescendantId() == root.getAncestorId()) {
                root = ancestorDescendant;
                root = reiterateAncestorDescendants(root, ancestorDescendants);
            }
        }

        FamilyMember familyMember = getFamilyMemberById(root.getAncestorId());
        return familyMember;
//        return new FamilyMember("test", "test", "test", "test");
    }

    private AncestorDescendant reiterateAncestorDescendants(AncestorDescendant root, List<AncestorDescendant> ancestorDescendants) {
        for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
            if (ancestorDescendant.getDescendantId() == root.getAncestorId()) {
                root = ancestorDescendant;
            }
        }
        return root;
    }

//    public List<FamilyMember> makeFamilyTree() {
//        FamilyMember root = getRoot();
//        List<AncestorDescendant> ancestorDescendants = mRepository.getAllAncestorDescendants().getValue();
//
//    }

    public List<FamilyMember> makeFamilyTree(FamilyMember root) {
        List<AncestorDescendant> ancestorDescendants = mRepository.test(root.getFamilyMemberId());
//        List<AncestorDescendant> ancestorDescendants = mRepository.getAncestorDescendantsByAncestorId(root.getFamilyMemberId()).getValue();
//        List<AncestorDescendant> ancestorDescendants = mRepository.getAncestorDescendantsByAncestorId(root.getFamilyMemberId());

        if (ancestorDescendants == null) {
            List<FamilyMember> grandchildren = new ArrayList<>();
//            grandchildren.add(root);
            return grandchildren;
        }
        else {
            List<FamilyMember> children = new ArrayList<>();
            for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
                FamilyMember child = getFamilyMemberById(ancestorDescendant.getDescendantId());

                // recurse
                List<FamilyMember> grandchildren = makeFamilyTree(child);
                child.setChildren(grandchildren);

//            root.addChild(child);
                children.add(child);
            }
            return children;
        }
    }

//    private static class GetAncestorDescendants extends AsyncTask<Void, Void, List<AncestorDescendant>> {
//
//        @Override
//        protected List<AncestorDescendant> doInBackground(Void... voids) {
//            return
//        }
//    }

    public void sync(int familyTreeId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> mRepository.sync(familyTreeId));
    }
}
