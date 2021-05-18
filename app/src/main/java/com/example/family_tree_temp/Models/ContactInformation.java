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

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    public ContactInformation(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public ContactInformation(int contactInformationId, int familyMemberId, @NonNull String createdAt, @NonNull String updatedAt) {
        this.contactInformationId = contactInformationId;
        this.familyMemberId = familyMemberId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    @NonNull
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
