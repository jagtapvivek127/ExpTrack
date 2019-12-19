package com.webfarmatics.exptrack.bean;

import java.io.Serializable;

public class BeanTransactionsHelper implements Serializable {

    private String item;
    private String amount;
    private String desc;
    private String date;
    private String state;
    private String paymentBy;

    public BeanTransactionsHelper(String item, String amount, String desc, String date, String state, String paymentBy) {
        this.item = item;
        this.amount = amount;
        this.desc = desc;
        this.date = date;
        this.state = state;
        this.paymentBy = paymentBy;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPaymentBy() {
        return paymentBy;
    }

    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }
}
