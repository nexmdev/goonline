package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 09-06-2017.
 */
@IgnoreExtraProperties
public class ListItem {

    public String listID ;
    public String category;
    public String subCategory;
    public String itemID;
    public String itemNAME;
    public String providerNAME;
    public String itemPHOTOURL;
    public String providerID;
    public String deliveryID ;
    public String itemPRICE;
    public int itemNO_OF_REVIEWS ;
    public int itemRATINGS;
    public long added;

    public ListItem(){}

    public void setListID(String id){
        this.listID = id;
    }
    public void setCategory(String id){
        this.category = id;
    }
    public void setSubCategory(String id){
        this.subCategory = id;
    }
    public void setItemID(String id){
        this.itemID = id;
    }
    public void setItemNAME(String id){
        this.itemNAME = id;
    }
    public void setProviderNAME(String id){
        this.providerNAME = id;
    }
    public void setItemPHOTOURL(String id){
        this.itemPHOTOURL = id;
    }
    public void setProviderID(String id){
        this.providerID = id;
    }
    public void setDeliveryID(String id){
        this.deliveryID = id;
    }
    public void setItemPRICE(String id){
        this.itemPRICE = id;
    }
    public void setItemNO_OF_REVIEWS(int id){
        this.itemNO_OF_REVIEWS = id;
    }
    public void setItemRATINGS(int id){
        this.itemRATINGS = id;
    }
    public void setAdded(long id){
        this.added = id;
    }


}
