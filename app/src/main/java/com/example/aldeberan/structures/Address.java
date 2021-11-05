package com.example.aldeberan.structures;

import com.example.aldeberan.structures.User;

import org.apache.commons.text.StringEscapeUtils;

/*
Ong Shuoh Chwen 1171102212

Ong is reponsible for this. 
He setup the database using heroku and linked it to android studio.

For debugging every members are involved
*/

public class Address{

    private int addID;
    private String addRecipient;
    private String addContact;
    private String addLine1;
    private String addLine2;
    private String addCode;
    private String addCity;
    private String addState;
    private String addCountry;
    private int isDefault;

    public Address(int addID, String userID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry, int isDefault){
        this.addID = addID;
        User u = new User(userID);
        this.addRecipient = StringEscapeUtils.unescapeHtml4(addRecipient);
        this.addContact = addContact;
        this.addLine1 = StringEscapeUtils.unescapeHtml4(addLine1);
        this.addLine2 = StringEscapeUtils.unescapeHtml4(addLine2);
        this.addCode = addCode;
        this.addCity = StringEscapeUtils.unescapeHtml4(addCity);
        this.addState = StringEscapeUtils.unescapeHtml4(addState);
        this.addCountry = StringEscapeUtils.unescapeHtml4(addCountry);
        this.isDefault = isDefault;
    }

    public Address() {

    }

    public int getAddID() {
        return addID;
    }
    public void setAddID(int addID) {
        this.addID = addID;
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

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
