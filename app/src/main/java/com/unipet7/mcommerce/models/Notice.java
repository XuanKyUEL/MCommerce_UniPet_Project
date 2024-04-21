package com.unipet7.mcommerce.models;

public class Notice {
    String NotiTtle, NotiDescription, NotiTime;

    public String getNotiTtle() {
        return NotiTtle;
    }

    public void setNotiTtle(String notiTtle) {
        NotiTtle = notiTtle;
    }

    public String getNotiDescription() {
        return NotiDescription;
    }

    public void setNotiDescription(String notiDescription) {
        NotiDescription = notiDescription;
    }

    public String getNotiTime() {
        return NotiTime;
    }

    public void setNotiTime(String notiTime) {
        NotiTime = notiTime;
    }

    public Notice(String notiTtle, String notiDescription, String notiTime) {
        NotiTtle = notiTtle;
        NotiDescription = notiDescription;
        NotiTime = notiTime;
    }
}
