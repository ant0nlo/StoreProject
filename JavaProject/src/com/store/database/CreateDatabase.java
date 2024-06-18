package com.store.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Register SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");

            
            // Create table
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS MyTable (id INTEGER PRIMARY KEY, name TEXT)";
            statement.executeUpdate(sql);
            System.out.println("Table created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
