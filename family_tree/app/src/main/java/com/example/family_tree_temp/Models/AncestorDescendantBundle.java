package com.example.family_tree_temp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AncestorDescendantBundle implements Parcelable {

    private final FamilyMember newFamilyMember;

    private final FamilyMember existingFamilyMember;

    private final int depth;

    private int serverId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

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

    public AncestorDescendantBundle(FamilyMember newFamilyMember, FamilyMember existingFamilyMember, int depth) {
        this(newFamilyMember, existingFamilyMember, depth, -1, "", "");
    }

    public AncestorDescendantBundle(FamilyMember newFamilyMember, FamilyMember existingFamilyMember, int depth, int serverId, @NonNull String createdAt, @NonNull String updatedAt) {
        this.newFamilyMember = newFamilyMember;
        this.existingFamilyMember = existingFamilyMember;
        this.depth = depth;
        this.serverId = serverId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected AncestorDescendantBundle(Parcel in) {
        String firstName = in.readString();
        String lastName = in.readString();

        String birthDate = in.readString();
        String gender = in.readString();
        int familyTreeId = in.readInt();
        String createdAt = in.readString();
        String updatedAt = in.readString();
        FamilyMember newFamilyMember = new FamilyMember(firstName, lastName, birthDate, gender, familyTreeId, createdAt, updatedAt);

        firstName = in.readString();
        lastName = in.readString();
        birthDate = in.readString();
        gender = in.readString();
        familyTreeId = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        FamilyMember existingFamilyMember = new FamilyMember(firstName, lastName, birthDate, gender, familyTreeId, createdAt, updatedAt);

        int depth = in.readInt();
        int serverId = in.readInt();

        this.newFamilyMember = newFamilyMember;
        this.existingFamilyMember = existingFamilyMember;
        this.depth = depth;
        this.serverId = serverId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // first family member
        dest.writeString(newFamilyMember.getFirstName());
        dest.writeString(newFamilyMember.getLastName());
        dest.writeString(newFamilyMember.getBirthDate());
        dest.writeString(newFamilyMember.getGender());
        dest.writeInt(newFamilyMember.getFamilyTreeId());
        dest.writeString(newFamilyMember.getCreatedAt());
        dest.writeString(newFamilyMember.getUpdatedAt());

        // second family member
        dest.writeString(existingFamilyMember.getFirstName());
        dest.writeString(existingFamilyMember.getLastName());
        dest.writeString(existingFamilyMember.getBirthDate());
        dest.writeString(existingFamilyMember.getGender());
        dest.writeInt(existingFamilyMember.getFamilyTreeId());
        dest.writeString(existingFamilyMember.getCreatedAt());
        dest.writeString(existingFamilyMember.getUpdatedAt());

        dest.writeInt(depth);
        dest.writeInt(serverId);
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

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @NonNull
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        newFamilyMember.setCreatedAt(createdAt);
    }

    @NonNull
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        newFamilyMember.setUpdatedAt(updatedAt);
    }
}
