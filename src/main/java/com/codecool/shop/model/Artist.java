package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Artist extends BaseModel {
    private List<Product> products;

    public Artist(String name, String description) {
        super(name, description);
        this.products = new ArrayList<>();
    }

    public Artist(String name) {
        super(name);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}