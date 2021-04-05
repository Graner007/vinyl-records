package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = ProductController.orderDataStore.find(ProductController.orderDataStore.getAll().size());
        Genre category = ProductController.productCategoryDataStore.find(ProductController.productCategoryDataStore.getAll().size());

        context.setVariable("category", category);
        context.setVariable("cart", currentOrder.getProducts());
        context.setVariable("grandTotal", currentOrder.getGrandTotalPrice());

        engine.process("product/review.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        Order currentOrder = ProductController.orderDataStore.find(ProductController.orderDataStore.getAll().size());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("quantity") != null && data.get("name") != null) {
            int quantity = data.get("quantity").getAsInt();
            String name = data.get("name").getAsString();
            List<Float> result = changeProductQuantity(currentOrder, name, quantity);

            resp.getWriter().write(new Gson().toJson(result));
        }
    }

    private List<Float> changeProductQuantity(Order currentOrder, String name, int quantity) {
        List<Float> result = new ArrayList<>();

        LineItem lineItem = ProductController.lineItemDataStore.findByName(name);
        lineItem.setQuantity(quantity);

        if (quantity == 0) {
            ProductController.lineItemDataStore.removeByObject(lineItem);
            ProductController.orderDataStore.removeLineItem(lineItem);
        }

        float newGrandTotal = currentOrder.getGrandTotalPrice();

        result.add(lineItem.getSubTotalPrice());
        result.add(newGrandTotal);

        return result;
    }
}
