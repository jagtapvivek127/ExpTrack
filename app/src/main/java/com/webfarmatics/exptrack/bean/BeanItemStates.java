package com.webfarmatics.exptrack.bean;

public class BeanItemStates {

    private String id;
    private String type;
    private String noOfTrans;
    private String moneySpend;
    private String priority;

    public BeanItemStates(String id, String type, String noOfTrans, String moneySpend, String priority) {
        this.id = id;
        this.type = type;
        this.noOfTrans = noOfTrans;
        this.moneySpend = moneySpend;
        this.priority = priority;
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

    public String getNoOfTrans() {
        return noOfTrans;
    }

    public void setNoOfTrans(String noOfTrans) {
        this.noOfTrans = noOfTrans;
    }
}
