package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.codecool.shop.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private ProductService service = ProductServiceController.getProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = service.findOrberById(service.getAllOrders().size());
        int orderQuantity = currentOrder.getProductNumbers();

        context.setVariable("orderQuantity", orderQuantity);
        context.setVariable("genres", service.getAllGenres());
        context.setVariable("products", service.getAllProducts());

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        Order currentOrder = service.findOrberById(service.getAllOrders().size());

        if (data.get("name") != null && data.get("price") != null) {
            String recordName = data.get("name").getAsString();
            float recordPrice = Float.parseFloat(data.get("price").getAsString().split(" ")[0]);
            service.addLineItemToOrder(currentOrder, recordName, recordPrice);

            int orderQuantity = currentOrder.getProductNumbers();

            resp.getWriter().write(new Gson().toJson(orderQuantity));
        }
        else if (data.get("text") != null) {
            String text = data.get("text").getAsString();
            List<String> names = service.getArtistsOrGenres(text);

            resp.getWriter().write(new Gson().toJson(names));
        }
        else if (data.get("filter") != null) {
            String filter = data.get("filter").getAsString();
            List<List<String>> filterdProducts = service.getFilteredProductsByFilter(filter);

            resp.getWriter().write(new Gson().toJson(filterdProducts));
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

}
