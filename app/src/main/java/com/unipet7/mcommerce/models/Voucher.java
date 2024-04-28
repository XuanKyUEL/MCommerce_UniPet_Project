package com.unipet7.mcommerce.models;

public class Voucher {
    String voucher_code, voucher_descript;

    Double voucher_id, voucher_limit_use, voucher_max_discount, voucher_minium_value, voucher_numb;

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public String getVoucher_descript() {
        return voucher_descript;
    }

    public void setVoucher_descript(String voucher_descript) {
        this.voucher_descript = voucher_descript;
    }

    public Double getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(Double voucher_id) {
        this.voucher_id = voucher_id;
    }

    public Double getVoucher_limit_use() {
        return voucher_limit_use;
    }

    public void setVoucher_limit_use(Double voucher_limit_use) {
        this.voucher_limit_use = voucher_limit_use;
    }

    public Double getVoucher_max_discount() {
        return voucher_max_discount;
    }

    public void setVoucher_max_discount(Double voucher_max_discount) {
        this.voucher_max_discount = voucher_max_discount;
    }

    public Double getVoucher_minium_value() {
        return voucher_minium_value;
    }

    public void setVoucher_minium_value(Double voucher_minium_value) {
        this.voucher_minium_value = voucher_minium_value;
    }

    public Double getVoucher_numb() {
        return voucher_numb;
    }

    public void setVoucher_numb(Double voucher_numb) {
        this.voucher_numb = voucher_numb;
    }

    public Voucher(String voucher_code, String voucher_descript, Double voucher_id, Double voucher_limit_use, Double voucher_max_discount, Double voucher_minium_value, Double voucher_numb) {
        this.voucher_code = voucher_code;
        this.voucher_descript = voucher_descript;
        this.voucher_id = voucher_id;
        this.voucher_limit_use = voucher_limit_use;
        this.voucher_max_discount = voucher_max_discount;
        this.voucher_minium_value = voucher_minium_value;
        this.voucher_numb = voucher_numb;
    }
    public Voucher() {
    }
}
