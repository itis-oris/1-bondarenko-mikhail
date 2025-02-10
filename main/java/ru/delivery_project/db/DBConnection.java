package ru.delivery_project.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static HikariDataSource dataSource;

    // Инициализация пула соединений
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/delivery"); // Замените на ваш URL
        config.setUsername("postgres"); // Замените на ваш username
        config.setPassword("new_password"); // Замените на ваш password
        config.setMaximumPoolSize(10); // Максимальное количество соединений в пуле
        config.setMinimumIdle(5); // Минимальное количество простых соединений в пуле

        // Дополнительные настройки
        config.setConnectionTimeout(30000); // Максимальное время ожидания соединения в миллисекундах
        config.setIdleTimeout(600000); // Время, через которое неиспользуемые соединения будут закрываться
        config.setMaxLifetime(1800000); // Максимальная продолжительность жизни соединения (в миллисекундах)

        dataSource = new HikariDataSource(config);
    }

    // Получение соединения из пула
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    // Закрытие пула соединений
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
