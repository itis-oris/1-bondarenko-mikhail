package ru.delivery_project.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.delivery_project.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {
    public static boolean isAdmin(HttpServletRequest req){
        int userId = SecurityService.getUserId(req);
        String query = """
                SELECT role FROM users WHERE id = ?;
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)
                ) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("role").equals("admin")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
