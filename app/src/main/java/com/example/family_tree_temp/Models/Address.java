package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String zipCode;

    @NonNull
    private int contactInformationId;

    private int serverId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    private int contactInformationServerId;

    @Ignore
    public Address(int contactInformationId, int houseNumber, @NonNull String streetName, @NonNull String city, @NonNull String state, @NonNull String zipCode, int contactInformationServerId) {
        this.contactInformationId = contactInformationId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.contactInformationServerId = contactInformationServerId;
    }

    public Address(int addressId, int contactInformationId, int houseNumber, @NonNull String streetName, String extra, @NonNull String city, @NonNull String state, @NonNull String zipCode, @NonNull String createdAt, @NonNull String updatedAt) {
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

    @NonNull
    public String getStreetName() {
        return streetName;
    }

    public String getExtra() {
        return extra;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    @NonNull
    public String getState() {
        return state;
    }

    @NonNull
    public String getZipCode() {
        return zipCode;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getContactInformationId() {
        return contactInformationId;
    }

    public void setContactInformationId(int contactInformationId) {
        this.contactInformationId = contactInformationId;
    }

    public int getContactInformationServerId() {
        return contactInformationServerId;
    }

    @NonNull
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NonNull String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
