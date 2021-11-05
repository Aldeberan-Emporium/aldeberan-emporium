package com.example.aldeberan.structures;

/*
Ong Shuoh Chwen 1171102212
Leong Kah Ming  1171100884

Ong and Leong are responsible for this. 
They discuss and setup the database togther using heroku and linked it to android studio.

For debugging every members are involved
*/

public class Wishlist {

    private int wishID;
    private int prodID;
    private String prodName;
    private String prodSKU;
    private String prodImg;
    private int prodAvail;
    private int prodStock;
    private double prodPrice;

    public Wishlist(){}

    public int getWishID() {
        return wishID;
    }

    public void setWishID(int wishID) {
        this.wishID = wishID;
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
        this.prodName = prodName;
    }

    public String getProdSKU() {
        return prodSKU;
    }

    public void setProdSKU(String prodSKU) {
        this.prodSKU = prodSKU;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    public int getProdAvail() {
        return prodAvail;
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
}
