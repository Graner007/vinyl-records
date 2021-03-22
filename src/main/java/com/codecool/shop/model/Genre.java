package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Genre extends BaseModel {
    private String genre;
    private List<Product> categories;

    public Genre(String name, String genre, String description) {
        super(name, description);
        this.genre = genre;
        this.categories = new ArrayList<>();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCategories(ArrayList<Product> categories) {
        this.categories = categories;
    }

    public List<Product> getCategories() {
        return this.categories;
    }

    public void addProduct(Product product) {
        this.categories.add(product);
    }

    @Override
    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.genre,
                this.description);
    }
}