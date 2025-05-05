package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://pg-232ae497-projeto-1-ban-2.k.aivencloud.com:26812/defaultdb";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_-RBKoyNX1rJeVpMdxI2";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados PostgreSQL", e);
        }
    }
}
