package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseManagerTest {

    List<Artist> artists;
    DatabaseManager databaseManager;

    @BeforeEach
    void init(){
        databaseManager = new DatabaseManager();
        artists = databaseManager.getAllArtists();
    }

    // getAllArtists()
    @Test
    void getAllArtists_returnsBackAListOfArtists() {
        System.out.println(databaseManager.getAllArtists());

    }


    // getAllProducts()

    // getAllGenres()
    // addProduct(Product product)
    // addGenre(Genre genre)
    // addArtist(Artist artist)

}
