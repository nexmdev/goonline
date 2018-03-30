package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 09-06-2017.
 */
@IgnoreExtraProperties
public class ExtraItemInfo {

    public String extra_item_infoID;
    public String itemYEAR;

    public String itemServiceArea;
    public Boolean emergencyAVAILABLE;
    public String emergencyPOLICY;

    public ExtraItemInfo(){}

    public void setItemYEAR(String mitemYEAR){
        this.itemYEAR = mitemYEAR;
    }

    public void setItemServiceArea(String mitemServiceArea){
        this.itemServiceArea = mitemServiceArea;
    }
    public void setEmergencyAVAILABLE(Boolean memergencyAVAILABLE){
        this.emergencyAVAILABLE = memergencyAVAILABLE;
    }
    public void setEmergencyPOLICY(String memergencyPOLICY){
        this.emergencyPOLICY = memergencyPOLICY;
    }
    public void setExtra_item_infoID(String mextra_item_infoID){
        this.extra_item_infoID = mextra_item_infoID;
    }

}
