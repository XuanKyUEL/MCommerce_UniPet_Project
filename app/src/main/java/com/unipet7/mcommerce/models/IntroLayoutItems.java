package com.unipet7.mcommerce.models;

public class IntroLayoutItems {

    String title;
    String description;

    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public IntroLayoutItems(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
