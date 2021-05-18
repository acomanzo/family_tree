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
    private final int contactInformationId;

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
    public PhoneNumber(int contactInformationId, @NonNull String phoneNumber, int contactInformationServerId) {
        this.contactInformationId = contactInformationId;
        this.phoneNumber = phoneNumber;
        this.contactInformationServerId = contactInformationServerId;
    }

    public PhoneNumber(int phoneNumberId, int contactInformationId, @NonNull String phoneNumber, @NonNull String createdAt, @NonNull String updatedAt) {
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

    @NonNull
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

    @NonNull
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NonNull String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
