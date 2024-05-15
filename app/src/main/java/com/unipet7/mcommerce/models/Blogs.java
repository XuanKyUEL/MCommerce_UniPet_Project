package com.unipet7.mcommerce.models;

import java.io.Serializable;

public class Blogs implements Serializable {
    int blogid;
    String title;
    int pic;
    String pubDate;
    String description;
    private int viewType;

    public Blogs(String title, int pic, String pubDate, String description, int viewType) {
        this.title = title;
        this.pic = pic;
        this.pubDate = pubDate;
        this.description = description;
        this.viewType = viewType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
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
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}

