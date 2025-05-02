package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://pg-2e685370-manutoledo0402-e9b9.b.aivencloud.com:11098/defaultdb?ssl=require&user=avnadmin&password=AVNS_VtzPp6wlGBiHEj5O2F0";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_VtzPp6wlGBiHEj5O2F0";

//    private static final String URL = "jdbc:postgresql://projeto-lanchonete-projeto1-ban2.d.aivencloud.com:12432/defaultdb?ssl=require&user=avnadmin&password=AVNS_0rmWKBq9_iR3kSlaSe8";
//    private static final String USER = "avnadmin";
//    private static final String PASSWORD = "AVNS_0rmWKBq9_iR3kSlaSe8";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados PostgreSQL", e);
        }
    }
}
