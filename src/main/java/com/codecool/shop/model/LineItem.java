package com.codecool.shop.model;

public class LineItem {

    private String name;
    private float price;

    public LineItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }


}
