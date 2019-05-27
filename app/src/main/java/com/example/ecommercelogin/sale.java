package com.example.ecommercelogin;

public class sale {
private String Name;
private String phNo;
private String SaleData;
private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public sale() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getSaleData() {
        return SaleData;
    }

    public void setSaleData(String saleData) {
        SaleData = saleData;
    }
}
