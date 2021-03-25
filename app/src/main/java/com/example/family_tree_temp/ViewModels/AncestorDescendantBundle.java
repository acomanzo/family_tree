package com.example.family_tree_temp.ViewModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.Models.AncestorDescendant;
import com.example.family_tree_temp.Models.FamilyMember;

import java.util.ArrayList;

public class AncestorDescendantBundle implements Parcelable {

    private FamilyMember newFamilyMember;

    private int existingFamilyMemberId;

    private int depth;

    public AncestorDescendantBundle(FamilyMember newFamilyMember, int existingFamilyMemberId, int depth) {
        this.newFamilyMember = newFamilyMember;
        this.existingFamilyMemberId = existingFamilyMemberId;
        this.depth = depth;
    }

    protected AncestorDescendantBundle(Parcel in) {
        String firstName = in.readString();
        String lastName = in.readString();
        int age = in.readInt();
        int genderId = in.readInt();
        FamilyMember newFamilyMember = new FamilyMember(firstName, lastName, age, genderId);

        int existingFamilyMemberId = in.readInt();
        int depth = in.readInt();

        this.newFamilyMember = newFamilyMember;
        this.existingFamilyMemberId = existingFamilyMemberId;
        this.depth = depth;
    }

    public FamilyMember getNewFamilyMember() {
        return newFamilyMember;
    }

    public int getExistingFamilyMemberId() {
        return existingFamilyMemberId;
    }

    public int getDepth() {
        return depth;
    }

    public static final Creator<AncestorDescendantBundle> CREATOR = new Creator<AncestorDescendantBundle>() {
        @Override
        public AncestorDescendantBundle createFromParcel(Parcel source) {
            return new AncestorDescendantBundle(source);
        }

        @Override
        public AncestorDescendantBundle[] newArray(int size) {
            return new AncestorDescendantBundle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newFamilyMember.getFirstName());
        dest.writeString(newFamilyMember.getLastName());
        dest.writeInt(newFamilyMember.getAge());
        dest.writeInt(newFamilyMember.getGenderId());

        dest.writeInt(existingFamilyMemberId);
        dest.writeInt(depth);
    }


}
