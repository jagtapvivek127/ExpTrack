package com.webfarmatics.exptrack.bean;

import java.io.Serializable;

public class BeanLoan implements Serializable {

    //columns   id,from,amount,date,desc,type
    private String id;
    private String from;
    private String amount;
    private String date;
    private String returnDate;
    private String desc;
    private String type;


    public BeanLoan(String id, String from, String amount, String date, String returnDate, String desc, String type) {
        this.id = id;
        this.from = from;
        this.amount = amount;
        this.date = date;
        this.returnDate = returnDate;
        this.desc = desc;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
