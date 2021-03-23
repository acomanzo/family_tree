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
public class ContactInformation {

    @PrimaryKey
    @NonNull
    private int contactInformationId;

    @NonNull
    private int familyMemberId;

    public ContactInformation(@NonNull int contactInformationId, @NonNull int familyMemberId) {
        this.contactInformationId = contactInformationId;
        this.familyMemberId = familyMemberId;
    }

    public int getContactInformationId() {
        return contactInformationId;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }
}
