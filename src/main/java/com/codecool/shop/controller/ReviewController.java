package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.GenreDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ReviewController extends HttpServlet {

    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private GenreDao productCategoryDataStore = GenreDaoMem.getInstance();
    private OrderDao orderDataStore = OrderDaoMem.getInstance();

    private Genre category = productCategoryDataStore.find(1);
    //private List<Product> products = productDataStore.getBy(productCategoryDataStore.find(1));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        //int productsNumber = orderDataStore.find(1).getProductNumbers();

        context.setVariable("category", category);
        //context.setVariable("products", products);
        context.setVariable("cart", orderDataStore.find(1).getProducts());
        //context.setVariable("productsNumber", productsNumber);


        engine.process("product/review.html", context, resp.getWriter());
    }
}
