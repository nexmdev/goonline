package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 15-06-2017.
 */
@IgnoreExtraProperties
public class Size {

    public String sizeNAME;
    public String sizeCOLOR_ID;

    public Size(){}

    public void setSizeNAME(String name){
        this.sizeNAME = name;
    }
    public void setSizeCOLOR_ID(String color_id){
        this.sizeCOLOR_ID = color_id;
    }
}
