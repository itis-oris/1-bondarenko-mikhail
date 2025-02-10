package ru.delivery_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.db.dao.Order;
import ru.delivery_project.services.OrderService;

import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet  extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = OrderService.getOrders(req);
        req.setAttribute("orders", orders);
        req.setAttribute("title", "Заказы");
        req.getRequestDispatcher("/WEB-INF/html/order.jsp").forward(req, resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        OrderService.updateOrderStatus(orderId, "Отменен");
        resp.sendRedirect(req.getContextPath() + "/orders");
    }
}
