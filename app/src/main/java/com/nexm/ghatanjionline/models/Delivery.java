package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 07-06-2017.
 */
@IgnoreExtraProperties
public class Delivery {

    public String deliveryID;
    public String deliveryRETURN_POLICY;
    public String deliveryTIME = "X";

    public int deliveryMIN_ORDER;
    public int deliveryDELIVERY_CHARGES;
    public Boolean deliveryCOD_AVAILABLE;
    public Boolean deliveryRETURN_AVAILABLE;

    public Delivery(){}

    public void setDeliveryID(String mdeliveryID){

        this.deliveryID = mdeliveryID;
    }
    public void setDeliveryTIME(String mdeliveryTIME){

        this.deliveryTIME = mdeliveryTIME;
    }
    public void setDeliveryRETURN_POLICY(String mdeliveryRETURNPOLICY){

        this.deliveryRETURN_POLICY = mdeliveryRETURNPOLICY;
    }
    public void setDeliveryMIN_ORDER(int mdeliveryMINORDER){

        this.deliveryMIN_ORDER = mdeliveryMINORDER;
    }
    public void setDeliveryDELIVERY_CHARGES(int mdeliveryDELIVERYCHARGES){

        this.deliveryDELIVERY_CHARGES = mdeliveryDELIVERYCHARGES;
    }
    public void setDeliveryCOD_AVAILABLE(Boolean mdeliveryCODAVAILABLE){

        this.deliveryCOD_AVAILABLE = mdeliveryCODAVAILABLE;
    }
    public void setDeliveryRETURN_AVAILABLE(Boolean mdeliveryRETURNAVAILABLE){

        this.deliveryRETURN_AVAILABLE = mdeliveryRETURNAVAILABLE;
    }

}
