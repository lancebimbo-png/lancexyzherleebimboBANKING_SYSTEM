/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 

public class DatabaseConnection {
 
    private static final String URL      = "jdbc:mysql://localhost:3306/banking_system?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";       // ← your MySQL username
    private static final String PASSWORD = "@Goodkidz123"; // ← your MySQL password
 
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("[DB] MySQL Driver not found. Add mysql-connector-j.jar to your libraries.");
        } catch (SQLException e) {
            System.err.println("[DB] Connection failed: " + e.getMessage());
        }
        return null;
    }
}