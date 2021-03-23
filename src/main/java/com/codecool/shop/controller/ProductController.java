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
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private GenreDao productCategoryDataStore = GenreDaoMem.getInstance();
    private OrderDao orderDataStore = OrderDaoMem.getInstance();
    private ArtistDao artistDataStore = ArtistDaoMem.getInstance();

    private Genre genre = productCategoryDataStore.find(1);
    private List<Product> products = productDataStore.getAll();
    private List<Artist> artists = artistDataStore.getAll();
    private List<Genre> genres = productCategoryDataStore.getAll();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        //int productsNumber = orderDataStore.find(1).getProductNumbers();
      
        context.setVariable("category", genre);
        context.setVariable("products", products);
        //context.setVariable("productsNumber", productsNumber);

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("name").getAsString().length() != 0 && data.get("price").getAsString().length() != 0) {
            String recordName = data.get("name").getAsString();
            float recordPrice = Float.parseFloat(data.get("price").getAsString().split(" ")[0]);

            LineItem lineItem = new LineItem(recordName,1, recordPrice);

            orderDataStore.find(1).addProduct(lineItem);

            resp.getWriter().write(new Gson().toJson(orderDataStore.find(1).getProductNumbers()));
        }
        else if (data.get("text").getAsString().length() != 0) {
            String text = data.get("text").getAsString();

            switch (text) {
                case "genre":
                    resp.getWriter().write(new Gson().toJson(genres));
                    break;
                case "artist":
                    resp.getWriter().write(new Gson().toJson(artists));
                    break;
            }
        }
    }
}
