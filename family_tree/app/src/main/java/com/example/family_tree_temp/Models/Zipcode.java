package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Zipcode {

    @PrimaryKey
    @NonNull
    private int zipcodeId;

    @NonNull
    private String label;

    public Zipcode(int zipcodeId, String label) {
        this.zipcodeId = zipcodeId;
        this.label = label;
    }

    public int getZipcodeId() {
        return zipcodeId;
    }

    public String getLabel() {
        return label;
    }
}
