package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 13-04-2017.
 */
@IgnoreExtraProperties
public class subCategoryData {

    public String subCategoryName;
    public String subCategoryOwner;
    public int subCategoryModel;
    public int subCategoryRate;
    public String subCategoryUrl;

    public int subCategoryRating;
    public int subCategoryNoOfUsers;

    public String subCategoryRegNo;

    public subCategoryData(){}

    public subCategoryData(String name,String owner,int rate,String url,int model
                           ,int ratings,int noOfUsers,String regNo){

        this.subCategoryName = name;
        this.subCategoryOwner = owner;
        this.subCategoryModel = model;
        this.subCategoryRate = rate;
        this.subCategoryUrl = url;
        this.subCategoryRating = ratings;
        this.subCategoryNoOfUsers = noOfUsers;
        this.subCategoryRegNo = regNo;

    }
    public String getSubCategoryName(){
        return subCategoryName;
    }
    public String getSubCategoryOwner(){
        return subCategoryOwner;
    }

    public int getSubCategoryRate(){
        return subCategoryRate;
    }
    public String getSubCategoryUrl(){
        return subCategoryUrl;
    }
    public int getSubCategoryModel() {

        return subCategoryModel;
    }
    public String getSubCategoryRegNo(){
        return subCategoryRegNo;
    }
    public int getSubCategoryRating(){
        return subCategoryRating;
    }
    public int getSubCategoryNoOfUsers(){
        return subCategoryNoOfUsers;
    }
}
