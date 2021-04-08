package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.mem.*;
import com.codecool.shop.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = null;
        try {
            productService = getProductService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Order currentOrder = productService.findOrberById(productService.getAllOrders().size());
        int orderQuantity = currentOrder.getProductNumbers();
      
        context.setVariable("genres", productService.getAllGenres());
        context.setVariable("products", productService.getAllProducts());
        context.setVariable("orderQuantity", orderQuantity);

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        ProductService productService = null;
        try {
            productService = getProductService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Order currentOrder = productService.findOrberById(productService.getAllOrders().size());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("name") != null && data.get("price") != null) {
            String recordName = data.get("name").getAsString();
            System.out.println(recordName);
            float recordPrice = Float.parseFloat(data.get("price").getAsString().split(" ")[0]);
            productService.addLineItemToOrder(currentOrder, recordName, recordPrice);

            int orderQuantity = currentOrder.getProductNumbers();

            resp.getWriter().write(new Gson().toJson(orderQuantity));
        }
        else if (data.get("text") != null) {
            String text = data.get("text").getAsString();
            List<String> names = productService.getArtistsOrGenres(text);

            resp.getWriter().write(new Gson().toJson(names));
        }
        else if (data.get("filter") != null) {
            String filter = data.get("filter").getAsString();
            List<List<String>> filterdProducts = productService.getFilteredProductsByFilter(filter);

            resp.getWriter().write(new Gson().toJson(filterdProducts));
        }
    }

    private ProductService getProductService() throws IOException, SQLException {
        String appConfigPath = "src/main/resources/connection.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }

        String dao = appProps.getProperty("dao");
        if (dao.equals("jdbc")) {
            String database = appProps.getProperty("database");
            String user = appProps.getProperty("user");
            String password = appProps.getProperty("password");

            ProductService productService = new ProductService(database, user, password);
            return productService;
        }
        else {
            ProductDao productsDao = ProductDaoMem.getInstance();
            GenreDao genresDao = GenreDaoMem.getInstance();
            ArtistDao artistsDao = ArtistDaoMem.getInstance();
            OrderDao orderDao = OrderDaoMem.getInstance();
            LineItemDao lineItemDao = LineItemDaoMem.getInstance();

            ProductService productService = new ProductService(productsDao, genresDao, artistsDao, orderDao, lineItemDao);
            return productService;
        }
    }

}
