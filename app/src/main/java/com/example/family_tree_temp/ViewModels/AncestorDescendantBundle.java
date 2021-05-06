package com.example.family_tree_temp.ViewModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.family_tree_temp.Adaptors.Person;
import com.example.family_tree_temp.Models.AncestorDescendant;
import com.example.family_tree_temp.Models.FamilyMember;

import java.util.ArrayList;

public class AncestorDescendantBundle implements Parcelable {

    private FamilyMember newFamilyMember;

    private FamilyMember existingFamilyMember;

    private int depth;

    private int serverId;

    public AncestorDescendantBundle(FamilyMember newFamilyMember, FamilyMember existingFamilyMember, int depth) {
        this(newFamilyMember, existingFamilyMember, depth, -1);
    }

    public AncestorDescendantBundle(FamilyMember newFamilyMember, FamilyMember existingFamilyMember, int depth, int serverId) {
        this.newFamilyMember = newFamilyMember;
        this.existingFamilyMember = existingFamilyMember;
        this.depth = depth;
        this.serverId = serverId;
    }

    protected AncestorDescendantBundle(Parcel in) {
        String firstName = in.readString();
        String lastName = in.readString();
//        int age = in.readInt();
//        int genderId = in.readInt();
//        FamilyMember newFamilyMember = new FamilyMember(firstName, lastName, age, genderId);

        String birthDate = in.readString();
        String gender = in.readString();
        FamilyMember newFamilyMember = new FamilyMember(firstName, lastName, birthDate, gender);

        firstName = in.readString();
        lastName = in.readString();
        birthDate = in.readString();
        gender = in.readString();
        FamilyMember existingFamilyMember = new FamilyMember(firstName, lastName, birthDate, gender);

        int depth = in.readInt();
        int serverId = in.readInt();

        this.newFamilyMember = newFamilyMember;
        this.existingFamilyMember = existingFamilyMember;
        this.depth = depth;
        this.serverId = serverId;
    }

    public FamilyMember getNewFamilyMember() {
        return newFamilyMember;
    }

    public FamilyMember getExistingFamilyMember() {
        return existingFamilyMember;
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
//        dest.writeInt(newFamilyMember.getAge());
//        dest.writeInt(newFamilyMember.getGenderId());
        dest.writeString(newFamilyMember.getBirthDate());
        dest.writeString(newFamilyMember.getGender());

        dest.writeString(existingFamilyMember.getFirstName());
        dest.writeString(existingFamilyMember.getLastName());
        dest.writeString(existingFamilyMember.getBirthDate());
        dest.writeString(existingFamilyMember.getGender());

        dest.writeInt(depth);
        dest.writeInt(serverId);
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
