package com.unipet7.mcommerce.models;

public class Addresses {
    String name;
    String phonenumber;
    String province;
    String street;

    public Addresses(String name, String phonenumber, String province, String street) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.province = province;
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
