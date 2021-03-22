package com.codecool.shop.dao;

import com.codecool.shop.model.Artist;

import java.util.List;

public interface ArtistDao {

    void add(Artist artist);
    Artist find(int id);
    void remove(int id);

    List<Artist> getAll();
}
