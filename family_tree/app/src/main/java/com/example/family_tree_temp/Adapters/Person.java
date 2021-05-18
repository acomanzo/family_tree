package com.example.family_tree_temp.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Person implements Parcelable {

    private int familyMemberId;
    private String firstName;
    private String lastName;
    private String gender;

    private String birthDate;

    private ArrayList<Person> children;
    private boolean expanded;
    private int image;

    private int futureRelativePosition;
    private String futureRelativeRelationship;

    public Person(@NonNull String firstName, @NonNull String lastName, @NonNull String birthDate, @NonNull String gender) {
        this(firstName, lastName, birthDate, gender, null);
    }

    public Person(@NonNull String firstName, @NonNull String lastName, @NonNull String birthDate, @NonNull String gender, String relationship) {
        this(firstName, lastName, birthDate, gender, -1, relationship);
    }

    public Person(@NonNull String firstName, @NonNull String lastName, @NonNull String birthDate, @NonNull String gender, int futureRelativePosition, String futureRelativeRelationship) {
        children = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;

        this.futureRelativePosition = futureRelativePosition;
        this.futureRelativeRelationship = futureRelativeRelationship;
    }

    protected Person(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        birthDate = in.readString();
        String streetNumber = in.readString();
        String streetName = in.readString();
        String townCity = in.readString();
        String state = in.readString();
        String country = in.readString();
        String zipcode = in.readString();

        children = new ArrayList<>();

        this.futureRelativePosition = in.readInt();
        this.futureRelativeRelationship = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public void addChild(Person person) {
        children.add(person);
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(birthDate);
        dest.writeInt(futureRelativePosition);
        dest.writeString(futureRelativeRelationship);
    }

    public int getFutureRelativePosition() {
        return futureRelativePosition;
    }

    public String getFutureRelativeRelationship() {
        return futureRelativeRelationship;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!firstName.equals(((Person) o).getFirstName())) return false;
        return firstName != null ? firstName.equals(person.getFirstName()) : person.getFirstName() == null;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
