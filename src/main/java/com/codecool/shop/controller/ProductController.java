package com.codecool.shop.controller;

import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ArtistDaoMem;
import com.codecool.shop.dao.implementation.GenreDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
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

            orderDataStore.find(1).addProduct(lineItem);

            resp.getWriter().write(new Gson().toJson(orderDataStore.find(1).getProductNumbers()));
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
            List<Product> filterdProducts = new ArrayList<>();

            products.forEach(product-> {
                if (product.getProductCategory().getName().equals(filter)) {
                    filterdProducts.add(product);
                }
                if (product.getSupplier().getName().equals(filter)) {
                    filterdProducts.add(product);
                }
            });

            System.out.println(filterdProducts.toString());

            resp.getWriter().write(new Gson().toJson(filterdProducts));
        }
    }
}
