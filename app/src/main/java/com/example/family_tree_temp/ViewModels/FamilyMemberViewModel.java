package com.example.family_tree_temp.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.family_tree_temp.Database.FamilyTreeSqlDatabase;
import com.example.family_tree_temp.Models.AncestorDescendant;
import com.example.family_tree_temp.Models.AncestorDescendantBundle;
import com.example.family_tree_temp.Models.FamilyTree;
import com.example.family_tree_temp.Repository.FamilyMemberRepository;
import com.example.family_tree_temp.Models.FamilyMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FamilyMemberViewModel extends AndroidViewModel {

    private final FamilyMemberRepository mRepository;

    private final LiveData<List<FamilyMember>> mAllFamilyMembers;
    private LiveData<List<AncestorDescendant>> mAllAncestorDescendants;

    private final FamilyTreeSqlDatabase familyTreeSqlDatabase;

    public FamilyMemberViewModel (Application application) {
        super(application);
        mRepository = new FamilyMemberRepository(application);
        mAllFamilyMembers = mRepository.getAllFamilyMembers();
        mAllAncestorDescendants = mRepository.getAllAncestorDescendants();
        familyTreeSqlDatabase = new FamilyTreeSqlDatabase();
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

    public FamilyMember getFamilyMemberAtIndex(int position, int familyTreeId) {
        return getFamilyMembersByFamilyTreeId(familyTreeId).get(position);
    }

    public List<FamilyMember> getFamilyMembersByFamilyTreeId(int familyTreeId) {
        List<FamilyMember> familyMembers = new ArrayList<>();
        for (FamilyMember familyMember : mAllFamilyMembers.getValue()) {
            if (familyMember.getFamilyTreeId() == familyTreeId) {
                familyMembers.add(familyMember);
            }
        }
        return familyMembers;
    }

    public void insertDescendant(AncestorDescendantBundle ancestorDescendantBundle) {
        mRepository.insertDescendant(ancestorDescendantBundle);
    }

    public void insertAncestor(AncestorDescendantBundle ancestorDescendantBundle) {
        mRepository.insertAncestor(ancestorDescendantBundle);
    }

    public FamilyMember getFamilyMemberByServerId(int serverId) {
        for (FamilyMember familyMember : mAllFamilyMembers.getValue()) {
            if (familyMember.getServerId() == serverId) {
                return familyMember;
            }
        }
        return null;
    }

    public FamilyMember getFamilyMemberByLocalId(int id) {
        for (FamilyMember familyMember : mAllFamilyMembers.getValue()) {
            if (familyMember.getFamilyMemberId() == id) {
                return familyMember;
            }
        }
        return null;
    }

    public FamilyMember getFamilyMemberById(int id) {
        return mRepository.getFamilyMemberById(id).get(0);
    }

    public List<FamilyMember> getRoots(int familyTreeId) {
        /*
        TODO: find a way to find all roots in the AncestorDescendant table... rn the function
            assumes that there is only one bloodline modeled in the tree, but there could be
            more than one that do not have a common ancestor
         */

        List<AncestorDescendant> ancestorDescendants = mRepository.getAllAncestorDescendantsNotLive();
        List<AncestorDescendant> temp = new ArrayList<>();
        for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
            int ancestorId = ancestorDescendant.getAncestorId();
            FamilyMember ancestor = getFamilyMemberByLocalId(ancestorId);
            if (ancestor != null) {
                int ancestorFamilyTreeId = ancestor.getFamilyTreeId();
                if (familyTreeId == ancestorFamilyTreeId) {
                    temp.add(ancestorDescendant);
                }
            }
        }

        ancestorDescendants = temp;

        List<FamilyMember> roots = new ArrayList<>();
        if (ancestorDescendants.size() > 0) {
            AncestorDescendant root = ancestorDescendants.get(0);
            for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
                if (ancestorDescendant.getDescendantId() == root.getAncestorId()) {
                    root = ancestorDescendant;
                    root = reiterateAncestorDescendants(root, ancestorDescendants);
                }
            }

            FamilyMember familyMember = getFamilyMemberById(root.getAncestorId());
            if (familyMember.getFamilyTreeId() == familyTreeId)
                roots.add(familyMember);

            List<FamilyMember> familyMembers = getFamilyMembersByFamilyTreeId(familyTreeId);
            for (FamilyMember fm : familyMembers) {
                boolean familyMemberIsARoot = true;
                for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
                    int familyMemberId = fm.getFamilyMemberId();
                    int ancestorId = ancestorDescendant.getAncestorId();
                    int descendantId = ancestorDescendant.getDescendantId();
                    if (familyMemberId == ancestorId || familyMemberId == descendantId) {
                        familyMemberIsARoot = false;
                        break;
                    }
                }

                // if the family member has no relationships, then make them a root
                if (familyMemberIsARoot) {
                    roots.add(fm);
                }
            }
        } else {
            // if there are no relationships, then make every family member a root
            roots.addAll(getFamilyMembersByFamilyTreeId(familyTreeId));
        }
        return roots;
    }

    private AncestorDescendant reiterateAncestorDescendants(AncestorDescendant root, List<AncestorDescendant> ancestorDescendants) {
        for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
            if (ancestorDescendant.getDescendantId() == root.getAncestorId()) {
                root = ancestorDescendant;
            }
        }
        return root;
    }

    public void makeFamilyTreeFor(FamilyMember familyMember) {
        List<FamilyMember> familyMembers = makeFamilyTree(familyMember);
        familyMember.setChildren(familyMembers);
    }

    private List<FamilyMember> makeFamilyTree(FamilyMember root) {
        List<AncestorDescendant> ancestorDescendants = mRepository.getAncestorDescendantsByAncestorId(root.getFamilyMemberId());

        if (ancestorDescendants == null) {
            List<FamilyMember> grandchildren = new ArrayList<>();
            return grandchildren;
        } else {
            List<FamilyMember> children = new ArrayList<>();
            for (AncestorDescendant ancestorDescendant : ancestorDescendants) {
                FamilyMember child = getFamilyMemberById(ancestorDescendant.getDescendantId());

                // recurse
                List<FamilyMember> grandchildren = makeFamilyTree(child);
                child.setChildren(grandchildren);

                children.add(child);
            }
            return children;
        }
    }

    public void sync(int familyTreeId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<FamilyMember> familyMembers = mAllFamilyMembers.getValue();
            if (familyMembers == null) {
                try {
                    // if the value is null, then it will be null in sync(), and
                    // a new family member will be inserted when it shouldn't be
                    Thread.sleep(3000);
                    mRepository.sync(familyTreeId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                mRepository.sync(familyTreeId);
            }
        });
    }
}
