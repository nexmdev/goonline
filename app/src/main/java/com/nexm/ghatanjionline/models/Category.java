package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 16-06-2017.
 */
@IgnoreExtraProperties
public class Category {

    private String NAME;
    private String DESCRIPTION;
    private String IMAGE_URL;

    public Category(){}

    public void setNAME(String name){
        this.NAME = name;
    }
    public void setDESCRIPTION(String id){
        this.DESCRIPTION = id;
    }
    public void setIMAGE_URL(String photo){
        this.IMAGE_URL = photo;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public String getNAME() {
        return NAME;
    }
}
