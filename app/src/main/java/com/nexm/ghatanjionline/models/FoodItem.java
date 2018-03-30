package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class FoodItem {

    public String foodItemID;
    public String foodItemNAME;
    public String foodItemPRICE;
    public String foodItemDESCRIPTION;
    public String foodtItemPHOTO1;
    public String foodtItemPHOTO2 ;
    public String foodtItemPHOTO3;

    public FoodItem(){}

    public void setFoodItemID(String id){
        this.foodItemID = id;
    }
    public void setFoodItemNAME(String name){
        this.foodItemNAME = name;
    }
    public void setFoodItemPRICE(String price){
        this.foodItemPRICE = price;
    }
    public void setFoodItemDESCRIPTION(String description){
        this.foodItemDESCRIPTION = description;
    }
    public void setFoodtItemPHOTO1(String url1){
        this.foodtItemPHOTO1 = url1;
    }
    public void setFoodtItemPHOTO2(String url2){
        this.foodtItemPHOTO2 = url2;
    }
    public void setFoodtItemPHOTO3(String url3){
        this.foodtItemPHOTO3 = url3;
    }
}
