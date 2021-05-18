package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class State {

    @PrimaryKey
    @NonNull
    private int stateId;

    private String label;

    public State(int stateId, String label) {
        this.stateId = stateId;
        this.label = label;
    }

    public int getStateId() {
        return stateId;
    }

    public String getLabel() {
        return label;
    }
}
