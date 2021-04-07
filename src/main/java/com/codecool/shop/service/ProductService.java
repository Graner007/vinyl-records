package com.codecool.shop.service;

import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.mem.ArtistDaoMem;
import com.codecool.shop.dao.mem.GenreDaoMem;
import com.codecool.shop.dao.mem.ProductDaoMem;
import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao = ProductDaoMem.getInstance();
    private GenreDao genreDao = GenreDaoMem.getInstance();
    private ArtistDao artistDao = ArtistDaoMem.getInstance();

    public ProductService() { }

    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    public List<Artist> getAllArtists() {
        return artistDao.getAll();
    }

}
