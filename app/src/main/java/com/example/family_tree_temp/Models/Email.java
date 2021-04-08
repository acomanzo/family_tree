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
public class Email {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int emailId;

    @NonNull
    private int contactInformationId;

    @NonNull
    private String email;

    private int serverId;

    @Ignore
    private int contactInformationServerId;

    @Ignore
    public Email(@NonNull int contactInformationId, @NonNull String email, int contactInformationServerId) {
        this.contactInformationId = contactInformationId;
        this.email = email;
        this.contactInformationServerId = contactInformationServerId;
    }

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
