package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = {@ForeignKey(
//        entity = FamilyMember.class,
//        parentColumns = "familyMemberId",
//        childColumns = "familyMemberId",
//        onDelete = ForeignKey.CASCADE
//), @ForeignKey(
//        entity = City.class,
//        parentColumns = "cityId",
//        childColumns = "cityId",
//        onDelete = ForeignKey.CASCADE
//), @ForeignKey(
//        entity = State.class,
//        parentColumns = "stateId",
//        childColumns = "stateId",
//        onDelete = ForeignKey.CASCADE
//), @ForeignKey(
//        entity = Zipcode.class,
//        parentColumns = "zipcodeId",
//        childColumns = "zipcodeId",
//        onDelete = ForeignKey.CASCADE
//)})
@Entity(foreignKeys = {@ForeignKey(
        entity = ContactInformation.class,
        parentColumns = "contactInformationId",
        childColumns = "contactInformationId",
        onDelete = ForeignKey.CASCADE
)})
public class Address {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int addressId;

    @NonNull
    private int houseNumber;

    @NonNull
    private String streetName;

    private String extra;

//    @NonNull
//    private int cityId;
//
//    @NonNull
//    private int stateId;
//
//    @NonNull
//    private int zipcodeId;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String zipCode;

    private int contactInformationId;

    private int serverId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    private int contactInformationServerId;

//    public Address(@NonNull int addressId, @NonNull int familyMemberId, @NonNull int houseNumber, @NonNull String streetName, String extra, @NonNull int cityId, @NonNull int stateId, @NonNull int zipcodeId) {
//        this.addressId = addressId;
//        this.familyMemberId = familyMemberId;
//        this.houseNumber = houseNumber;
//        this.streetName = streetName;
//        this.cityId = cityId;
//        this.stateId = stateId;
//        this.zipcodeId = zipcodeId;
//    }

    @Ignore
    public Address(int contactInformationId, int houseNumber, String streetName, String city, String state, String zipCode, int contactInformationServerId) {
        this.contactInformationId = contactInformationId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.contactInformationServerId = contactInformationServerId;
    }

    public Address(@NonNull int addressId, @NonNull int contactInformationId, @NonNull int houseNumber, @NonNull String streetName, String extra, @NonNull String city, @NonNull String state, @NonNull String zipCode, @NonNull String createdAt, @NonNull String updatedAt) {
        this.addressId = addressId;
        this.contactInformationId = contactInformationId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getAddressId() {
        return addressId;
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

//    public int getCityId() {
//        return cityId;
//    }
//
//    public int getStateId() {
//        return stateId;
//    }
//
//    public int getZipcodeId() {
//        return zipcodeId;
//    }


    @NonNull
    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

//    @Override
//    public String toString() {
//        return houseNumber + " " + streetName + ", " + cityId + ", " + stateId + " " + zipcodeId;
//    }

    public int getServerId() {
        return serverId;
    }

    public int getContactInformationId() {
        return contactInformationId;
    }

    public int getContactInformationServerId() {
        return contactInformationServerId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setContactInformationId(int contactInformationId) {
        this.contactInformationId = contactInformationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
