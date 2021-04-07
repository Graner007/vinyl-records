package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product (name, description, defaultPrice,  defaultCurrency, genre_id, artist_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getDefaultPrice());
            statement.setString(4, String.valueOf(product.getDefaultCurrency()));
            statement.setInt(5, product.getSupplier().getId());
            statement.setInt(6, product.getProductCategory().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public Product findByName(String name) {
        return null;
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public void removeAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM product";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT p.id, p.name, p.description, p.defaultPrice, p.defaultCurrency, p.genre_id, p.artist_id, g.id, g.name, g.description, a.id, a.name, a.description\n" +
                    "FROM product as p\n" +
                    "INNER JOIN genre as g ON g.id = p.id\n" +
                    "INNER JOIN artist as a ON a.id = p.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                Genre genre = new Genre(rs.getString(9), rs.getString(10));
                genre.setId(rs.getInt(8));

                Artist artist = new Artist(rs.getString(12), rs.getString(13));
                artist.setId(rs.getInt(11));

                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(5), rs.getString(3), genre, artist);

                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all products", e);
        }
    }

    @Override
    public List<Product> getBy(Genre genre) {
        return null;
    }

    @Override
    public List<Product> getBy(Artist artist) {
        return null;
    }
}
