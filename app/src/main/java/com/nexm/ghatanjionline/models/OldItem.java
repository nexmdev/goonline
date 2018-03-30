package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class OldItem {

    public String oldItemID;

    public String oldItemNAME;
    public String oldItemPRICE;
    public String oldItemDESCRIPTION;
    public Boolean oldItemBILL_AVAILABLE;
    public String oldItemAGE;
    public String oldItemPHOTO1;
    public String oldItemPHOTO2;
    public String oldItemPHOTO3;

    public OldItem(){}

    public void setOldItemID(String id){
        this.oldItemID = id;
    }
    public void setOldItemNAME(String name){
        this.oldItemNAME = name;
    }
    public void setOldItemPRICE(String price){
        this.oldItemPRICE = price;
    }
    public void setOldItemDESCRIPTION(String description){
        this.oldItemDESCRIPTION = description;
    }
    public void setOldItemPHOTO1(String url1){
        this.oldItemPHOTO1 = url1;
    }
    public void setOldItemPHOTO2(String url2){
        this.oldItemPHOTO2 = url2;
    }
    public void setOldItemPHOTO3(String url3){
        this.oldItemPHOTO3 = url3;
    }
    public void setOldItemBILL_AVAILABLE(Boolean bill_available){
        this.oldItemBILL_AVAILABLE = bill_available;
    }
    public void setOldItemAGE(String age){
        this.oldItemAGE = age;
    }

}
