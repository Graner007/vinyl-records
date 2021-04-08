package com.codecool.shop.service;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.jdbc.ArtistDaoJdbc;
import com.codecool.shop.dao.jdbc.GenreDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductDaoJdbc;
import com.codecool.shop.model.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private GenreDao genreDao;
    private ArtistDao artistDao;
    private OrderDao orderDao;
    private LineItemDao lineItemDao;

    public void setup(String user, String dbname, String password) throws SQLException, IOException {
        DataSource dataSource = connect(user, dbname, password);
        productDao = new ProductDaoJdbc(dataSource);
        genreDao = new GenreDaoJdbc(dataSource);
        artistDao = new ArtistDaoJdbc(dataSource);
    }

    public ProductService(String user, String dbname, String password) throws IOException, SQLException {
        setup(user, dbname, password);
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

    public Order findOrberById(int id) { return orderDao.find(id); }

    public LineItem findLineItemByName(String name) { return lineItemDao.findByName(name); }

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

    private DataSource connect(String user, String dbname, String password) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(dbname);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

}
