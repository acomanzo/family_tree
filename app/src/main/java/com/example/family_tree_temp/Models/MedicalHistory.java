package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = {@ForeignKey(
//        entity = FamilyMember.class,
//        parentColumns = "familyMemberId",
//        childColumns = "familyMemberId",
//        onDelete = ForeignKey.CASCADE
//), @ForeignKey(
//        entity = Diagnosis.class,
//        parentColumns = "diagnosisId",
//        childColumns = "diagnosisId",
//        onDelete = ForeignKey.CASCADE
//)})
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

//    @NonNull
//    private int diagnosisId;

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
    public MedicalHistory(String dateDiagnosed, String note, String diagnosis, int familyMemberId, int familyMemberServerId) {
        this.dateDiagnosed = dateDiagnosed;
        this.note = note;
        this.diagnosis = diagnosis;
        this.familyMemberId = familyMemberId;
        this.familyMemberServerId = familyMemberServerId;
    }

//    public MedicalHistory(@NonNull int medicalHistoryNoteId, int date, @NonNull int diagnosisId, @NonNull int familyMemberId, String note) {
//        this.medicalHistoryNoteId =  medicalHistoryNoteId;
//        this.date = date;
//        this.diagnosisId = diagnosisId;
//        this.familyMemberId = familyMemberId;
//        this.note = note;
//    }

    public MedicalHistory(@NonNull int medicalHistoryNoteId, String dateDiagnosed, @NonNull String diagnosis, @NonNull int familyMemberId, String note, @NonNull String createdAt, @NonNull String updatedAt) {
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

//    public int getDiagnosisId() {
//        return diagnosisId;
//    }

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
