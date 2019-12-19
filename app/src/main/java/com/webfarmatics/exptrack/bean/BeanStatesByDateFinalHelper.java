package com.webfarmatics.exptrack.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class BeanStatesByDateFinalHelper implements Serializable {

    private String date;
    private ArrayList<BeanStatesByDate> statesByDatesList;
    private String state;

    public BeanStatesByDateFinalHelper(String date, ArrayList<BeanStatesByDate> statesByDatesList, String state) {
        this.date = date;
        this.statesByDatesList = statesByDatesList;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
