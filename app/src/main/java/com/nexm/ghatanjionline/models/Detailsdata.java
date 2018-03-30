package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 23-04-2017.
 */
@IgnoreExtraProperties
public class Detailsdata {

    public String productPhotoUrl1;
    public String productPhotoUrl2;
    public String productPhotoUrl3;

    public String ownerPhotoUrl;
    public String driverPhotoUrl;

    public int model;
    public String regNo;

    public String ownerName;
    public String driverName;

    public String ownerNo;
    public String driverNo;

    public String ownerAddress;

    public int rate;
    public int yavRate;


    public int rating;
    public int noOfUsers;

    public Boolean emergency;
    public String emergencyDetails;

    public Detailsdata(){

    }

    /***** setters **************/

    public void setProductPhotoUrl1(String url){
        productPhotoUrl1 = url;
    }
    public void setProductPhotoUrl2(String url){
        productPhotoUrl2 = url;
    }
    public void setProductPhotoUrl3(String url){
        productPhotoUrl3 = url;
    }
    public void setOwnerPhotoUrl(String url){
        ownerPhotoUrl = url;
    }
    public void setDriverPhotoUrl(String url){
        driverPhotoUrl = url;
    }
    public void setOwnerName(String name){
        ownerName = name;
    }
    public void setDriverName(String name){
        driverName = name;
    }
    public void setOwnerNo(String no){
        ownerNo = no;
    }
    public void setDriverNo(String no){
        driverNo = no;
    }
    public void setRate(int no){
        rate = no;
    }
    public void setYavRate(int no){
        yavRate = no;
    }
    public void setModel(int no){
        model = no;
    }
    public void setRegNo(String no){
        regNo = no;
    }
    public void setRating(int no){
        rating = no;
    }
    public void setNoOfUsers(int no){
        noOfUsers = no;
    }
    public void setOwnerAddress(String address){
        ownerAddress = address;
    }
    public void setEmergency(Boolean yesno){
        emergency = yesno;
    }
    public void setEmergencyDetails(String details){
        emergencyDetails = details;
    }

    /******** getters ***************/


    public String getProductPhotoUrl1(){
       return productPhotoUrl1;
    }
    public String getProductPhotoUrl2(){
        return productPhotoUrl2 ;
    }
    public String getProductPhotoUrl3(){
        return productPhotoUrl3;
    }
    public String getOwnerPhotoUrl( ){
        return ownerPhotoUrl ;
    }
    public String getDriverPhotoUrl(){
        return driverPhotoUrl ;
    }
    public String getOwnerName(){
        return ownerName ;
    }
    public String getDriverName(){
        return driverName ;
    }
    public String getOwnerNo(){
        return ownerNo;
    }
    public String getDriverNo(){
        return driverNo;
    }
    public int getRate(){
        return rate ;
    }
    public int getYavRate(){
        return yavRate ;
    }
    public int getModel(){
       return model;
    }
    public String getRegNo(){
        return regNo ;
    }
    public int getRating(){
        return rating ;
    }
    public int getNoOfUsers(){
        return noOfUsers ;
    }
    public String getOwnerAddress(){
        return ownerAddress;
    }
    public Boolean getEmergency(){
        return emergency ;
    }
    public String getEmergencyDetails(){
        return emergencyDetails ;
    }



}
