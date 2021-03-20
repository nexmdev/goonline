package com.nexm.ghatanjionline.models;

public class ProductDelivery {

    private String returnPolicy;
    private String time;
    private int charges;
    private int free_delivery;
    private int minOrder;
    private boolean returnAvailable;

    public ProductDelivery(){}

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public String getTime() {
        return time;
    }

    public int getCharges() {
        return charges;
    }

    public boolean isReturnAvailable() {
        return returnAvailable;
    }

    public int getFree_delivery() {
        return free_delivery;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFree_delivery(int free_delivery) {
        this.free_delivery = free_delivery;
    }

    public void setMinOrder(int minOrder) {
        this.minOrder = minOrder;
    }

    public void setReturnAvailable(boolean returnAvailable) {
        this.returnAvailable = returnAvailable;
    }

}
