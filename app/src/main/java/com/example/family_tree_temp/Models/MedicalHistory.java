package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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

    @PrimaryKey
    @NonNull
    private int medicalHistoryNoteId;

    private int date;

//    @NonNull
//    private int diagnosisId;

    @NonNull
    private String diagnosis;

    @NonNull
    private int familyMemberId;

    private String note;

//    public MedicalHistory(@NonNull int medicalHistoryNoteId, int date, @NonNull int diagnosisId, @NonNull int familyMemberId, String note) {
//        this.medicalHistoryNoteId =  medicalHistoryNoteId;
//        this.date = date;
//        this.diagnosisId = diagnosisId;
//        this.familyMemberId = familyMemberId;
//        this.note = note;
//    }

    public MedicalHistory(@NonNull int medicalHistoryNoteId, int date, @NonNull String diagnosis, @NonNull int familyMemberId, String note) {
        this.medicalHistoryNoteId =  medicalHistoryNoteId;
        this.date = date;
        this.diagnosis = diagnosis;
        this.familyMemberId = familyMemberId;
        this.note = note;
    }

    public int getMedicalHistoryNoteId() {
        return medicalHistoryNoteId;
    }

    public int getDate() {
        return date;
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
}
