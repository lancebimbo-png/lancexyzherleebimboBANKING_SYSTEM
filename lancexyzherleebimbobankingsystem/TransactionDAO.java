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
 
public class TransactionDAO {
 
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (account_id, transaction_type, amount) VALUES (?,?,?)";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, transaction.getAccountId());
            ps.setString(2, transaction.getTransactionType());
            ps.setDouble(3, transaction.getAmount());
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.err.println("[TransactionDAO.addTransaction] " + e.getMessage());
        }
        return false;
    }
 
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transaction ORDER BY transaction_date DESC";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) list.add(mapRow(rs));
 
        } catch (SQLException e) {
            System.err.println("[TransactionDAO.getAllTransactions] " + e.getMessage());
        }
        return list;
    }

    public List<Transaction> getTransactionsByAccount(int accountId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_id=? ORDER BY transaction_date DESC";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
 
        } catch (SQLException e) {
            System.err.println("[TransactionDAO.getTransactionsByAccount] " + e.getMessage());
        }
        return list;
    }
 
    public List<Transaction> getFilteredTransactions(int accountId, String type) {
        List<Transaction> list = new ArrayList<>();
 
        boolean filterAccount = accountId > 0;
        boolean filterType    = type != null && !type.equals("All");
 
        StringBuilder sql = new StringBuilder("SELECT * FROM transaction WHERE 1=1 ");
        if (filterAccount) sql.append("AND account_id=? ");
        if (filterType)    sql.append("AND transaction_type=? ");
        sql.append("ORDER BY transaction_date DESC");
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
 
            int idx = 1;
            if (filterAccount) ps.setInt(idx++, accountId);
            if (filterType)    ps.setString(idx, type);
 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
 
        } catch (SQLException e) {
            System.err.println("[TransactionDAO.getFilteredTransactions] " + e.getMessage());
        }
        return list;
    }
 
    private Transaction mapRow(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("transaction_id"),
            rs.getInt("account_id"),
            rs.getString("transaction_type"),
            rs.getDouble("amount"),
            rs.getTimestamp("transaction_date")
        );
    }
}
 