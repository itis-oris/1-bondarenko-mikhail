package ru.delivery_project.servlets.admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.UserService;

import java.io.IOException;

@WebServlet("/admin/update-user-role")
public class UpdateUserRoleServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String role = req.getParameter("role");
        UserService.updateUserRole(userId, role);
        resp.sendRedirect("/admin?section=users");
    }
}
