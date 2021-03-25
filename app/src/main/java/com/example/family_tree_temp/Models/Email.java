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
public class Email {

    @PrimaryKey
    @NonNull
    private int emailId;

    @NonNull
    private int contactInformationId;

    @NonNull
    private String email;

    public Email(@NonNull int emailId, @NonNull int contactInformationId, @NonNull String email) {
        this.emailId = emailId;
        this.contactInformationId = contactInformationId;
        this.email = email;
    }

    public int getEmailId() {
        return emailId;
    }

    public int getContactInformationId() {
        return contactInformationId;
    }

    public String getEmail() {
        return email;
    }
}
