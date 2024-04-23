package com.unipet7.mcommerce.models;

public class BlogDetail {

    int imvThumb;
    String blogtitle;
    String blogdescription;
    public BlogDetail(int imvThumb, String blogtitle, String blogdescription) {
        this.imvThumb = imvThumb;
        this.blogtitle = blogtitle;
        this.blogdescription = blogdescription;
    }

    public int getImvThumb() {
        return imvThumb;
    }

    public void setImvThumb(int imvThumb) {
        this.imvThumb = imvThumb;
    }

    public String getBlogtitle() {
        return blogtitle;
    }

    public void setBlogtitle(String blogtitle) {
        this.blogtitle = blogtitle;
    }

    public String getBlogdescription() {
        return blogdescription;
    }

    public void setBlogdescription(String blogdescription) {
        this.blogdescription = blogdescription;
    }
}
