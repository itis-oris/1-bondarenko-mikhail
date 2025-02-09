package ru.delivery_project.services;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import ru.delivery_project.db.DBConnection;

@WebListener
public class AppContextListener implements ServletContextListener {
    final static Logger logger = LogManager.getLogger(AppContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        logger.info("start migration config");
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/delivery", "postgres", "new_password")
                .load();
        logger.info("start migration");
        flyway.migrate();
        logger.info("migration done");


        sce.getServletContext().setAttribute("dbConnection", DBConnection.getDataSource());

        System.out.println("Database connection established");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.close();
        System.out.println("Database connection closed");
    }
}
