package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Order {

    private int id;
    private Map<LineItem, Integer> products;

    public Order() {
        products = new HashMap<>();
    }

    public Map<LineItem, Integer> getProducts() { return products; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getProductNumbers() {
        if (!products.values().equals(null)) {
            return products.values().stream().collect(Collectors.summingInt(Integer::intValue));
        }
        return 0;
    }

    public void addProduct(LineItem lineItem) {
        if (products.containsKey(lineItem)) {
            products.put(lineItem, products.get(lineItem) + 1);
        }
        else {
            products.put(lineItem, 1);
        }
    }
}
