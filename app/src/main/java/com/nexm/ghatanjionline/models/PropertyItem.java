package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class PropertyItem {

    public String rentalsItemID;
    public String rentalsItemNAME;
    public String rentalsItemPRICE;
    public String rentalsItemDESCRIPTION;
    public String rentalsItemADDRESS;
    public String rentalsItemAGE;
    public String rentalsItemPHOTO1;
    public String rentalsItemPHOTO2;
    public String rentalsItemPHOTO3;

    public PropertyItem(){}

    public void setRentalsItemID(String id){
        this.rentalsItemID = id;
    }
    public void setRentalsItemNAME(String name){
        this.rentalsItemNAME = name;
    }
    public void setRentalsItemPRICE(String price){
        this.rentalsItemPRICE = price;
    }
    public void setRentalsItemDESCRIPTION(String description){
        this.rentalsItemDESCRIPTION = description;
    }
    public void setRentalsItemPHOTO1(String url1){
        this.rentalsItemPHOTO1 = url1;
    }
    public void setRentalsItemPHOTO2(String url2){
        this.rentalsItemPHOTO2 = url2;
    }
    public void setRentalsItemPHOTO3(String url3){
        this.rentalsItemPHOTO3 = url3;
    }
    public void setRentalsItemADDRESS(String address){
        this.rentalsItemADDRESS = address;
    }
    public void setRentalsItemAGE(String age){
        this.rentalsItemAGE = age;
    }
}
