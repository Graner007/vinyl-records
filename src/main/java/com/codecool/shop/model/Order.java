package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private List<LineItem> products;

    public Order() {
        products = new ArrayList<>();
    }

    public List<LineItem> getProducts() { return products; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getProductNumbers() {
        int counter = 0;
        for (LineItem item : products) {
            counter += item.getQuantity();
        }
        return counter;
    }

    public void addProduct(LineItem lineItem) {
        if (products.contains(lineItem)) {
            LineItem item = products.get(products.indexOf(lineItem));
            item.setQuantity(item.getQuantity()+1);
        }
        else {
            products.add(lineItem);
        }
    }
}
