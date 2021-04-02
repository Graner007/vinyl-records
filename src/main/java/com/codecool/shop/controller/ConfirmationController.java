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
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Payment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/confirmation"})
public class ConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setCharacterEncoding("UTF-8");
        Order currentOrder = ProductController.orderDataStore.find(ProductController.orderDataStore.getAll().size()-1);

        context.setVariable("orderId", currentOrder.getId());
        context.setVariable("order", currentOrder.getProducts());
        context.setVariable("userDetails", currentOrder.getUser());
        context.setVariable("orderDate", currentOrder.getOrderDate());

        Gson gson = new Gson();
        try {
            File myObj = new File("orderInformation.json");
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(myObj.getPath());
                myWriter.write(gson.toJson(currentOrder));
                myWriter.close();
            }
            else {
                System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File(currentOrder.getId() + String.valueOf(currentOrder.getOrderDate()) + ".json");
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(myObj.getPath());
                myWriter.write(gson.toJson(currentOrder + gson.toJson(currentOrder.getUser())) + gson.toJson(currentOrder.getProducts()));
                myWriter.close();
            }
            else {
                System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        engine.process("product/confirmation.html", context, resp.getWriter());
    }
}
