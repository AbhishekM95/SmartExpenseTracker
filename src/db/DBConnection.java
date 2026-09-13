package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST =
            System.getenv("MYSQL_HOST");

    private static final String PORT =
            System.getenv("MYSQL_PORT");

    private static final String DATABASE =
            System.getenv("MYSQL_DATABASE");

    private static final String USER =
            System.getenv("MYSQL_USER");

    private static final String PASSWORD =
            System.getenv("MYSQL_PASSWORD");

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}