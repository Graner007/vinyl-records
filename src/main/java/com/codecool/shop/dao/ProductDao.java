package com.codecool.shop.dao;

import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;

import java.util.List;

public interface ProductDao {

    void add(Product product);
    Product find(int id);
    Product findByName(String name);
    void remove(int id);

    List<Product> getAll();
    List<Product> getBy(Artist artist);
    List<Product> getBy(Genre genre);

}
