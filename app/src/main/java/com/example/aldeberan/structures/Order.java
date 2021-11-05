package com.example.aldeberan.structures;

import org.apache.commons.text.StringEscapeUtils;

/*
Ong Shuoh Chwen 1171102212
Chong Wai Hou   1161104445

Ong and Chong reponsible for this. 
They discuss and setup the database togther using heroku and linked it to android studio.

For debugging every members are involved
*/

public class Order {
    //Order
    private int orderID;
    private String orderRef;
    private String orderDate;
    private double total;
    private String orderStatus;
    private int totalItems;

    //Order Item
    private int orderItemID;
    private String prodName;
    private String prodSKU;
    private int prodQuantity;
    private double prodPrice;
    private String prodImg;

    //Order Address
    private int orderAddressID;
    private String addRecipient;
    private String addContact;
    private String addLine1;
    private String addLine2;
    private String addCode;
    private String addCity;
    private String addState;
    private String addCountry;

    //Order Payment
    private int orderPaymentID;
    private String payType;
    private String payID;

    public Order(int orderID, String userID, String orderRef, String orderDate, double total, String orderStatus,
        int orderItemID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg,
        int orderAddressID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry,
        int orderPaymentID, String payType, String payID){
        this.orderID = orderID;
        User u = new User(userID);
        this.orderRef = orderRef;
        this.orderDate = orderDate;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderItemID = orderItemID;
        this.prodName = StringEscapeUtils.unescapeHtml4(prodName);
        this.prodSKU = StringEscapeUtils.unescapeHtml4(prodSKU);
        this.prodQuantity = prodQuantity;
        this.prodPrice = prodPrice;
        this.prodImg = prodImg;
        this.orderAddressID = orderAddressID;
        this.addRecipient = StringEscapeUtils.unescapeHtml4(addRecipient);
        this.addContact = addContact;
        this.addLine1 = StringEscapeUtils.unescapeHtml4(addLine1);
        this.addLine2 = StringEscapeUtils.unescapeHtml4(addLine2);
        this.addCode = addCode;
        this.addCity = StringEscapeUtils.unescapeHtml4(addCity);
        this.addState = StringEscapeUtils.unescapeHtml4(addState);
        this.addCountry = StringEscapeUtils.unescapeHtml4(addCountry);
        this.orderPaymentID = orderPaymentID;
        this.payType = payType;
        this.payID = payID;
    }

    public Order() {

    }

    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderRef() {
        return orderRef;
    }
    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderItemID() {
        return orderItemID;
    }
    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
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

    public int getOrderAddressID() {
        return orderAddressID;
    }
    public void setOrderAddressID(int orderAddressID) {
        this.orderAddressID = orderAddressID;
    }

    public String getAddRecipient() {
        return addRecipient;
    }
    public void setAddRecipient(String addRecipient) {
        this.addRecipient = StringEscapeUtils.unescapeHtml4(addRecipient);
    }

    public String getAddContact() {
        return addContact;
    }
    public void setAddContact(String addContact) {
        this.addContact = addContact;
    }

    public String getAddLine1() {
        return addLine1;
    }
    public void setAddLine1(String addLine1) {
        this.addLine1 = StringEscapeUtils.unescapeHtml4(addLine1);
    }

    public String getAddLine2() {
        return addLine2;
    }
    public void setAddLine2(String addLine2) {
        this.addLine2 = StringEscapeUtils.unescapeHtml4(addLine2);
    }

    public String getAddCode() {
        return addCode;
    }
    public void setAddCode(String addCode) {
        this.addCode = addCode;
    }

    public String getAddCity() {
        return addCity;
    }
    public void setAddCity(String addCity) {
        this.addCity = StringEscapeUtils.unescapeHtml4(addCity);
    }

    public String getAddState() {
        return addState;
    }
    public void setAddState(String addState) {
        this.addState = StringEscapeUtils.unescapeHtml4(addState);
    }

    public String getAddCountry() {
        return addCountry;
    }
    public void setAddCountry(String addCountry) {
        this.addCountry = StringEscapeUtils.unescapeHtml4(addCountry);
    }

    public int getOrderPaymentID() {
        return orderPaymentID;
    }
    public void setOrderPaymentID(int orderPaymentID) {
        this.orderPaymentID = orderPaymentID;
    }

    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayID() {
        return payID;
    }
    public void setPayID(String payID) {
        this.payID = payID;
    }

    public int getTotalItems() {return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
}
