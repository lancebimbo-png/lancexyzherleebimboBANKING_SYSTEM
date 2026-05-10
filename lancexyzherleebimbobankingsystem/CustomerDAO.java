/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

/**
 *
 * @author Lance
 */
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 

public class CustomerDAO {

    public int addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, email, phone_number) VALUES (?,?,?,?)";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.executeUpdate();
 
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
 
        } catch (SQLException e) {
            System.err.println("[CustomerDAO.addCustomer] " + e.getMessage());
        }
        return -1;
    }
 
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY last_name, first_name";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[CustomerDAO.getAllCustomers] " + e.getMessage());
        }
        return list;
    }
 

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
 
        } catch (SQLException e) {
            System.err.println("[CustomerDAO.getCustomerById] " + e.getMessage());
        }
        return null;
    }
 
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET first_name=?, last_name=?, email=?, phone_number=? WHERE customer_id=?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5, customer.getCustomerId());
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.err.println("[CustomerDAO.updateCustomer] " + e.getMessage());
        }
        return false;
    }
 
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_id=?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, customerId);
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.err.println("[CustomerDAO.deleteCustomer] " + e.getMessage());
        }
        return false;
    }
 
    private Customer mapRow(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("customer_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("phone_number")
        );
    }
}