package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database { 
    private static final String URL = "jdbc:postgresql://localhost:5432/charity_platform";  
private static final String USER = "jaretushar";  
private static final String PASSWORD = "Tushar@123";


    private Database() {
        // Private constructor to prevent instantiation
    }

    // ✅ Always return a new connection to prevent "Connection has been closed" issues
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found!", e);
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
        + "id SERIAL PRIMARY KEY, "
        + "name VARCHAR(100) NOT NULL, "
        + "mobile_number VARCHAR(15) UNIQUE NOT NULL, "
        + "email VARCHAR(100) UNIQUE NOT NULL, "
        + "address TEXT NOT NULL, "
        + "gender VARCHAR(10) NOT NULL, "
        + "problem VARCHAR(100) NOT NULL, "
        + "description TEXT NOT NULL, "
        + "hashed_password TEXT NOT NULL, "
        + "profile_pic_path TEXT, "   // Store file path instead of BYTEA
        + "upi_qr_path TEXT"          // Store file path instead of BYTEA
        + ");";
    
        try (Connection conn = getConnection();  // ✅ Get a new connection
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ Table 'users' created successfully (if not exists).");
        } catch (SQLException e) {
            System.out.println("❌ Error creating table: " + e.getMessage());
        }
    }

    // ✅ Test database connection
    public static void testConnection() {
        System.out.println("🔄 Testing Database Connection...");
        try (Connection conn = getConnection()) {  // ✅ New connection every time
            if (conn != null) {
                System.out.println("✅ Database Connection is OK!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testConnection();  // Test connection when running Database.java
    }
}
