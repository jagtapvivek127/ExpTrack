package com.webfarmatics.exptrack.bean;

public class BeanThought {

    private String auther;
    private String thought;

    public BeanThought(String auther, String thought) {
        this.auther = auther;
        this.thought = thought;
    }


    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }
}
