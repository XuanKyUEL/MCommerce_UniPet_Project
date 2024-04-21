package com.unipet7.mcommerce.models;

public class ProductCart {
    int imvProductCart;
    String txtProductName;
    Double txtProductPrice, txtSumNumbPrice, txtNumberOrder;

    public int getImvProductCart() {
        return imvProductCart;
    }

    public void setImvProductCart(int imvProductCart) {
        this.imvProductCart = imvProductCart;
    }

    public String getTxtProductName() {
        return txtProductName;
    }

    public void setTxtProductName(String txtProductName) {
        this.txtProductName = txtProductName;
    }

    public Double getTxtProductPrice() {
        return txtProductPrice;
    }

    public void setTxtProductPrice(Double txtProductPrice) {
        this.txtProductPrice = txtProductPrice;
    }

    public Double getTxtSumNumbPrice() {
        return txtSumNumbPrice;
    }

    public void setTxtSumNumbPrice(Double txtSumNumbPrice) {
        this.txtSumNumbPrice = txtSumNumbPrice;
    }

    public Double getTxtNumberOrder() {
        return txtNumberOrder;
    }

    public void setTxtNumberOrder(Double txtNumberOrder) {
        this.txtNumberOrder = txtNumberOrder;
    }

    public ProductCart(int imvProductCart, String txtProductName, Double txtProductPrice, Double txtSumNumbPrice, Double txtNumberOrder) {
        this.imvProductCart = imvProductCart;
        this.txtProductName = txtProductName;
        this.txtProductPrice = txtProductPrice;
        this.txtSumNumbPrice = txtSumNumbPrice;
        this.txtNumberOrder = txtNumberOrder;
    }
}
