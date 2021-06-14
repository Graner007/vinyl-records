package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Payment;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    private ProductService service = ProductServiceController.getProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Order currentOrder = service.findOrberById(service.getAllOrders().size());

        if (req.getParameterMap().containsKey("card-holder-name")) {
            String ccNumber = req.getParameter("cc-number");
            String ccExp = req.getParameter("cc-exp");
            String ccCvc = req.getParameter("cc-cvc");
            String cardHolderName = String.valueOf(req.getParameter("card-holder-name"));

            Payment payment = new Payment(ccNumber, ccExp, ccCvc, cardHolderName);
            currentOrder.getUser().setPayment(payment);
            LocalDate dateNow = LocalDate.now();
            currentOrder.setOrderDate(dateNow);
            Order newOrder = new Order();
            service.addOrder(newOrder);
            service.removeLineItems();
            resp.sendRedirect(req.getContextPath() + "/confirmation");
        }

        engine.process("product/payment.html", context, resp.getWriter());
    }
}
