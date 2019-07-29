package com.onedata.sunexchange.logintask.bean;

import com.google.gson.annotations.SerializedName;

public class KycFirstStep {

    private Integer   id;
    @SerializedName("firstName")
    private String    firstName;
    @SerializedName("lastName")
    private String    lastName;
    @SerializedName("middleName")
    private String    middleName;
    @SerializedName("dateOfBirth")
    private String    dateOfBirth;
    @SerializedName("residentialAddress")
    private String    residentialAddress;
    @SerializedName("area")
    private String    area;
    @SerializedName("city")
    private String    city;
    @SerializedName("street")
    private String    street;
    @SerializedName("state")
    private String    state;
    @SerializedName("country")
    private String    country;
    @SerializedName("pincode")
    private Integer   pincode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }


}
