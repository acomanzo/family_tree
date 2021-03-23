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
public class MedicalHistoryNote {

    @PrimaryKey
    @NonNull
    private int medicalHistoryNoteId;

    @NonNull
    private int date;

    @NonNull
    private String diagnosis;

    @NonNull
    private int familyMemberId;

    public MedicalHistoryNote(@NonNull int medicalHistoryNoteId, @NonNull int date, @NonNull String diagnosis, @NonNull int familyMemberId) {
        this.medicalHistoryNoteId =  medicalHistoryNoteId;
        this.date = date;
        this.diagnosis = diagnosis;
        this.familyMemberId = familyMemberId;
    }

    public int getMedicalHistoryNoteId() {
        return medicalHistoryNoteId;
    }

    public int getDate() {
        return date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }
}
