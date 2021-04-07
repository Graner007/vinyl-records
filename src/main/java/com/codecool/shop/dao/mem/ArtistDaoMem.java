package com.codecool.shop.dao.mem;

import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistDaoMem implements ArtistDao {

    private List<Artist> data = new ArrayList<>();
    private static ArtistDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ArtistDaoMem() {}

    public static ArtistDaoMem getInstance() {
        if (instance == null) {
            instance = new ArtistDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Artist artist) {
        artist.setId(data.size() + 1);
        data.add(artist);
    }

    @Override
    public Artist find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Artist findByName(String name) {
        return data.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void removeAll() {

    }

    @Override
    public List<Artist> getAll() {
        return data;
    }
}
