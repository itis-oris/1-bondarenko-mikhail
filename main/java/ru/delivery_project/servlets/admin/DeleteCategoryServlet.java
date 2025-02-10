package ru.delivery_project.servlets.admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.CategoryService;

import java.io.IOException;


@WebServlet("/admin/delete-category")
public class DeleteCategoryServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        CategoryService.deleteCategory(categoryId);
        resp.sendRedirect("/admin?section=categories");
    }
}
