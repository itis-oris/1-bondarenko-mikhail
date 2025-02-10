package ru.delivery_project.servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.OrderService;

import java.io.IOException;
@WebServlet("/admin/delete-order")
public class AdminDeleteOrderServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        OrderService.deleteOrder(orderId);
        resp.sendRedirect(req.getContextPath() + "/admin?section=orders");
    }

}
