package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.GenreDaoMem;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.LineItem;
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

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ReviewController extends HttpServlet {

    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private GenreDao productCategoryDataStore = GenreDaoMem.getInstance();
    private OrderDao orderDataStore = OrderDaoMem.getInstance();
    LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();

    private Genre category = productCategoryDataStore.find(1);
    private float grandTotal = orderDataStore.find(1).getGrandTotalPrice();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("category", category);
        context.setVariable("cart", orderDataStore.find(1).getProducts());
        context.setVariable("grandTotal", grandTotal);

        engine.process("product/review.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("quantity") != null && data.get("name") != null) {
            int quantity = data.get("quantity").getAsInt();
            String name = data.get("name").getAsString();
            List<Float> result = new ArrayList<>();

            LineItem lineItem = lineItemDataStore.findByName(name);
            lineItem.setQuantity(quantity);

            if (quantity == 0) {
                lineItemDataStore.removeByObject(lineItem);
                orderDataStore.removeLineItem(lineItem);
            }

            float newGrandTotal = orderDataStore.find(1).getGrandTotalPrice();
            grandTotal = newGrandTotal;

            result.add(lineItem.getSubTotalPrice());
            result.add(newGrandTotal);

            resp.getWriter().write(new Gson().toJson(result));
        }
    }
}
