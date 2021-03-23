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
public class Email {

    @PrimaryKey
    @NonNull
    private int emailId;

    @NonNull
    private int familyMemberId;

    @NonNull
    private String email;

    public Email(@NonNull int emailId, @NonNull int familyMemberId, @NonNull String email) {
        this.emailId = emailId;
        this.familyMemberId = familyMemberId;
        this.email = email;
    }

    public int getEmailId() {
        return emailId;
    }

    public int getFamilyMemberId() {
        return getFamilyMemberId();
    }

    public String getEmail() {
        return email;
    }
}
