package com.unipet7.mcommerce.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private int ProductId;

    private int CategoryId;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }

    private String ProductDescription, ProductImageUrl;



    int imvThumb;
    String productname;

    double productprice;
    double productratenum;
    double producttotalnum;
    double salepercent;
    double presaleprice;

    public Product() {
    }

    public Product(int imvThumb, String productname, double productprice, double productratenum, double producttotalnum, double salepercent, double presaleprice) {
        this.imvThumb = imvThumb;
        this.productname = productname;
        this.productprice = productprice;
        this.productratenum = productratenum;
        this.producttotalnum = producttotalnum;
        this.salepercent = salepercent;
        this.presaleprice = presaleprice;
    }

    public int getImvThumb() {
        return imvThumb;
    }

    public void setImvThumb(int imvThumb) {
        this.imvThumb = imvThumb;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getProductprice() {
        return productprice;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }

    public double getProductratenum() {
        return productratenum;
    }

    public void setProductratenum(double productratenum) {
        this.productratenum = productratenum;
    }

    public double getProducttotalnum() {
        return producttotalnum;
    }

    public void setProducttotalnum(double producttotalnum) {
        this.producttotalnum = producttotalnum;
    }

    public double getSalepercent() {
        return salepercent;
    }

    public void setSalepercent(double salepercent) {
        this.salepercent = salepercent;
    }

    public double getPresaleprice() {
        return presaleprice;
    }

    public void setPresaleprice(double presaleprice) {
        this.presaleprice = presaleprice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.imvThumb);
        dest.writeString(this.productname);
        dest.writeDouble(this.productprice);
        dest.writeDouble(this.productratenum);
        dest.writeDouble(this.producttotalnum);
        dest.writeDouble(this.salepercent);
        dest.writeDouble(this.presaleprice);
    }
}

