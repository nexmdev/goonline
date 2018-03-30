package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 15-06-2017.
 */
@IgnoreExtraProperties
public class Color {

    public String colorNAME;
    public String colorPHOTO;
    public int colorQUANTITY;

    public Color(){}

    public void setColorNAME(String name){
        this.colorNAME = name;
    }
    public void setColorPHOTO(String photo){
        this.colorPHOTO = photo;
    }
    public void setColorQUANTITY(int quantity){
        this.colorQUANTITY = quantity;
    }
}
