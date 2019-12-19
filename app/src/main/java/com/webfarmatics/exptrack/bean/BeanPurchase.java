package com.webfarmatics.exptrack.bean;

import java.io.Serializable;

public class BeanPurchase implements Serializable {

    private String item;
    private int amount;
    private String desc;
    private String date;
    private String subItem;
    private String paymentBy;


    public BeanPurchase(String item, int amount, String desc, String date, String subItem, String paymentBy) {
        this.item = item;
        this.amount = amount;
        this.desc = desc;
        this.date = date;
        this.subItem = subItem;
        this.paymentBy = paymentBy;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubItem() {
        return subItem;
    }

    public void setSubItem(String subItem) {
        this.subItem = subItem;
    }

    public String getPaymentBy() {
        return paymentBy;
    }

    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }
}
