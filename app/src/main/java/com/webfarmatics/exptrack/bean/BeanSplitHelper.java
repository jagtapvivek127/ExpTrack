package com.webfarmatics.exptrack.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class BeanSplitHelper implements Serializable {

    private String title;
    private String amount;
    private String date;
    private String returnDate;
    private String comment;
    private String owesYou;
    private String participant;

    public BeanSplitHelper(String title, String amount, String date, String returnDate, String comment, String owesYou, String participant) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.returnDate = returnDate;
        this.comment = comment;
        this.owesYou = owesYou;
        this.participant = participant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOwesYou() {
        return owesYou;
    }

    public void setOwesYou(String owesYou) {
        this.owesYou = owesYou;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
