package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = FamilyMember.class,
        parentColumns = "familyMemberId",
        childColumns = "familyMemberId",
        onDelete = ForeignKey.CASCADE
)})
public class PhoneNumber {

    @PrimaryKey
    @NonNull
    private int phoneNumberId;

    @NonNull
    private int familyMemberId;

    @NonNull
    private String phoneNumber;

    public PhoneNumber(@NonNull int phoneNumberId, @NonNull int familyMemberId, @NonNull String phoneNumber) {
        this.phoneNumberId = phoneNumberId;
        this.familyMemberId = familyMemberId;
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
