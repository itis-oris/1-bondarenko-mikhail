package ru.delivery_project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.CartService;
import ru.delivery_project.services.SecurityService;


import java.io.IOException;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (SecurityService.isSigned(req)) {
            CartService.addToCart(req);
            resp.sendRedirect("/");
        }else {
            resp.sendRedirect("/signin");
        }

    }
}
