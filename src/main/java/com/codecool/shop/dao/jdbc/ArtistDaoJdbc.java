package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.model.Artist;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDaoJdbc implements ArtistDao {

    private DataSource dataSource;

    public ArtistDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Artist artist) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO artist (name, description) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, artist.getName());
            statement.setString(2, artist.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            artist.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist find(int id) {
        return null;
    }

    @Override
    public Artist findByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM artist WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Artist artist = new Artist(rs.getString(2));
            artist.setId(rs.getInt(1));
            return artist;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading artist with name: " + name, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    public void removeAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM artist";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artist> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM artist";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Artist> result = new ArrayList<>();
            while (rs.next()) {
                Artist artist = new Artist(rs.getString(2));

                artist.setId(rs.getInt(1));
                result.add(artist);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all artists", e);
        }
    }
}
