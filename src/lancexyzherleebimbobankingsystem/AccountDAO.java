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
 
public class AccountDAO {

    public int addAccount(Account account) {
        String sql = "INSERT INTO account (customer_id, account_type, balance) VALUES (?,?,?)";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountType());
            ps.setDouble(3, account.getBalance());
            ps.executeUpdate();
 
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
 
        } catch (SQLException e) {
            System.err.println("[AccountDAO.addAccount] " + e.getMessage());
        }
        return -1;
    }

    public List<Object[]> getAllAccountsWithCustomer() {
        List<Object[]> list = new ArrayList<>();
        String sql =
            "SELECT a.account_id, c.customer_id, c.first_name, c.last_name, " +
            "       c.email, c.phone_number, a.account_type, a.balance " +
            "FROM account a " +
            "JOIN customer c ON a.customer_id = c.customer_id " +
            "ORDER BY a.account_id";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("account_id"),
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("account_type"),
                    rs.getDouble("balance")
                });
            }
        } catch (SQLException e) {
            System.err.println("[AccountDAO.getAllAccountsWithCustomer] " + e.getMessage());
        }
        return list;
    }

    public List<Object[]> searchAccounts(String keyword) {
        List<Object[]> list = new ArrayList<>();
        String sql =
            "SELECT a.account_id, c.customer_id, c.first_name, c.last_name, " +
            "       c.email, c.phone_number, a.account_type, a.balance " +
            "FROM account a " +
            "JOIN customer c ON a.customer_id = c.customer_id " +
            "WHERE c.first_name LIKE ? OR c.last_name LIKE ? OR CAST(a.account_id AS CHAR) = ? " +
            "ORDER BY a.account_id";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, keyword);
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("account_id"),
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("account_type"),
                    rs.getDouble("balance")
                });
            }
        } catch (SQLException e) {
            System.err.println("[AccountDAO.searchAccounts] " + e.getMessage());
        }
        return list;
    }

    public List<Integer> getAllAccountIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT account_id FROM account ORDER BY account_id";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) ids.add(rs.getInt("account_id"));
 
        } catch (SQLException e) {
            System.err.println("[AccountDAO.getAllAccountIds] " + e.getMessage());
        }
        return ids;
    }
 

    public double getBalance(int accountId) {
        String sql = "SELECT balance FROM account WHERE account_id = ?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
 
        } catch (SQLException e) {
            System.err.println("[AccountDAO.getBalance] " + e.getMessage());
        }
        return -1.0;
    }
 
    public boolean updateAccount(Account account) {
        String sql = "UPDATE account SET account_type=?, balance=? WHERE account_id=?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, account.getAccountType());
            ps.setDouble(2, account.getBalance());
            ps.setInt(3, account.getAccountId());
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.err.println("[AccountDAO.updateAccount] " + e.getMessage());
        }
        return false;
    }
 

    public boolean updateBalance(int accountId, double newBalance) {
        String sql = "UPDATE account SET balance=? WHERE account_id=?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.err.println("[AccountDAO.updateBalance] " + e.getMessage());
        }
        return false;
    }
 
    public boolean deleteAccount(int accountId) {
        String sql = "DELETE FROM account WHERE account_id=?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, accountId);
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.err.println("[AccountDAO.deleteAccount] " + e.getMessage());
        }
        return false;
    }
}