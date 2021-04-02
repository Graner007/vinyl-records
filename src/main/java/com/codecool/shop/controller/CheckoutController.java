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
import com.codecool.shop.model.*;
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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = ProductController.orderDataStore.find(ProductController.orderDataStore.getAll().size());

        List<String> dataNames = Arrays.asList("name", "email", "phone", "billingCompany", "billingAddress", "billingCity",
                "billingState", "billingZip", "shippingAddress", "shippingCity", "shippingState", "shippingZip");

        if (req.getParameterMap().containsKey("name")) {
            List<String> userDatas = new ArrayList<>();
            for (int i = 0; i < dataNames.size(); i++) {
                userDatas.add(req.getParameter(dataNames.get(i)));
            }

            currentOrder.setUser(new User(userDatas.get(0), userDatas.get(1), userDatas.get(2), userDatas.get(3), userDatas.get(4), userDatas.get(5), userDatas.get(6), Integer.parseInt(userDatas.get(7)), userDatas.get(8), userDatas.get(9),  userDatas.get(10), Integer.parseInt(userDatas.get(11))));
            resp.sendRedirect(req.getContextPath() + "/payment");
        }

        engine.process("product/checkout.html", context, resp.getWriter());
    }
}
