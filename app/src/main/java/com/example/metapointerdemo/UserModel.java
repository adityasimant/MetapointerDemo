package com.example.metapointerdemo;

public class UserModel {

    String name;
    String phoneNumber;
    String email;
    String amount;



    public UserModel(String name, String phoneNumber, String email, String amount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
