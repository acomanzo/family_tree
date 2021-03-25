package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diagnosis {

    @PrimaryKey
    @NonNull
    private int diagnosisId;

    @NonNull
    private String label;

    public Diagnosis(int diagnosisId, String label) {
        this.diagnosisId = diagnosisId;
        this.label = label;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public String getLabel() {
        return label;
    }
}
