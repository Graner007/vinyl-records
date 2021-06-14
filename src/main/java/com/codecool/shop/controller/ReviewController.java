package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceController;
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

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ReviewController extends HttpServlet {

    private ProductService service = ProductServiceController.getProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = service.findOrberById(service.getAllOrders().size());

        context.setVariable("cart", currentOrder.getProducts());
        context.setVariable("grandTotal", currentOrder.getGrandTotalPrice());

        engine.process("product/review.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        Order currentOrder = service.findOrberById(service.getAllOrders().size());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (data.get("quantity") != null && data.get("name") != null) {
            int quantity = data.get("quantity").getAsInt();
            String name = data.get("name").getAsString();
            List<Float> result = service.changeProductQuantity(currentOrder, name, quantity);

            resp.getWriter().write(new Gson().toJson(result));
        }
    }
}
