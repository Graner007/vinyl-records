package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {

    private ProductDao productDao;
    private GenreDao genreDao;
    private ArtistDao artistDao;

    public void setup(String user, String dbname, String password) throws SQLException, IOException {
        DataSource dataSource = connect(user, dbname, password);
        productDao = new ProductDaoJdbc(dataSource);
        genreDao = new GenreDaoJdbc(dataSource);
        artistDao = new ArtistDaoJdbc(dataSource);
    }

    public List<Product> getAllProducts() {
        System.out.println(productDao.getAll().size());
        return productDao.getAll();
    }

    public List<Artist> getAllArtists() {
        return artistDao.getAll();
    }

    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    public void addProduct(Product product) {
        productDao.add(product);
    }

    public void addGenre(Genre genre) {
        genreDao.add(genre);
    }

    public void addArtist(Artist artist) {
        artistDao.add(artist);
    }

    public void removeProducts() { productDao.removeAll(); }

    public void removeArtists() { artistDao.removeAll(); }

    public void removeGenres() { genreDao.removeALl(); }

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
