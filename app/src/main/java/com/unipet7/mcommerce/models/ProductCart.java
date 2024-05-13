package com.unipet7.mcommerce.models;

import java.io.Serializable;

public class ProductCart implements Serializable {
    private String productImageUrl;
    private String productName;
    private Double productPrice;
    private Double totalPrice;
    private Integer numOfProduct;
    private Integer productId;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNumOfProduct() {
        return numOfProduct;
    }

    public void setNumOfProduct(Integer numOfProduct) {
        this.numOfProduct = numOfProduct;
    }
    
}
