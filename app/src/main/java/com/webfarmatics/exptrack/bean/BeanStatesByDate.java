package com.webfarmatics.exptrack.bean;

import java.io.Serializable;

public class BeanStatesByDate implements Serializable {

    private String id;
    private String type;
    private String moneySpend;
    private String priority;
    private String date;

    public BeanStatesByDate(String id, String type, String moneySpend, String priority, String date) {
        this.id = id;
        this.type = type;
        this.moneySpend = moneySpend;
        this.priority = priority;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoneySpend() {
        return moneySpend;
    }

    public void setMoneySpend(String moneySpend) {
        this.moneySpend = moneySpend;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
