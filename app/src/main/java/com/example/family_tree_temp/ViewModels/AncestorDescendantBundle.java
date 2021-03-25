package com.example.family_tree_temp.ViewModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.Models.AncestorDescendant;
import com.example.family_tree_temp.Models.FamilyMember;

import java.util.ArrayList;

public class AncestorDescendantBundle implements Parcelable {

    private FamilyMember descendant;

    private int ancestorId;

    private int depth;

    public AncestorDescendantBundle(FamilyMember descendant, int ancestorId, int depth) {
        this.descendant = descendant;
        this.ancestorId = ancestorId;
        this.depth = depth;
    }

    protected AncestorDescendantBundle(Parcel in) {
        String firstName = in.readString();
        String lastName = in.readString();
        int age = in.readInt();
        int genderId = in.readInt();
        FamilyMember descendant = new FamilyMember(firstName, lastName, age, genderId);

        int ancestorId = in.readInt();
        int depth = in.readInt();

        this.descendant = descendant;
        this.ancestorId = ancestorId;
        this.depth = depth;
    }

    public FamilyMember getDescendant() {
        return descendant;
    }

    public int getAncestorId() {
        return ancestorId;
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
        dest.writeString(descendant.getFirstName());
        dest.writeString(descendant.getLastName());
        dest.writeInt(descendant.getAge());
        dest.writeInt(descendant.getGenderId());

        dest.writeInt(ancestorId);
        dest.writeInt(depth);
    }


}
