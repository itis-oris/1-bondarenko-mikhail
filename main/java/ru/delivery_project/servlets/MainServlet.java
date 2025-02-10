package ru.delivery_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.db.dao.Catalog;
import ru.delivery_project.services.CatalogService;

import java.io.IOException;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Catalog catalog = CatalogService.getCatalog(req);
        req.setAttribute("products", catalog.getProducts());
        req.setAttribute("categories", catalog.getCategories());
        req.setAttribute("title", "Каталог");
        req.getRequestDispatcher("/WEB-INF/html/main.jsp").forward(req, res);

    }
}
