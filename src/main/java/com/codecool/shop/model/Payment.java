package com.codecool.shop.model;

public class Payment {
    private String cardNumber;
    private String cardExpiry;
    private String cardCvc;
    private String cardHolderName;

    public Payment(String cardNumber, String cardExpiry, String cardCvc, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.cardCvc = cardCvc;
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }
}
