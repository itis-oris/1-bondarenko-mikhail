package ru.delivery_project.db.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class Catalog {
    private List<Category> categories;
    private List<Product> products;
    public Catalog() {}
    public Catalog(List<Category> categories, List<Product> products) {
        this.categories = categories;
        this.products = products;
    }
}
