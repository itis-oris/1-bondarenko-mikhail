package ru.delivery_project.db.dao;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private int id;
    private String firstName;
    private String secondName;
    private String email;
    private String telephone;
    private String role;
    public User(int id, String firstName, String secondName, String email, String telephone, String role) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
    }
}
