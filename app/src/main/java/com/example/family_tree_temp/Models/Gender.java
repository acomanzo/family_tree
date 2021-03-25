package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Gender {

    @PrimaryKey
    @NonNull
    private int genderId;

    private String label;

    public Gender(@NonNull int genderId, @NonNull String label) {
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
