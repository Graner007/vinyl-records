package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.jdbc.GameDatabaseManager;
import com.codecool.shop.dao.mem.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    public static ProductDao productDataStore = ProductDaoMem.getInstance();
    public static GenreDao productCategoryDataStore = GenreDaoMem.getInstance();
    public static OrderDao orderDataStore = OrderDaoMem.getInstance();
    public static ArtistDao artistDataStore = ArtistDaoMem.getInstance();
    public static LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();
    public static GameDatabaseManager dbManager;

    private List<Product> productsMem = productDataStore.getAll();
    private List<Artist> artistsMem = artistDataStore.getAll();
    private List<Genre> genresMem = productCategoryDataStore.getAll();

    private List<Product> productsJdbc;
    private List<Genre> genresJdbc;
    private List<Artist> artistsJdbc;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setupDbManager();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = orderDataStore.find(orderDataStore.getAll().size());
        productsJdbc = dbManager.getAllProducts();
        genresJdbc = dbManager.getAllGenres();
        artistsJdbc = dbManager.getAllArtists();
        int orderQuantity = currentOrder.getProductNumbers();
      
        context.setVariable("genres", genresJdbc);
        context.setVariable("products", productsJdbc);
        context.setVariable("orderQuantity", orderQuantity);

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        Order currentOrder = orderDataStore.find(orderDataStore.getAll().size());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("name") != null && data.get("price") != null) {
            String recordName = data.get("name").getAsString();
            System.out.println(recordName);
            float recordPrice = Float.parseFloat(data.get("price").getAsString().split(" ")[0]);
            addLineItemToOrder(currentOrder, recordName, recordPrice);

            int orderQuantity = currentOrder.getProductNumbers();

            resp.getWriter().write(new Gson().toJson(orderQuantity));
        }
        else if (data.get("text") != null) {
            String text = data.get("text").getAsString();
            List<String> names = getArtistsOrGenres(text);

            resp.getWriter().write(new Gson().toJson(names));
        }
        else if (data.get("filter") != null) {
            String filter = data.get("filter").getAsString();
            List<List<String>> filterdProducts = getFilteredProductsByFilter(filter);

            resp.getWriter().write(new Gson().toJson(filterdProducts));
        }
    }

    private List<List<String>> getFilteredProductsByFilter(String filter) {
        List<List<String>> filterdProducts = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();

        productsJdbc.forEach(product-> {
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

    private List<String> getArtistsOrGenres(String text) {
        List<String> names = new ArrayList<>();

        switch (text) {
            case "genre":
                genresJdbc.forEach(genre -> names.add(genre.getName()));
                break;
            case "artist":
                artistsJdbc.forEach(artist -> names.add(artist.getName()));
                break;
        }
        
        return names;
    }

    private void addLineItemToOrder(Order currentOrder, String recordName, float recordPrice) {
        if (lineItemDataStore.findByName(recordName) != null) {
            LineItem existsItem = lineItemDataStore.findByName(recordName);
            existsItem.setQuantity(existsItem.getQuantity()+1);
        }
        else {
            LineItem lineItem = new LineItem(recordName,1, recordPrice);
            lineItemDataStore.add(lineItem);

            currentOrder.addProduct(lineItem);
        }
    }

    public void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

}
