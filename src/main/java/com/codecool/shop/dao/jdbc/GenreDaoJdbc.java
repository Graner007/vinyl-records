package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class GenreDaoJdbc implements GenreDao {

    private DataSource dataSource;

    public GenreDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Genre genre) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO genre (name, description) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            genre.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Genre find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Genre> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM genre";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Genre> result = new ArrayList<>();
            while (rs.next()) {
                Genre genre = new Genre(rs.getString(2), rs.getString(3));

                genre.setId(rs.getInt(1));
                result.add(genre);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all genres", e);
        }
    }
}
