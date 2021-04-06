package com.codecool.shop;

import com.codecool.shop.dao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class ProductServiceTest {
    ProductDao productDao;

    @BeforeEach
    void init() {
        productDao = mock(ProductDao.class);
    }

}
