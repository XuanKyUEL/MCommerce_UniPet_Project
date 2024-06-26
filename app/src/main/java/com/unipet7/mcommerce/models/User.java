package com.unipet7.mcommerce.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String image;
    private Long mobile;
    private String fcmToken;

    public List<Addresses> addresses;

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }

    private List<Integer> favProductId;
    private List<Integer> orderHistoryId;

    public List<Integer> getFavProductId() {
        return favProductId;
    }

    public void setFavProductId(List<Integer> favProductId) {
        this.favProductId = favProductId;
    }

    public List<Integer> getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(List<Integer> orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    public User() {
        // Empty constructor required by Firebase
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        image = in.readString();
        mobile = in.readLong();
        fcmToken = in.readString();
        favProductId = new ArrayList<>();
        in.readList(favProductId, Integer.class.getClassLoader());
        orderHistoryId = new ArrayList<>();
        in.readList(orderHistoryId, Integer.class.getClassLoader());
        addresses = new ArrayList<>();
        in.readList(addresses, Addresses.class.getClassLoader());
        orders = new ArrayList<>();
        in.readList(orders, Order.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String uid, String email, String initName) {
        this.id = uid;
        this.email = email;
        this.name = initName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeLong(mobile != null ? mobile : 0L);
        dest.writeString(fcmToken);
        dest.writeList(favProductId);
        dest.writeList(orderHistoryId);
        dest.writeList(addresses);
        dest.writeList(orders);
    }
}
