package com.example.aldeberan.structures;

/*
Ong Shuoh Chwen 1171102212

Ong is responsible for this. 
He setup the database using heroku and linked it to android studio.

For debugging every members are involved
*/

public class User {

    private String userID;

    public User(String userID){
        this.userID = userID;
    }


    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
