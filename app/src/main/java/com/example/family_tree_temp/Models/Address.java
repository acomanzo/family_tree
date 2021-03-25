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
), @ForeignKey(
        entity = City.class,
        parentColumns = "cityId",
        childColumns = "cityId",
        onDelete = ForeignKey.CASCADE
)})
public class Address {

    @PrimaryKey
    @NonNull
    private int addressId;

    @NonNull
    private int familyMemberId;

    @NonNull
    private int houseNumber;

    @NonNull
    private String streetName;

    private String extra;

    @NonNull
    private int cityId;

    @NonNull
    private int stateId;

    @NonNull
    private int zipcodeId;

    public Address(@NonNull int addressId, @NonNull int familyMemberId, @NonNull int houseNumber, @NonNull String streetName, String extra, @NonNull int cityId, @NonNull int stateId, @NonNull int zipcodeId) {
        this.addressId = addressId;
        this.familyMemberId = familyMemberId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.cityId = cityId;
        this.stateId = stateId;
        this.zipcodeId = zipcodeId;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getExtra() {
        return extra;
    }

    public int getCityId() {
        return cityId;
    }

    public int getStateId() {
        return stateId;
    }

    public int getZipcodeId() {
        return zipcodeId;
    }

    @Override
    public String toString() {
        return houseNumber + " " + streetName + ", " + cityId + ", " + stateId + " " + zipcodeId;
    }
}
