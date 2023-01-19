package com.example.ggaming_frontend.models;

public class PaymentCard {
    public PaymentCard() {
    }

    private String cardNumber;
    private String cardType;

    public PaymentCard(String cardNumber, String cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

}
