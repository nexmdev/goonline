package com.nexm.ghatanjionline.models;

public class Seller {
    private String account;
    private String displayName;
    private String address;
    private String email;
    private String idUrl;
    private String moNo;
    private String moNoSecond;
    private String name;
    private String photoUrl;
    private String sellerId;
    private long joinDate;
    private long planExpiry;
    private int products;
    private int productsLimit;
    private int planAmount;

    public Seller(){}

    public String getAccount() {
        return account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAddress() {
        return address;
    }

    public int getPlanAmount() {
        return planAmount;
    }

    public int getProducts() {
        return products;
    }

    public int getProductsLimit() {
        return productsLimit;
    }

    public long getJoinDate() {
        return joinDate;
    }

    public long getPlanExpiry() {
        return planExpiry;
    }

    public String getEmail() {
        return email;
    }

    public String getIdUrl() {
        return idUrl;
    }

    public String getMoNo() {
        return moNo;
    }

    public String getMoNoSecond() {
        return moNoSecond;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdUrl(String idUrl) {
        this.idUrl = idUrl;
    }

    public void setJoinDate(long joinDate) {
        this.joinDate = joinDate;
    }

    public void setMoNo(String moNo) {
        this.moNo = moNo;
    }

    public void setMoNoSecond(String moNoSecond) {
        this.moNoSecond = moNoSecond;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setPlanAmount(int planAmount) {
        this.planAmount = planAmount;
    }

    public void setPlanExpiry(long planExpiry) {
        this.planExpiry = planExpiry;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public void setProductsLimit(int productsLimit) {
        this.productsLimit = productsLimit;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

}
