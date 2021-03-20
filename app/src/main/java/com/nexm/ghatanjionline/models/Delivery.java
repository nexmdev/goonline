package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 07-06-2017.
 */
@IgnoreExtraProperties
public class Delivery {

    private String deliveryID;
    private String deliveryRETURN_POLICY;
    private String deliveryTIME = "X";

    private int deliveryMIN_ORDER;
    private int deliveryDELIVERY_CHARGES;
    private Boolean deliveryCOD_AVAILABLE;
    private Boolean deliveryRETURN_AVAILABLE;

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

    public int getDeliveryDELIVERY_CHARGES() {
        return deliveryDELIVERY_CHARGES;
    }

    public Boolean getDeliveryCOD_AVAILABLE() {
        return deliveryCOD_AVAILABLE;
    }

    public Boolean getDeliveryRETURN_AVAILABLE() {
        return deliveryRETURN_AVAILABLE;
    }

    public int getDeliveryMIN_ORDER() {
        return deliveryMIN_ORDER;
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public String getDeliveryRETURN_POLICY() {
        return deliveryRETURN_POLICY;
    }

    public String getDeliveryTIME() {
        return deliveryTIME;
    }

}
