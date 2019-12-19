package com.webfarmatics.exptrack.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class BeanSplitHelperHelper implements Serializable {

    private String title;
    private String date;
    private ArrayList<BeanSplitHelper>splitHelpersList;

    public BeanSplitHelperHelper(String title, String date, ArrayList<BeanSplitHelper> splitHelpersList) {
        this.title = title;
        this.date = date;
        this.splitHelpersList = splitHelpersList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<BeanSplitHelper> getSplitHelpersList() {
        return splitHelpersList;
    }

    public void setSplitHelpersList(ArrayList<BeanSplitHelper> splitHelpersList) {
        this.splitHelpersList = splitHelpersList;
    }
}
