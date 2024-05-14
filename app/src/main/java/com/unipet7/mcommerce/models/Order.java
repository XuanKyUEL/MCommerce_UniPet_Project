package com.unipet7.mcommerce.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {
    private String orderId;

    private String userId;

    private String paymentMethod;

    private Addresses oderAddress;

    private String orderDate;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Order(String paymentMethod, Addresses oderAddress, String status, ArrayList<ProductCart> products, Double totalPrice) {
        this.paymentMethod = paymentMethod;
        this.oderAddress = oderAddress;
        this.status = status;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    private String status;

    private ArrayList<ProductCart> products;

    private Double totalPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Addresses getOderAddress() {
        return oderAddress;
    }

    public void setOderAddress(Addresses oderAddress) {
        this.oderAddress = oderAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductCart> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order() {
    }
}
