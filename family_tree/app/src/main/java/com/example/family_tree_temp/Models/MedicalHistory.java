package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = FamilyMember.class,
        parentColumns = "familyMemberId",
        childColumns = "familyMemberId",
        onDelete = ForeignKey.CASCADE
)})
public class MedicalHistory {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int medicalHistoryNoteId;

    private String dateDiagnosed;

    @NonNull
    private String diagnosis;

    @NonNull
    private int familyMemberId;

    private String note;

    private int serverId;

    @Ignore
    private int familyMemberServerId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    public MedicalHistory(String dateDiagnosed, String note, @NonNull String diagnosis, int familyMemberId, int familyMemberServerId) {
        this.dateDiagnosed = dateDiagnosed;
        this.note = note;
        this.diagnosis = diagnosis;
        this.familyMemberId = familyMemberId;
        this.familyMemberServerId = familyMemberServerId;
    }

    public MedicalHistory(int medicalHistoryNoteId, String dateDiagnosed, @NonNull String diagnosis, int familyMemberId, String note, @NonNull String createdAt, @NonNull String updatedAt) {
        this.medicalHistoryNoteId =  medicalHistoryNoteId;
        this.dateDiagnosed = dateDiagnosed;
        this.diagnosis = diagnosis;
        this.familyMemberId = familyMemberId;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getMedicalHistoryNoteId() {
        return medicalHistoryNoteId;
    }

    public String getDateDiagnosed() {
        return dateDiagnosed;
    }

    @NonNull
    public String getDiagnosis() {
        return diagnosis;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public String getNote() {
        return note;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getFamilyMemberServerId() {
        return familyMemberServerId;
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
