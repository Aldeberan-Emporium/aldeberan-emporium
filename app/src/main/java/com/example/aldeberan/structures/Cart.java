package com.example.aldeberan.structures;

import org.apache.commons.text.StringEscapeUtils;

public class Cart {

    //Quote
    private int quoteID;
    private double total;
    private int quoteStatus; //0: cart haven't checkout yet & 1: cart checkout ald

    //Quote Item
    private int quoteItemID;
    private String prodName;
    private String prodSKU;
    private int prodQuantity;
    private double prodPrice;
    private String prodImg;

    public Cart(int quoteID, String userID, double total, int quoteStatus, int quoteItemID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg){
        this.quoteID = quoteID;
        User u = new User(userID);
        this.total = total;
        this.quoteStatus = quoteStatus;
        this.quoteItemID = quoteItemID;
        this.prodName = StringEscapeUtils.unescapeHtml4(prodName);
        this.prodSKU = StringEscapeUtils.unescapeHtml4(prodSKU);
        this.prodQuantity = prodQuantity;
        this.prodPrice = prodPrice;
        this.prodImg = StringEscapeUtils.unescapeHtml4(prodImg);
    }

    public Cart() {

    }

    public int getQuoteID() {
        return quoteID;
    }
    public void setQuoteID(int quoteID) {
        this.quoteID = quoteID;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuoteStatus() {
        return quoteStatus;
    }
    public void setQuoteStatus(int quoteStatus) {
        this.quoteStatus = quoteStatus;
    }

    public int getQuoteItemID() {
        return quoteItemID;
    }
    public void setQuoteItemID(int quoteItemID) {
        this.quoteItemID = quoteItemID;
    }

    public String getProdName() {
        return prodName;
    }
    public void setProdName(String prodName) {
        this.prodName = StringEscapeUtils.unescapeHtml4(prodName);
    }

    public String getProdSKU() {
        return prodSKU;
    }
    public void setProdSKU(String prodSKU) {
        this.prodSKU = StringEscapeUtils.unescapeHtml4(prodSKU);
    }

    public String getProdImg() {
        return prodImg;
    }
    public void setProdImg(String prodImg) {
        this.prodImg = StringEscapeUtils.unescapeHtml4(prodImg);
    }

    public int getProdQuantity() {
        return prodQuantity;
    }
    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public double getProdPrice() {
        return prodPrice;
    }
    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }
}
