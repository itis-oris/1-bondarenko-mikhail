package ru.delivery_project.db.dao;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Product {

    private int id;
    private String name;
    private int category_id;
    private double price;
    private String picture_path;
    private String categoryName;
    public Product(int id, String name, int categoryId, double price, String picture_path, String categoryName) {
        this.id = id;
        this.name = name;
        this.category_id = categoryId;
        this.price = price;
        this.picture_path = picture_path;
        this.categoryName = categoryName;
    }
}
