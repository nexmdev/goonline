package com.nexm.ghatanjionline.models;

public class CartItem {
    private String productName;
    private String sellertName;
    private String sellerId;
    private String productId;
    private String price;
    private int quantity;
    private int minOrder;
    private int deliveryCharges;
    private int freeDelivery;
    private String productThumb;

    public CartItem(){}

    public int getFreeDelivery() {
        return freeDelivery;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public int getDeliveryCharges() {
        return deliveryCharges;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getSellertName() {
        return sellertName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setFreeDelivery(int freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSellertName(String sellertName) {
        this.sellertName = sellertName;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMinOrder(int minOrder) {
        this.minOrder = minOrder;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

}
