package com.nexm.ghatanjionline.models;

public class Customer {
    public String name;
    public String middleName;
    public String surname;
    public String address;
    public String altMoNo;
    public String email;
    public int cartitemsNo=0;

    public Customer(){}

    public int getCartitemsNo() {
        return cartitemsNo;
    }

    public void setCartitemsNo(int cartitemsNo) {
        this.cartitemsNo = cartitemsNo;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAltMoNo(String altMoNo) {
        this.altMoNo = altMoNo;
    }

    public String getEmail() {
        return email;
    }

    public String getAltMoNo() {
        return altMoNo;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
