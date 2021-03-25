package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private GenreDao productCategoryDataStore = GenreDaoMem.getInstance();
    private OrderDao orderDataStore = OrderDaoMem.getInstance();
    private ArtistDao artistDataStore = ArtistDaoMem.getInstance();
    private LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();

    private List<Product> products = productDataStore.getAll();
    private List<Artist> artists = artistDataStore.getAll();
    private List<Genre> genres = productCategoryDataStore.getAll();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
      
        context.setVariable("genres", genres);
        context.setVariable("products", products);

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("name") != null && data.get("price") != null) {
            String recordName = data.get("name").getAsString();
            float recordPrice = Float.parseFloat(data.get("price").getAsString().split(" ")[0]);

            LineItem lineItem = new LineItem(recordName,1, recordPrice);
            lineItemDataStore.add(lineItem);

            Order order = orderDataStore.find(1);
            order.addProduct(lineItem);

            resp.getWriter().write(new Gson().toJson(order.getProductNumbers()));
        }
        else if (data.get("text") != null) {
            String text = data.get("text").getAsString();
            List<String> names = new ArrayList<>();

            switch (text) {
                case "genre":
                    genres.forEach(genre -> names.add(genre.getName()));
                    resp.getWriter().write(new Gson().toJson(names));
                    break;
                case "artist":
                    artists.forEach(artist -> names.add(artist.getName()));
                    resp.getWriter().write(new Gson().toJson(names));
                    break;
            }
        }
        else if (data.get("filter") != null) {
            String filter = data.get("filter").getAsString();
            List<List<String>> filterdProducts = new ArrayList<>();
            List<Product> newProducts = new ArrayList<>();

            products.forEach(product-> {
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

            resp.getWriter().write(new Gson().toJson(filterdProducts));
        }
    }
}
