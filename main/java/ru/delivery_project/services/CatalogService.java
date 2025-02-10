package ru.delivery_project.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.delivery_project.db.dao.Catalog;
import ru.delivery_project.db.dao.Category;
import ru.delivery_project.db.dao.Product;

import java.util.List;

public class CatalogService {
    public static Catalog getCatalog(HttpServletRequest req){
        String sortBy = req.getParameter("sort");
        String category = req.getParameter("category");
        String search = req.getParameter("name");
        Integer categoryId = CategoryService.getCategoryIdByName(category);
        List<Category> categories = CategoryService.getCategories();
        Catalog catalog = new Catalog();
        catalog.setCategories(categories);
        List<Product> products;
        if (categoryId == null) {
            products = ProductService.getProducts();
        } else {
            products = ProductService.getProductsByCategoryId(categoryId);
        }
        if (search != null) {
            products = ProductService.searchProductsByName(products, search);
        }
        ProductService.sortProducts(products, sortBy);
        catalog.setProducts(products);
        return catalog;
    }
}
