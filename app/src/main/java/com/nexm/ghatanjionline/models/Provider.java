package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 07-06-2017.
 */
@IgnoreExtraProperties
public class Provider {

    public String providerID;
    public String providerNAME;
    public String providerMO_NO;
    public String providerEMAIL;
    public String providerADRESS;
    public String providerPHOTOURL  = "X";
    public int providerNO_OF_ITEMS = 1;
    public int providerNO_OF_ORDERS = 0;
    public int providerNO_OF_INQUIRES = 0;

    public Provider(){}

    public void setProviderID(String mproviderID){

        this.providerID = mproviderID;
    }
    public void setProviderNAME(String mproviderNAME){

        this.providerNAME = mproviderNAME;
    }
    public void setProviderMO_NO(String mproviderMONO){

        this.providerMO_NO = mproviderMONO;
    }
    public void setProviderEMAIL(String mproviderEMAIL){

        this.providerEMAIL = mproviderEMAIL;
    }
    public void setProviderADRESS(String mproviderADRESS){

        this.providerADRESS = mproviderADRESS;
    }
    public void setProviderPHOTOURL(String mproviderPHOTOURL){

        this.providerPHOTOURL = mproviderPHOTOURL;
    }
    public void setProviderNO_OF_ITEMS(int mproviderNOOFITEMS){

        this.providerNO_OF_ITEMS = mproviderNOOFITEMS;
    }
    public void setProviderNO_OF_ORDERS(int mproviderNOOFORDERS){

        this.providerNO_OF_ORDERS = mproviderNOOFORDERS;
    }
    public void setProviderNO_OF_INQUIRES(int mproviderNOOFINQUIRES){

        this.providerNO_OF_INQUIRES = mproviderNOOFINQUIRES;
    }
}
