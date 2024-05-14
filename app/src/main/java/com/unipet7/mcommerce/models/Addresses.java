package com.unipet7.mcommerce.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Addresses implements Parcelable {
    String name;
    String phonenumber;
    String province;
    String street;

    String addressId; // UID from Firebase

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Addresses() {
    }


    public Addresses(String name, String phonenumber, String province, String street) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.province = province;
        this.street = street;
    }

    protected Addresses(Parcel in) {
        name = in.readString();
        phonenumber = in.readString();
        province = in.readString();
        street = in.readString();
        addressId = in.readString();
    }

    public static final Creator<Addresses> CREATOR = new Creator<>() {
        @Override
        public Addresses createFromParcel(Parcel in) {
            return new Addresses(in);
        }

        @Override
        public Addresses[] newArray(int size) {
            return new Addresses[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phonenumber);
        dest.writeString(province);
        dest.writeString(street);
        dest.writeString(addressId);
    }
}
