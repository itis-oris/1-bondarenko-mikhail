package ru.delivery_project.db.dao;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductInCart extends Product{
    private int cartId;
    public ProductInCart(int id, String name, int categoryId, double price, String picture_path, String categoryName, int cartId) {
        super(id, name, categoryId, price, picture_path, categoryName);
        this.cartId = cartId;
    }
}
