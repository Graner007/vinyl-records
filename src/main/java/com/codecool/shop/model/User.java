package com.codecool.shop.model;

public class User{

    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String companyName;
    private String billingAddress;
    private String billingCity;
    private String billingState;
    private int billingZip;
    private String shippingAddress;
    private String shippingCity;
    private String shippingState;
    private int shippingZip;
    private Payment payment;
    private int paymentId;

    public User(String name, String email, String phoneNumber, String companyName, String billingAddress, String billingCity, String billingState, int billingZip, String shippingAddress, String shippingCity, String shippingState, int shippingZip) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.billingAddress = billingAddress;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZip = billingZip;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingState = shippingState;
        this.shippingZip = shippingZip;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Payment getPayment() { return payment; }

    public void setPayment(Payment payment) { this.payment = payment; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public int getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(int billingZip) {
        this.billingZip = billingZip;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public int getShippingZip() {
        return shippingZip;
    }

    public void setShippingZip(int shippingZip) {
        this.shippingZip = shippingZip;
    }
}
