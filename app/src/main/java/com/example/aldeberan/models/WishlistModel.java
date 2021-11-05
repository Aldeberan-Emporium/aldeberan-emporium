package com.example.aldeberan.models;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Product;
import com.example.aldeberan.structures.Wishlist;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
Ong Shuoh Chwen 1171102212

Ong is reponsible for this. 
Ong setup the database using heroku and linked it to android studio.

For debugging every members are involved.
*/

public class WishlistModel extends DatabaseModel{

    //Add to wishlist
    public void addToWishlist(String userID, int prodID){
        RequestParams params = new RequestParams();
        params.put("action", "addToWishlist");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        params.put("product_id", prodID);
        this.postData(params);
    }

    //Remove from wishlist
    public void removeFromWishlist(int wishID){
        RequestParams params = new RequestParams();
        params.put("action", "deleteFromWishlist");
        params.put("wishlist_id", wishID);
        this.postData(params);
    }

    //Callback function for readWishlistByUser response
    public interface OnResponseCallback {
        public void onResponse(List<Wishlist> response) throws JSONException;
    }

    //Read wishlist by user ID
    public void readWishlistByUser(String userID, OnResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "readWishlistByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        this.getData(params, (success, response) -> {
            List<Wishlist> wishlistList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int wishID = Integer.parseInt(object.getString("wishlist_id"));
                    int prodID = Integer.parseInt(object.getString("product_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodAvail = Integer.parseInt(object.getString("product_availability"));
                    int prodStock = Integer.parseInt(object.getString("product_stock"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));

                    Wishlist wishlist = new Wishlist();
                    wishlist.setWishID(wishID);
                    wishlist.setProdID(prodID);
                    wishlist.setProdName(prodName);
                    wishlist.setProdSKU(prodSKU);
                    wishlist.setProdImg(prodImg);
                    wishlist.setProdAvail(prodAvail);
                    wishlist.setProdStock(prodStock);
                    wishlist.setProdPrice(prodPrice);

                    wishlistList.add(wishlist);

                    Log.i("wish", response);
                }
            }catch (Exception e){}
            callback.onResponse(wishlistList);
        });
    }
}
