package com.codecool.shop;

import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class ProductServiceTest {
    ProductDao productDao;
    GenreDao genreDao;

    @BeforeEach
    void init() {
        productDao = mock(ProductDao.class);
        genreDao = mock(GenreDao.class);
    }

    @Test
    void test_getProductCategory() {
        assertTrue(true);
    }

    @Test
    void test_getProductsForCategory(){

    }

}
