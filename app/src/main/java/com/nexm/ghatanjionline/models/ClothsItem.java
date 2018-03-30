package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class ClothsItem {

    public String clothsItemID;

    public String clothsItemNAME;
    public String clothsItemPRICE;
    public String clothsItemDESCRIPTION;
    public String clothsItemSIZEID;
    public String clothsItemSUB_CATEGORY;

    public String clothsItemPHOTO1;
    public String clothsItemPHOTO2;
    public String clothsItemPHOTO3;

    public ClothsItem(){}

    public void setClothsItemID(String id){
        this.clothsItemID = id;
    }
    public void setClothsItemNAME(String name){
        this.clothsItemNAME = name;
    }
    public void setClothsItemPRICE(String price){
        this.clothsItemPRICE = price;
    }
    public void setClothsItemDESCRIPTION(String description){
        this.clothsItemDESCRIPTION = description;
    }
    public void setClothsItemPHOTO1(String url1){
        this.clothsItemPHOTO1 = url1;
    }
    public void setClothsItemPHOTO2(String url2){
        this.clothsItemPHOTO2 = url2;
    }
    public void setClothsItemPHOTO3(String url3){
        this.clothsItemPHOTO3 = url3;
    }
    public void setClothsItemSIZEID(String sizeid){
        this.clothsItemSIZEID = sizeid;
    }
    public void setClothsItemSUB_CATEGORY(String sub_category){
        this.clothsItemSUB_CATEGORY = sub_category;
    }

}
