package ru.delivery_project.servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.CategoryService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/add-category")
public class AddCategoryServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("title", "Добавить категорию");
        req.getRequestDispatcher("/WEB-INF/html/admin/add-category.jsp").forward(req,resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CategoryService.addCategory(req);
        resp.sendRedirect(req.getContextPath() + "/admin?section=categories");
    }

}
