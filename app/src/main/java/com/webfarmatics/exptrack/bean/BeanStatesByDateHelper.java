package com.webfarmatics.exptrack.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class BeanStatesByDateHelper implements Serializable {

    private String date;
    private ArrayList<BeanStatesByDate>statesByDatesList;

    public BeanStatesByDateHelper(String date, ArrayList<BeanStatesByDate> statesByDatesList) {
        this.date = date;
        this.statesByDatesList = statesByDatesList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<BeanStatesByDate> getStatesByDatesList() {
        return statesByDatesList;
    }

    public void setStatesByDatesList(ArrayList<BeanStatesByDate> statesByDatesList) {
        this.statesByDatesList = statesByDatesList;
    }
}
