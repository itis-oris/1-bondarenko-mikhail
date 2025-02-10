package ru.delivery_project.db.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Getter @Setter
public class Order {
    private int order_id;
    private int user_id;
    private List<Product> products;
    private String status;
    private Date created_at;
    private double total_price;

    public Order(int order_id, int user_id, List<Product> products, String status, Date created_at) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.products = products;
        this.status = status;
        this.created_at = created_at;
        double total_price = 0;
        for (Product product : products) {
            total_price += product.getPrice();
        }
        this.total_price = total_price;
    }
}

