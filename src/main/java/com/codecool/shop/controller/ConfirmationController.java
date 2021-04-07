package com.codecool.shop.controller;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(urlPatterns = {"/confirmation"})
public class ConfirmationController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmationController.class);

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

        appendToFile("orderInformation.json", gson.toJson(currentOrder));

        String currentOrderId = String.valueOf(currentOrder.getId());
        String currentOrderDate = String.valueOf(currentOrder.getOrderDate());
        String fileName = currentOrderId + "-" + currentOrderDate + ".json";
        createNewFile(fileName, gson.toJson(currentOrder + gson.toJson(currentOrder.getUser())) + gson.toJson(currentOrder.getProducts()));

        engine.process("product/confirmation.html", context, resp.getWriter());
    }

    private void appendToFile(String fileName, String text) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.close();
            logger.trace("Append was succesfull.");
        }
        catch (IOException e) {
            logger.error(String.valueOf(e));
        }
    }

    private void createNewFile(String fileName, String text) {
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(myObj.getPath());
                myWriter.write(text);
                myWriter.close();
                logger.trace("Create new file was succesfull.");
            }
            else {
                logger.warn("File already exits.");
            }
        }
        catch (IOException e) {
            logger.error("An error occurred.");
        }
    }
}
