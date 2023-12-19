/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hasan
 */
public class DatabaseManager {
     private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ooc2023";
    private static final String USERNAME = "ooc2023";
    private static final String PASSWORD = "ooc_2023";

    public static Connection establishConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
            System.out.println("Database connected");
            
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
