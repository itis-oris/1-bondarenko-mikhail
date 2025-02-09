package ru.delivery_project.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.db.DBConnection;
import ru.delivery_project.jwt.JwtGenerator;
import ru.delivery_project.jwt.JwtValidator;

import java.io.IOException;
import java.sql.*;

public class SecurityService {
    private static final Logger logger = LogManager.getLogger(SecurityService.class);

    public static boolean isSigned(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return false;
        }

        for (Cookie cookie : cookies) {
            if ("jwt".equals(cookie.getName())) {
                String jwtCookie = cookie.getValue();
                String query = "select email, current_token from users where email = ?";
                String email = JwtValidator.validate(jwtCookie).getSubject();
                try (
                        Connection connection = DBConnection.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query)
                ) {
                    preparedStatement.setString(1, email);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String token = resultSet.getString("current_token");
                        resultSet.close();
                        return token.equals(jwtCookie);
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    public static boolean signIn(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String selectQuery = "SELECT password_hash FROM users WHERE email = ?";
        String updateQuery = "UPDATE users SET current_token = ? WHERE email = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setString(1, email);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String passwordHash = rs.getString("password_hash");
                if (PasswordSecurity.checkPassword(password, passwordHash)) {
                    String token = JwtGenerator.generateToken(email);

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, token);
                        updateStmt.setString(2, email);
                        updateStmt.executeUpdate();
                        logger.info("user with email {} signed in", email);
                    }

                    Cookie cookie = new Cookie("jwt", token);
                    cookie.setMaxAge(60 * 60); // 1 час
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                    return true;
                } else {
                    req.setAttribute("message", "Неверный пароль");
                }
            } else {
                req.setAttribute("message", "Пользователь не найден");
            }

            req.getRequestDispatcher("WEB-INF/html/signin.jsp").forward(req, resp);
            return false;

        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void signOut(HttpServletRequest req, HttpServletResponse resp) {
        if (isSigned(req)) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                }
            }
        }
    }

    public static void register(HttpServletRequest req, HttpServletResponse resp){

        String insertQuery =
                """
                        INSERT INTO users (first_name, second_name, email, password_hash, telephone, role) VALUES
                        (?, ?, ?, ?, ?, ?);
                        """;
        String selectQuery = "SELECT email, telephone FROM users WHERE email = ? OR telephone = ?";
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordHash = PasswordSecurity.hashPassword(password);
        String firstName = req.getParameter("first_name");
        String secondName = req.getParameter("second_name");
        String tel = req.getParameter("telephone");
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery)
        ) {
            selectStmt.setString(1, email);
            selectStmt.setString(2, tel);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String existingEmail = rs.getString("user_email");
                String existingTelephone = rs.getString("telephone");
                if (email.equals(existingEmail)) {
                    req.setAttribute("message", "Пользователь с таким email уже существует.");
                    req.getRequestDispatcher("WEB-INF/html/registration.jsp").forward(req, resp);
                } else if (tel.equals(existingTelephone)) {
                    req.setAttribute("message", "Пользователь с таким номером телефона уже существует.");
                    req.getRequestDispatcher("WEB-INF/html/registration.jsp").forward(req, resp);
                }
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Пользователь с таким email или телефоном уже существует.", e);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setString(1, firstName);
            insertStmt.setString(2, secondName);
            insertStmt.setString(3, email);
            insertStmt.setString(4, passwordHash);
            insertStmt.setString(5, tel);
            insertStmt.setString(6, "user");
            insertStmt.executeUpdate();
            logger.info("user with email {} registered", email);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static Integer getUserId(HttpServletRequest req) {
        if (isSigned(req)) {
            String jwt = null;
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwt")) {
                        jwt = cookie.getValue();
                    }
                }
            } else {
                logger.error("no jwt token");
            }
            if (jwt != null) {
                String userEmail = JwtValidator.getUserEmail(jwt);
                String query = """
                        SELECT id FROM users WHERE email = ?;
                        """;
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setString(1, userEmail);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("id");
                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}

