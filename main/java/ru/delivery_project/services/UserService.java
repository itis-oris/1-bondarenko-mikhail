package ru.delivery_project.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.db.DBConnection;
import ru.delivery_project.db.dao.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public final static Logger logger = LogManager.getLogger(UserService.class);
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, first_name, second_name, email, telephone, role FROM users";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
                ) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getString("email"),
                        resultSet.getString("telephone"),
                        resultSet.getString("role")
                ));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }
    public static void updateUserRole(int id, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        try(
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            logger.info("updated user with id {}, new role {}", id, role);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
                ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("deleted user with id {}", id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
