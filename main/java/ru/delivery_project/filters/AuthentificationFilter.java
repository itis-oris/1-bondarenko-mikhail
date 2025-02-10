package ru.delivery_project.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.SecurityService;
import ru.delivery_project.db.DBConnection;

import java.io.IOException;

@WebFilter(value = {"/cart", "/orders"})
public class AuthentificationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (SecurityService.isSigned(request)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("WEB-INF/html/signin.jsp").forward(request, response);
        }
    }
}
