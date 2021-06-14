package com.codecool.shop.service;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.jdbc.ArtistDaoJdbc;
import com.codecool.shop.dao.jdbc.GenreDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductDaoJdbc;
import com.codecool.shop.dao.jdbc.UserDaoJdbc;
import com.codecool.shop.dao.mem.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceController {

    private static ProductService productService;

    public ProductServiceController() throws SQLException { generateProductService(); }

    private void generateProductService() throws SQLException {
        if (ConfigType.getDao().equals("jdbc")) {
            List<String> dbConfigs = ConfigType.getDatabaseDetails();
            String user = dbConfigs.get(0);
            String databaseName = dbConfigs.get(1);
            String password = dbConfigs.get(2);

            DatabaseConnection db = new DatabaseConnection(user, databaseName, password);
            DataSource dataSource = db.connect();

            OrderDao orderDao = OrderDaoMem.getInstance();
            LineItemDao lineItemDao = LineItemDaoMem.getInstance();

            this.productService = new ProductService(new ProductDaoJdbc(dataSource), new GenreDaoJdbc(dataSource), new ArtistDaoJdbc(dataSource), orderDao, lineItemDao, new UserDaoJdbc(dataSource));
        }
        else {
            ProductDao productsDao = ProductDaoMem.getInstance();
            GenreDao genresDao = GenreDaoMem.getInstance();
            ArtistDao artistsDao = ArtistDaoMem.getInstance();
            OrderDao orderDao = OrderDaoMem.getInstance();
            LineItemDao lineItemDao = LineItemDaoMem.getInstance();
            UserDao userDao = UserDaoMem.getInstance();

            this.productService = new ProductService(productsDao, genresDao, artistsDao, orderDao, lineItemDao, userDao);
        }
    }

    public static ProductService getProductService() {
        return productService;
    }
}
