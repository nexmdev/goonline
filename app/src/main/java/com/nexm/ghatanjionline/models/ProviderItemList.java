package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 27-06-2017.
 */
@IgnoreExtraProperties
public class ProviderItemList {

    public String itemID;
    public String categoryID;
    public String subCategoryID;

    public ProviderItemList(){}

    public void setCategoryID(String id){

        this.categoryID = id;
    }
    public void setItemID(String itemID){

        this.itemID = itemID;
    }
    public void setSubCategoryID(String subCategoryID){

        this.subCategoryID = subCategoryID;
    }
}
