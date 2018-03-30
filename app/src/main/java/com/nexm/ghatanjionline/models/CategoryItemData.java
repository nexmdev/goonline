package com.nexm.ghatanjionline.models;

/**
 * Created by user on 13-04-2017.
 */

public class CategoryItemData {

    private String name;
    private int imageID;

    public CategoryItemData(String mname , int mimageID){

        this.name = mname;
        this.imageID = mimageID;
    }
    public String getName(){
        return name;
    }
    public int getImageID(){
        return imageID;
    }
}
