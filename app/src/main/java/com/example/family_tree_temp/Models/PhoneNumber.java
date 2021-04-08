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

    @Ignore
    public PhoneNumber(@NonNull int contactInformationId, @NonNull String phoneNumber, int contactInformationServerId) {
        this.contactInformationId = contactInformationId;
        this.phoneNumber = phoneNumber;
        this.contactInformationServerId = contactInformationServerId;
    }

    public PhoneNumber(@NonNull int phoneNumberId, @NonNull int contactInformationId, @NonNull String phoneNumber) {
        this.phoneNumberId = phoneNumberId;
        this.contactInformationId = contactInformationId;
        this.phoneNumber = phoneNumber;
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
}
