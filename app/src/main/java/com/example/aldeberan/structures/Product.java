package com.example.aldeberan.structures;

import org.apache.commons.text.StringEscapeUtils;

/*
Ong Shuoh Chwen 1171102212

Ong is responsible for this. 
He setup the database using heroku and linked it to android studio.

For debugging every members are involved
*/

public class Product {
    private int prodID;
    private String prodName;
    private String prodSKU;
    private String prodImg;
    private int prodAvail;
    private int prodStock;
    private double prodPrice;
    private int prodSold;
    private boolean isTopSeller;
    private int wishID;

    public Product(int prodID, String prodName, String prodSKU, int prodAvail, int prodStock, double prodPrice, String prodImg){
        this.prodID = prodID;
        this.prodName = StringEscapeUtils.unescapeHtml4(prodName);
        this.prodSKU = StringEscapeUtils.unescapeHtml4(prodSKU);
        this.prodAvail = prodAvail;
        this.prodStock = prodStock;
        this.prodPrice = prodPrice;
        this.prodImg = StringEscapeUtils.unescapeHtml4(prodImg);
    }

    public Product() {

    }

    public int getProdID() {
        return prodID;
    }
    public void setProdID(int prodID) {
        this.prodID = prodID;
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

    public boolean getProdAvail() {
        if (this.prodAvail == 0){return false;}
        else{return true;}
    }
    public void setProdAvail(int prodAvail) {
        this.prodAvail = prodAvail;
    }

    public int getProdStock() {
        return prodStock;
    }
    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    public double getProdPrice() {
        return prodPrice;
    }
    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProdSold() { return prodSold; }
    public void setProdSold(int prodSold) { this.prodSold = prodSold; }

    public boolean getIsTopSeller() { return isTopSeller; }
    public void setIsTopSeller(boolean topSeller) { isTopSeller = topSeller; }

    public int getWishID() {
        return wishID;
    }
    public void setWishID(int wishID) {
        this.wishID = wishID;
    }
}
