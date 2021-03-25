package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Gender {

    @PrimaryKey(autoGenerate = true)
    private int genderId;

    private String label;

    @Ignore
    public Gender(@NonNull String label) {
        this.label = label;
    }

    public Gender(int genderId, @NonNull String label) {
        this.genderId = genderId;
        this.label = label;
    }

    public int getGenderId() {
        return genderId;
    }

    public String getLabel() {
        return label;
    }
}
