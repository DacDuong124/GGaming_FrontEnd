package com.example.ggaming_frontend.models;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class User {

    @DocumentId
    private String docId;
    private String username;
    private Integer age;
    private String country;
    private String email;
    private ArrayList<PaymentCard> paymentCards;


    public User(String docId, String username, Integer age, String country, String email, ArrayList<PaymentCard> paymentCards) {
        this.docId = docId;
        this.username = username;
        this.age = age;
        this.country = country;
        this.email = email;
        this.paymentCards = paymentCards;
    }

    public User() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<PaymentCard> getPaymentCards() {
        return paymentCards;
    }

    public void setPaymentCards(ArrayList<PaymentCard> paymentCards) {
        this.paymentCards = paymentCards;
    }
}
