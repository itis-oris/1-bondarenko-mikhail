package ru.delivery_project.servlets.admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.OrderService;

import java.io.IOException;

@WebServlet("/admin/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int orderId = Integer.parseInt(req.getParameter("order_id"));
        String status = req.getParameter("status");
        OrderService.updateOrderStatus(orderId, status);
        resp.sendRedirect(req.getContextPath() + "/admin?section=orders");
    }
}
