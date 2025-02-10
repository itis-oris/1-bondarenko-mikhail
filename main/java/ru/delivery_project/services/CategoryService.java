package ru.delivery_project.services;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.db.DBConnection;
import ru.delivery_project.db.dao.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryService.class);
    public static Integer getCategoryIdByName(String name) {
        String query = """
                SELECT id FROM category WHERE name = ?;
                """;
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<Category> getCategories() {
        String query = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        categories.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
        return categories;
    }
    public static void addCategory(HttpServletRequest req){
        String name = req.getParameter("name");
        String sql = "INSERT INTO category (name) VALUES (?);";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            logger.info("category {} added", name);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void deleteCategory(int categoryId) {
        String sql = "DELETE FROM category WHERE id = ?;";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
                ){
            logger.info("category with id {} deleted", categoryId);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
