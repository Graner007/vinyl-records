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
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = ProductController.orderDataStore.find(ProductController.orderDataStore.getAll().size());

        if (req.getParameterMap().containsKey("card-holder-name")) {
            String ccNumber = req.getParameter("cc-number");
            String ccExp = req.getParameter("cc-exp");
            String ccCvc = req.getParameter("cc-cvc");
            String cardHolderName = req.getParameter("card-holder-name");

            Payment payment = new Payment(ccNumber, ccExp, ccCvc, cardHolderName);
            currentOrder.getUser().setPayment(payment);
            LocalDate dateNow = LocalDate.now();
            currentOrder.setOrderDate(dateNow);
            Order newOrder = new Order();
            ProductController.orderDataStore.add(newOrder);
            ProductController.lineItemDataStore.removeAll();
            resp.sendRedirect(req.getContextPath() + "/confirmation");
        }

        engine.process("product/payment.html", context, resp.getWriter());
    }
}
