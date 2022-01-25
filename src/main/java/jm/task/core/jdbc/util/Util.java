package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.hibernate.cfg.AvailableSettings.DRIVER;


public class Util {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/test1";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "6068259";

    private static SessionFactory sessionFactory = null;
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(HOST, LOGIN, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Не удалось загрузить класс драйвера!");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty(Environment.URL, HOST);
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
