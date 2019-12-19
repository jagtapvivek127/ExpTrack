package com.webfarmatics.exptrack.bean;

import java.io.Serializable;

public class BeanTransactions implements Serializable {

    private String item;
    private String amount;
    private String desc;
    private String date;
    private String paymentBy;


    public BeanTransactions(String item, String amount, String desc, String date, String paymentBy) {
        this.item = item;
        this.amount = amount;
        this.desc = desc;
        this.date = date;
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

    public String getPaymentBy() {
        return paymentBy;
    }

    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }
}
