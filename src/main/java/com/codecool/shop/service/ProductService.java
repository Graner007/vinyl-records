package com.codecool.shop.service;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.model.*;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private GenreDao genreDao;
    private ArtistDao artistDao;
    private OrderDao orderDao;
    private LineItemDao lineItemDao;

    public ProductService(ProductDao productDao, GenreDao genreDao, ArtistDao artistDao) {
        this.productDao = productDao;
        this.genreDao = genreDao;
        this.artistDao = artistDao;
    }

    public ProductService(ProductDao productDao, GenreDao genreDao, ArtistDao artistDao, OrderDao orderDao, LineItemDao lineItemDao) {
        this.productDao = productDao;
        this.genreDao = genreDao;
        this.artistDao = artistDao;
        this.orderDao = orderDao;
        this.lineItemDao = lineItemDao;
    }

    public List<Product> getAllProducts() { return productDao.getAll(); }

    public List<Genre> getAllGenres() { return genreDao.getAll(); }

    public List<Artist> getAllArtists() { return artistDao.getAll(); }

    public List<Order> getAllOrders() { return orderDao.getAll(); }

    public List<LineItem> getAllLineItems() { return lineItemDao.getAll(); }

    public void addProduct(Product product) {
        productDao.add(product);
    }

    public void addGenre(Genre genre) {
        genreDao.add(genre);
    }

    public void addArtist(Artist artist) {
        artistDao.add(artist);
    }

    public void addOrder(Order order) { orderDao.add(order); }

    public void addLineItem(LineItem lineItem) { lineItemDao.add(lineItem); }

    public void removeProducts() { productDao.removeAll(); }

    public void removeArtists() { artistDao.removeAll(); }

    public void removeGenres() { genreDao.removeALl(); }

    public void removeLineItems() { lineItemDao.removeAll(); }

    public Order findOrberById(int id) { return orderDao.find(id); }

    public LineItem findLineItemByName(String name) { return lineItemDao.findByName(name); }

    public void removeLineItemByObject(LineItem lineItem) { lineItemDao.removeByObject(lineItem); }

    public void removeLineItemFromOrder(LineItem lineItem) { orderDao.removeLineItem(lineItem); }

    public List<List<String>> getFilteredProductsByFilter(String filter) {
        List<List<String>> filterdProducts = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();

        getAllProducts().forEach(product-> {
            List<String> productList = new ArrayList<>();
            if (product.getProductCategory().getName().equals(filter)) {
                productList.add(product.getName());
                productList.add(product.getProductCategory().getName());
                productList.add(product.getPrice());
                productList.add(product.getDescription());
                filterdProducts.add(productList);
                newProducts.add(product);
            }
            if (product.getSupplier().getName().equals(filter)) {
                productList.add(product.getName());
                productList.add(product.getProductCategory().getName());
                productList.add(product.getPrice());
                productList.add(product.getDescription());
                filterdProducts.add(productList);
                newProducts.add(product);
            }
        });

        return filterdProducts;
    }

    public List<String> getArtistsOrGenres(String text) {
        List<String> names = new ArrayList<>();

        switch (text) {
            case "genre":
                getAllGenres().forEach(genre -> names.add(genre.getName()));
                break;
            case "artist":
                getAllArtists().forEach(artist -> names.add(artist.getName()));
                break;
        }

        return names;
    }

    public void addLineItemToOrder(Order currentOrder, String recordName, float recordPrice) {
        if (lineItemDao.findByName(recordName) != null) {
            LineItem existsItem = lineItemDao.findByName(recordName);
            existsItem.setQuantity(existsItem.getQuantity()+1);
        }
        else {
            LineItem lineItem = new LineItem(recordName,1, recordPrice);
            lineItemDao.add(lineItem);

            currentOrder.addProduct(lineItem);
        }
    }

    public List<Float> changeProductQuantity(Order currentOrder, String name, int quantity) {
        List<Float> result = new ArrayList<>();

        LineItem lineItem = findLineItemByName(name);
        lineItem.setQuantity(quantity);

        if (quantity == 0) {
            removeLineItemByObject(lineItem);
            removeLineItemFromOrder(lineItem);
        }

        float newGrandTotal = currentOrder.getGrandTotalPrice();

        result.add(lineItem.getSubTotalPrice());
        result.add(newGrandTotal);

        return result;
    }

}
