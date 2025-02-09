package ru.delivery_project.services;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.db.DBConnection;
import ru.delivery_project.db.dao.ProductInCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final static Logger logger = LogManager.getLogger(CartService.class);
    public static void addToCart(HttpServletRequest req) {
        String productId = req.getParameter("productId");
        int userId = SecurityService.getUserId(req);
        String query = """
                INSERT INTO cart (product_id, user_id) VALUES(?, ?);
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(productId));
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            logger.info("user {} added to cart {}", userId, productId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static void removeFromCart(int cartId) {
        String query = """
                DELETE FROM cart WHERE id = ?;
                """;
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.executeUpdate();
            logger.info("removed cart {}", cartId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<ProductInCart> getCart(HttpServletRequest req){

        int userId = SecurityService.getUserId(req);
        List<ProductInCart> cart = new ArrayList<>();

        String query = "SELECT product_id, id FROM cart WHERE user_id = ?;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int cartId = rs.getInt("id");
                int productId = rs.getInt("product_id");
                String productQuery = """
                        SELECT p.id, p.name, c.id AS category_id, p.price, p.picture_path, c.name AS category_name
                        FROM product p
                        LEFT JOIN category c ON p.category_id = c.id
                        WHERE p.id = ?;
                        """;
                try (PreparedStatement productstmnt = conn.prepareStatement(productQuery)) {
                    productstmnt.setInt(1, productId);
                    ResultSet rsProducts = productstmnt.executeQuery();
                    while (rsProducts.next()) {

                        String name = rsProducts.getString("name");
                        int categoryId = rsProducts.getInt("category_id");
                        int price = rsProducts.getInt("price");
                        String picturePath = rsProducts.getString("picture_path");
                        String categoryName = rsProducts.getString("category_name");
                        ProductInCart product = new ProductInCart(productId, name, categoryId, price, picturePath, categoryName, cartId);
                        cart.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return cart.reversed();
    }
    public static void deleteFromAllCarts(int productId) {
        String sql = """
                DELETE FROM cart
                WHERE product_id = ?
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            logger.info("product {} deleted from all carts", productId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
