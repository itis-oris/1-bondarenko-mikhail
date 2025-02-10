package ru.delivery_project.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.delivery_project.services.AdminService;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (AdminService.isAdmin(req)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            req.getRequestDispatcher("/WEB-INF/html/406.jsp").forward(req, resp);
        }
    }
}
