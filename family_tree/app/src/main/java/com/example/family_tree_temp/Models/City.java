package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey
    @NonNull
    private int cityId;

    @NonNull
    private String label;

    public City(int cityId, String label) {
        this.cityId = cityId;
        this.label = label;
    }

    public int getCityId() {
        return cityId;
    }

    public String getLabel() {
        return label;
    }
}
