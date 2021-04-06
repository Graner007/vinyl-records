package com.codecool.shop.service;

import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private GenreDao genreDao;

    public ProductService(ProductDao productDao, GenreDao genreDao) {
        this.productDao = productDao;
        this.genreDao = genreDao;
    }

    public Genre getProductCategory(int categoryId){
        return genreDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId){
        var category = genreDao.find(categoryId);
        return productDao.getBy(category);
    }

}
