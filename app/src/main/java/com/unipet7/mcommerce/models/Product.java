package com.unipet7.mcommerce.models;

public class Product {
    int imvThumb;
    String productname;
    double productprice;
    double productratenum;
    double producttotalnum;

    public Product(int imvThumb, String productname, double productprice, double productratenum, double producttotalnum) {
        this.imvThumb = imvThumb;
        this.productname = productname;
        this.productprice = productprice;
        this.productratenum = productratenum;
        this.producttotalnum = producttotalnum;
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

    public Double getProductprice() {
        return productprice;
    }

    public void setProductprice(Double productprice) {
        this.productprice = productprice;
    }

    public Double getProductratenum() {
        return productratenum;
    }

    public void setProductratenum(Double productratenum) {
        this.productratenum = productratenum;
    }

    public Double getProducttotalnum() {
        return producttotalnum;
    }

    public void setProducttotalnum(Double producttotalnum) {
        this.producttotalnum = producttotalnum;
    }
}
