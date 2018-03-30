package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class TransportItem {

    public String transportItemID;
    public String transportItemNAME;
    public String transportItemPRICE;
    public String transportItemMODEL;
    public String transportItemREG_NO;
    public String transportItemDRIVER_NAME;
    public String transportItemDRIVER_NO;
    public String transportItemPHOTO1;
    public String transportItemPHOTO2;
    public String transportItemPHOTO3;

    public TransportItem(){}

    public void setTransportItemID(String id){
        this.transportItemID = id;
    }
    public void setTransportItemNAME(String name){
        this.transportItemNAME = name;
    }
    public void setTransportItemPRICE(String price){
        this.transportItemPRICE = price;
    }
    public void setTransportItemMODEL(String description){
        this.transportItemMODEL = description;
    }
    public void setTransportItemPHOTO1(String url1){
        this.transportItemPHOTO1 = url1;
    }
    public void setTransportItemPHOTO2(String url2){
        this.transportItemPHOTO2 = url2;
    }
    public void setTransportItemPHOTO3(String url3){
        this.transportItemPHOTO3 = url3;
    }
    public void setTransportItemREG_NO(String regno){
        this.transportItemREG_NO = regno;
    }
    public void setTransportItemDRIVER_NAME(String drivername){
        this.transportItemDRIVER_NAME = drivername;
    }
    public void setTransportItemDRIVER_NO(String mono){
        this.transportItemDRIVER_NO = mono;
    }
}
