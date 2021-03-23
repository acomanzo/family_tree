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
    private String city;

    @NonNull
    private String state;

    @NonNull
    private int zipcode;

    public Address(@NonNull int addressId, @NonNull int familyMemberId, @NonNull int houseNumber, @NonNull String streetName, String extra, @NonNull String city, @NonNull String state, @NonNull int zipcode) {
        this.addressId = addressId;
        this.familyMemberId = familyMemberId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
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

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipcode() {
        return zipcode;
    }

    @Override
    public String toString() {
        return houseNumber + " " + streetName + ", " + city + ", " + state + " " + zipcode;
    }
}
