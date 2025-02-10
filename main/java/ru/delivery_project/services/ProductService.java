package ru.delivery_project.services;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.delivery_project.db.DBConnection;
import ru.delivery_project.db.dao.Product;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    public static Product getProductById(int id) {

        String query = """
                SELECT p.id, p.category_id, p.name, p.picture_path, p.price, c.name AS category_name
                FROM product p
                LEFT JOIN category c ON p.category_id = c.id
                WHERE p.id = ?;
                """;
        Product product = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int categoryId = resultSet.getInt("category_id");
                String picture_path = resultSet.getString("picture_path");
                String categoryName = resultSet.getString("category_name");
                product = new Product(id, name, categoryId, price, picture_path, categoryName);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return product;
    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String query = """
                SELECT p.id, p.name, p.price, p.category_id, p.picture_path, c.name AS categoryName
                FROM product p
                LEFT JOIN category c ON p.category_id = c.id
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                String categoryName = rs.getString("categoryName");
                String picturePath = rs.getString("picture_path");
                String productName = rs.getString("name");
                int id = rs.getInt("id");
                int categoryId = rs.getInt("category_id");
                double price = rs.getDouble("price");
                Product product = new Product(id, productName, categoryId, price, picturePath, categoryName);
                products.add(product);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return products;
    }

    public static List<Product> getProductsByCategoryId(int categoryId) {
        String query = """
                SELECT p.id, p.name, p.price, p.category_id, p.picture_path, c.name AS categoryName
                FROM product p
                LEFT JOIN category c ON p.category_id = c.id
                WHERE p.category_id = ?;
                """;
        List<Product> products = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("categoryName");
                String picturePath = rs.getString("picture_path");
                String productName = rs.getString("name");
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                Product product = new Product(id, productName, categoryId, price, picturePath, categoryName);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return products;
    }

    public static void sortProducts(List<Product> products, String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "price_asc";
        }
        switch (sortBy) {
            case "price_desc":
                products.sort(new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return Double.compare(o2.getPrice(), o1.getPrice());
                    }
                });
                break;
            case "name_asc":
                products.sort(new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });
                break;
            case "name_desc":
                products.sort(new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return o2.getName().compareToIgnoreCase(o1.getName());
                    }
                });
                break;
            default:
                products.sort(new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return Double.compare(o1.getPrice(), o2.getPrice());
                    }
                });
                break;
        }
    }

    public static void addProduct(HttpServletRequest req) throws ServletException, IOException {
        String productName = req.getParameter("name");
        String categoryIdStr = req.getParameter("categoryId");
        int categoryId = Integer.parseInt(categoryIdStr);
        String priceStr = req.getParameter("price");
        double price = Double.parseDouble(priceStr);
        Part picturePart = req.getPart("picture");
        File picture = new File(req.getServletContext().getRealPath("static/productsImg") + "/" + productName + ".png");
        try (FileOutputStream fos = new FileOutputStream(picture)) {
            fos.write(picturePart.getInputStream().readAllBytes());
        }

        String pathname = "/static/productsImg/" + productName.replace(' ', '_') + ".png";
        String sql = """
                INSERT INTO product (name, category_id, price, picture_path)
                VALUES (?, ?, ?, ?)
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, pathname);
            preparedStatement.executeUpdate();
            logger.info("product {} from category {} with price {} added", productName, categoryId, price);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static void deleteProduct(int productId) {
        CartService.deleteFromAllCarts(productId);
        String sql = """
                DELETE FROM product
                WHERE id = ?
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            logger.info("product with id {} deleted", productId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static List<Product> searchProductsByName(List<Product> products, String name) {
        List<Product> searchedProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }
}
