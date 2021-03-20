package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 14-06-2017.
 */
@IgnoreExtraProperties
public class ProductDetails {

    private String discription;
    private String five;
    private String four;
    private String one;
    private String prices;
    private String six ;
    private String three;
    private String two;
    private String url1;
    private String url2;
    private String url3;

    public ProductDetails(){}

    public String getDiscription() {
        return discription;
    }

    public String getFive() {
        return five;
    }

    public String getFour() {
        return four;
    }

    public String getOne() {
        return one;
    }

    public String getPrices() {
        return prices;
    }

    public String getSix() {
        return six;
    }

    public String getThree() {
        return three;
    }

    public String getTwo() {
        return two;
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setFive(String five) {
        this.five = five;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public void setSix(String six) {
        this.six = six;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

}
