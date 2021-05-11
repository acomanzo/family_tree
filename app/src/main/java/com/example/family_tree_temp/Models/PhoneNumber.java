package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = ContactInformation.class,
        parentColumns = "contactInformationId",
        childColumns = "contactInformationId",
        onDelete = ForeignKey.CASCADE
)})
public class PhoneNumber {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int phoneNumberId;

    @NonNull
    private int contactInformationId;

    @NonNull
    private String phoneNumber;

    private int serverId;

    @Ignore
    private int contactInformationServerId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    public PhoneNumber(@NonNull int contactInformationId, @NonNull String phoneNumber, int contactInformationServerId) {
        this.contactInformationId = contactInformationId;
        this.phoneNumber = phoneNumber;
        this.contactInformationServerId = contactInformationServerId;
    }

    public PhoneNumber(@NonNull int phoneNumberId, @NonNull int contactInformationId, @NonNull String phoneNumber, @NonNull String createdAt, @NonNull String updatedAt) {
        this.phoneNumberId = phoneNumberId;
        this.contactInformationId = contactInformationId;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    public int getContactInformationId() {
        return contactInformationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getContactInformationServerId() {
        return contactInformationServerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
