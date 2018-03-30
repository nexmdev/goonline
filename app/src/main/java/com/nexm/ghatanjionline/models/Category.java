package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 16-06-2017.
 */
@IgnoreExtraProperties
public class Category {

    public String categoryNAME;
    public String categoryID;
    public String categoryPHOTO;

    public Category(){}

    public void setCategoryNAME(String name){
        this.categoryNAME = name;
    }
    public void setCategoryID(String id){
        this.categoryID = id;
    }
    public void setCategoryPHOTO(String photo){
        this.categoryPHOTO = photo;
    }
}
