package ru.delivery_project.db.dao;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Category {
    private int id;
    private String name;
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
