package ru.delivery_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.services.SecurityService;

import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(SignInServlet.class);
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        logger.info("/signin");
        if (SecurityService.isSigned(req)) {
            req.setAttribute("title","400 Bad Request");
            res.setStatus(400);
            req.setAttribute("message", "Вы уже авторизованы");
            logger.info("User authorized");
            req.getRequestDispatcher("WEB-INF/html/400.jsp").forward(req, res);
        } else {
            logger.info("User not authorized");
            req.setAttribute("title", "Войти");
            req.getRequestDispatcher("WEB-INF/html/signin.jsp").forward(req, res);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if(SecurityService.signIn(req, res)) {
            res.sendRedirect(req.getContextPath() + "/");
        }




    }
}
