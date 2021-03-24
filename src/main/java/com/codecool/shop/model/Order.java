package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private List<LineItem> products;
    private User user;

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

    public void addUser(User user) {
        user = user;
    }

    public User getUser() {
        return user;
    }

    public float getGrandTotalPrice() {
        float counter = 0;
        for (int i = 0; i < products.size(); i++) {
            counter += products.get(i).getSubTotalPrice();
        }
        return counter;
    }

    public void addProduct(LineItem lineItem) {
        boolean find = false;
        for (LineItem item : products) {
            if (item.getName().equals(lineItem.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                find = true;
            }
        }
        if (!find) {
            products.add(lineItem);
        }
    }
}
