package com.codecool.shop.dao;

import com.codecool.shop.model.Genre;

import java.util.List;

public interface GenreDao {

    void add(Genre category);
    Genre find(int id);
    void remove(int id);

    List<Genre> getAll();

}
