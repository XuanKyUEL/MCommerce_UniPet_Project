package com.unipet7.mcommerce.models;

public class Voucher {
    String txtVoucherCode, txtVoucherDecription;
    Double txtVoucherNumb;

    public String getTxtVoucherCode() {
        return txtVoucherCode;
    }

    public void setTxtVoucherCode(String txtVoucherCode) {
        this.txtVoucherCode = txtVoucherCode;
    }

    public String getTxtVoucherDecription() {
        return txtVoucherDecription;
    }

    public void setTxtVoucherDecription(String txtVoucherDecription) {
        this.txtVoucherDecription = txtVoucherDecription;
    }

    public Double getTxtVoucherNumb() {
        return txtVoucherNumb;
    }

    public void setTxtVoucherNumb(Double txtVoucherNumb) {
        this.txtVoucherNumb = txtVoucherNumb;
    }

    public Voucher(String txtVoucherCode, String txtVoucherDecription, Double txtVoucherNumb) {
        this.txtVoucherCode = txtVoucherCode;
        this.txtVoucherDecription = txtVoucherDecription;
        this.txtVoucherNumb = txtVoucherNumb;
    }
}
