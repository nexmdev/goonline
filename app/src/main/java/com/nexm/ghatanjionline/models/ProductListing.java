package com.nexm.ghatanjionline.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 09-06-2017.
 */
@IgnoreExtraProperties
public class ProductListing implements Parcelable {

    private String price ;
    private String department;
    private String deptCat;
    private String deptCatSubCat;
    private String productName;
    private String productThumb;
    private int qAvailable;
    private int qSold;
    private int ratings ;
    private String sellerID;
    private String sellerName;
    private int displayPriority ;
    private int noOfRaters;
    private long dateOfListing;
    private long validity;

    public ProductListing(){}

    public int getqAvailable() {
        return qAvailable;
    }

    public int getqSold() {
        return qSold;
    }

    public String getDepartment() {
        return department;
    }

    public int getRatings() {
        return ratings;
    }

    public String getDeptCat() {
        return deptCat;
    }

    public int getDisplayPriority() {
        return displayPriority;
    }

    public String getDeptCatSubCat() {
        return deptCatSubCat;
    }

    public String getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public int getNoOfRaters() {
        return noOfRaters;
    }

    public long getDateOfListing() {
        return dateOfListing;
    }

    public String getSellerID() {
        return sellerID;
    }

    public long getValidity() {
        return validity;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDeptCat(String deptCat) {
        this.deptCat = deptCat;
    }

    public void setDeptCatSubCat(String deptCatSubCat) {
        this.deptCatSubCat = deptCatSubCat;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDateOfListing(long dateOfListing) {
        this.dateOfListing = dateOfListing;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }

    public void setDisplayPriority(int displayPriority) {
        this.displayPriority = displayPriority;
    }

    public void setNoOfRaters(int noOfRaters) {
        this.noOfRaters = noOfRaters;
    }

    public void setqAvailable(int qAvailable) {
        this.qAvailable = qAvailable;
    }

    public void setqSold(int qSold) {
        this.qSold = qSold;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setValidity(long validity) {
        this.validity = validity;
    }


    protected ProductListing(Parcel in) {
        price = in.readString();
        department = in.readString();
        deptCat = in.readString();
        deptCatSubCat = in.readString();
        productName = in.readString();
        productThumb = in.readString();
        qAvailable = in.readInt();
        qSold = in.readInt();
        ratings = in.readInt();
        sellerID = in.readString();
        sellerName = in.readString();
        displayPriority = in.readInt();
        noOfRaters = in.readInt();
        dateOfListing = in.readLong();
        validity = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeString(department);
        dest.writeString(deptCat);
        dest.writeString(deptCatSubCat);
        dest.writeString(productName);
        dest.writeString(productThumb);
        dest.writeInt(qAvailable);
        dest.writeInt(qSold);
        dest.writeInt(ratings);
        dest.writeString(sellerID);
        dest.writeString(sellerName);
        dest.writeInt(displayPriority);
        dest.writeInt(noOfRaters);
        dest.writeLong(dateOfListing);
        dest.writeLong(validity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductListing> CREATOR = new Parcelable.Creator<ProductListing>() {
        @Override
        public ProductListing createFromParcel(Parcel in) {
            return new ProductListing(in);
        }

        @Override
        public ProductListing[] newArray(int size) {
            return new ProductListing[size];
        }
    };
}