package ru.delivery_project.services;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.db.DBConnection;
import ru.delivery_project.db.dao.Order;
import ru.delivery_project.db.dao.Product;
import ru.delivery_project.exceptions.NoProductException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final static Logger logger = LogManager.getLogger(OrderService.class);
    public static void createOrder(HttpServletRequest req) throws NoProductException {
        int userId = SecurityService.getUserId(req);
        String[] carts = req.getParameterValues("selectedCarts");
        List<Integer> productIds = new ArrayList<>();
        if (carts != null && carts.length > 0) {
            for (String cart : carts) {
                String productsQuery = """
                            SELECT product_id FROM cart WHERE id=?;
                        """;
                try (
                        Connection connection = DBConnection.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(productsQuery);
                ) {
                    int cartId = Integer.parseInt(cart);
                    preparedStatement.setInt(1, cartId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        productIds.add(resultSet.getInt("product_id"));
                        CartService.removeFromCart(cartId);
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            String query = """
                    
                        INSERT INTO orders (user_id, products_id) VALUES (?, ?);
                    
                    """;
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.
                         prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                Integer[] productIdsArray =
                        productIds.toArray(new Integer[0]);
                preparedStatement.setArray(2, connection.
                        createArrayOf("integer",
                                productIdsArray));
                preparedStatement.executeUpdate();
                logger.info("user {} created order", userId);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            throw new NoProductException("Нет товаров");
        }

    }


    public static List<Order> getOrders(HttpServletRequest req) {
        List<Order> orders = new ArrayList<>();
        int userId = SecurityService.getUserId(req);
        String query = """
                SELECT * FROM orders
                WHERE user_id = ?;
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                LocalDateTime orderDate = resultSet.getTimestamp("created_at").toLocalDateTime();
                java.util.Date date = Date.from(orderDate.atZone(ZoneId.systemDefault()).toInstant());
                String orderStatus = resultSet.getString("status");

                Integer[] productsId = (Integer[]) resultSet.getArray("products_id").getArray();
                List<Product> products = new ArrayList<>();
                for (Integer productId : productsId) {
                    products.add(ProductService.getProductById(productId));
                }
                Order order = new Order(orderId, userId, products, orderStatus, date);
                orders.add(order);

            }
            return orders;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Order> getAllOrders() {
        String query = """
                SELECT * FROM orders;
                """;
        ArrayList<Order> orders = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int userId = resultSet.getInt("user_id");
                String status = resultSet.getString("status");
                LocalDateTime orderDate = resultSet.getTimestamp("created_at").toLocalDateTime();
                java.util.Date date = Date.from(orderDate.atZone(ZoneId.systemDefault()).toInstant());
                Integer[] productsId = (Integer[]) resultSet.getArray("products_id").getArray();
                List<Product> products = new ArrayList<>();
                for (Integer productId : productsId) {
                    products.add(ProductService.getProductById(productId));
                }
                Order order = new Order(orderId, userId, products, status, date);
                orders.add(order);

            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return orders;
    }

    public static void deleteOrder(int orderId) {
        String sql = """
                DELETE FROM orders WHERE order_id = ?;
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
            logger.info("order {} deleted", orderId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void updateOrderStatus(int orderId, String status) {
        String sql = """
                UPDATE orders SET status = ?
                WHERE order_id = ?;
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
            logger.info("order {} updated, new status is {}", orderId, status);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
