package com.unipet7.mcommerce.models;

public class HistoryOrders {
    String order_date;
    String order_code;
    String order_status;
    int imvThumb;
    String order_productname;
    String product_info;
    double product_count;
    double product_price;
    double order_totalprice;

    public HistoryOrders(String order_date, String order_code, String order_status, int imvThumb, String order_productname, String product_info, double product_count, double product_price, double order_totalprice) {
        this.order_date = order_date;
        this.order_code = order_code;
        this.order_status = order_status;
        this.imvThumb = imvThumb;
        this.order_productname = order_productname;
        this.product_info = product_info;
        this.product_count = product_count;
        this.product_price = product_price;
        this.order_totalprice = order_totalprice;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getImvThumb() {
        return imvThumb;
    }

    public void setImvThumb(int imvThumb) {
        this.imvThumb = imvThumb;
    }

    public String getOrder_productname() {
        return order_productname;
    }

    public void setOrder_productname(String order_productname) {
        this.order_productname = order_productname;
    }

    public String getProduct_info() {
        return product_info;
    }

    public void setProduct_info(String product_info) {
        this.product_info = product_info;
    }

    public double getProduct_count() {
        return product_count;
    }

    public void setProduct_count(double product_count) {
        this.product_count = product_count;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getOrder_totalprice() {
        return order_totalprice;
    }

    public void setOrder_totalprice(double order_totalprice) {
        this.order_totalprice = order_totalprice;
    }
}
