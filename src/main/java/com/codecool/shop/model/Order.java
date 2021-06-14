package com.codecool.shop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private List<LineItem> products;
    private User user;
    private LocalDate orderDate;

    public Order() {
        products = new ArrayList<>();
    }

    public List<LineItem> getProducts() { return products; }

    public void setProducts(List<LineItem> products) { this.products = products; }

    public int getId() { return id; }

    public LocalDate getOrderDate() { return orderDate; }

    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public void setId(int id) { this.id = id; }

    public int getProductNumbers() {
        int counter = 0;
        for (LineItem item : products) {
            counter += item.getQuantity();
        }
        return counter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public float getGrandTotalPrice() {
        float counter = 0;
        for (int i = 0; i < products.size(); i++) {
            counter += products.get(i).getSubTotalPrice();
        }
        return counter;
    }

    public void addProduct(LineItem lineItem) { products.add(lineItem); }
}
