package com.unipet7.mcommerce.models;

public class SliderItems {
    private String url;

    public SliderItems(String url) {
        this.url = url;
    }
    public SliderItems() {
        // Constructor không đối số
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
