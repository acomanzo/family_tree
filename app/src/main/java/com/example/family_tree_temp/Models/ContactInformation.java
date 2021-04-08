package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ContactInformation",
        foreignKeys = {@ForeignKey(
        entity = FamilyMember.class,
        parentColumns = "familyMemberId",
        childColumns = "familyMemberId",
        onDelete = ForeignKey.CASCADE
)})
public class ContactInformation {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int contactInformationId;

    @NonNull
    private int familyMemberId;

    private int serverId;

    @Ignore
    public ContactInformation(@NonNull int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

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

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }
}
