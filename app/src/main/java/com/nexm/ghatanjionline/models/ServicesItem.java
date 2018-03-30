package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class ServicesItem {
    public String servicesItemID;
    public String servicesItemNAME;
    public String servicesItemPRICE;
    public String servicesItemDESCRIPTION;
    public String servicesItemEXPERIENCE;
    public String servicesItemQUALIFICATION;
    public String servicesItemPHOTO1;
    public String servicesItemPHOTO2;
    public String servicesItemPHOTO3;

    public ServicesItem(){}

    public void setServicesItemID(String id){
        this.servicesItemID = id;
    }
    public void setServicesItemNAME(String name){
        this.servicesItemNAME = name;
    }
    public void setServicesItemPRICE(String price){
        this.servicesItemPRICE = price;
    }
    public void setServicesItemDESCRIPTION(String description){
        this.servicesItemDESCRIPTION = description;
    }
    public void setServicesItemPHOTO1(String url1){
        this.servicesItemPHOTO1 = url1;
    }
    public void setServicesItemPHOTO2(String url2){
        this.servicesItemPHOTO2 = url2;
    }
    public void setServicesItemPHOTO3(String url3){
        this.servicesItemPHOTO3 = url3;
    }
    public void setServicesItemEXPERIENCE(String experience){
        this.servicesItemEXPERIENCE = experience;
    }
    public void setServicesItemQUALIFICATION(String qualification){
        this.servicesItemQUALIFICATION = qualification;
    }
}
