package com.unipet7.mcommerce.models;

public class Blogs {
    String title;
    String pic;
    String pubDate;
    String description;

    public Blogs(String title, String pic, String pubDate, String description) {
        this.title = title;
        this.pic = pic;
        this.pubDate = pubDate;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }}