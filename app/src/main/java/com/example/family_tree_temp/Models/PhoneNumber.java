package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = ContactInformation.class,
        parentColumns = "contactInformationId",
        childColumns = "contactInformationId",
        onDelete = ForeignKey.CASCADE
)})
public class PhoneNumber {

    @PrimaryKey
    @NonNull
    private int phoneNumberId;

    @NonNull
    private int contactInformationId;

    @NonNull
    private String phoneNumber;

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
}
