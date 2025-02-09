package ru.delivery_project.servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.db.dao.Category;
import ru.delivery_project.services.CategoryService;
import ru.delivery_project.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/add-product")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Category> categories = CategoryService.getCategories();
        req.setAttribute("categories", categories);
        req.setAttribute("title", "Добавить товар");
        req.getRequestDispatcher("/WEB-INF/html/admin/add-product.jsp").forward(req, resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProductService.addProduct(req);
        resp.sendRedirect("/admin?section=products");
    }
}
