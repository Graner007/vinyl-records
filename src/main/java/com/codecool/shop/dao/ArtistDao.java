package com.codecool.shop.dao;

import com.codecool.shop.model.Artist;

import java.util.List;

public interface ArtistDao {

    void add(Artist artist);
    Artist find(int id);
    Artist findByName(String name);
    void remove(int id);
    void removeAll();

    List<Artist> getAll();
}
