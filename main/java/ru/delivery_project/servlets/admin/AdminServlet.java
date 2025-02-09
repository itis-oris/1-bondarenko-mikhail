package ru.delivery_project.servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.CategoryService;
import ru.delivery_project.services.OrderService;
import ru.delivery_project.services.ProductService;
import ru.delivery_project.services.UserService;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String section = req.getParameter("section");
        if (section == null) {
            section = "orders";
        }
        switch (section) {
            case "users":
                req.setAttribute("users", UserService.getAllUsers());
                break;
            case "products":
                req.setAttribute("products", ProductService.getProducts());
                break;
            case "orders":
                req.setAttribute("orders", OrderService.getAllOrders());
                break;
            case "categories":
                req.setAttribute("categories", CategoryService.getCategories());
                break;
        }
        req.setAttribute("title", "Админ панель");
        req.getRequestDispatcher("/WEB-INF/html/admin/base.jsp").forward(req, resp);
    }
}
