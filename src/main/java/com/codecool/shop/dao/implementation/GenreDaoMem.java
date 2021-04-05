package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreDaoMem implements GenreDao {

    private List<Genre> data = new ArrayList<>();
    private static GenreDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private GenreDaoMem() {
    }

    public static GenreDaoMem getInstance() {
        if (instance == null) {
            instance = new GenreDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Genre category) {
        category.setId(data.size() + 1);
        data.add(category);
    }

    @Override
    public Genre find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Genre> getAll() {
        return data;
    }
}
