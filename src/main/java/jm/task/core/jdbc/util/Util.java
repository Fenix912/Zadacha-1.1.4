package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost/my_user";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "6068259";

    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;

    public static Connection getMySQLConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty(Environment.URL, URL);
            configuration.setProperty(Environment.USER, LOGIN);
            configuration.setProperty(Environment.PASS, PASSWORD);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
