/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

/**
 *
 * @author Lance
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
 
public class TransactionFrame extends JFrame {
 
    private final AccountDAO     accountDAO     = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
 
    private static final String[] TX_COLUMNS = {
        "Txn ID", "Account ID", "Type", "Amount (₱)", "Date & Time"
    };
 
    private JComboBox<Integer> cmbAccount;
    private JTextField         txtAmount;
    private JLabel             lblCurrentBalance;
    private JButton            btnDeposit, btnWithdraw, btnRefreshAccounts;
    private JTable             tblTransactions;
    private JLabel             lblStatus;
 
    public TransactionFrame() {
        initComponents();
        loadAccountIds();
        loadAllTransactions();
    }
 
    private void initComponents() {
        setTitle("Banking System — Transactions");
        setSize(860, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
 
        // ── NORTH: Title ──────────────────────────────────────
        JPanel pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setBackground(new Color(26, 60, 100));
        pnlTitle.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel("Transaction Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        pnlTitle.add(lblTitle, BorderLayout.WEST);
 
        // ── WEST: Transaction form ────────────────────────────
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 10, 10, 0),
            BorderFactory.createTitledBorder("Perform Transaction")));
        pnlForm.setPreferredSize(new Dimension(280, 0));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
 
        cmbAccount = new JComboBox<>();
        cmbAccount.setFont(new Font("Segoe UI", Font.PLAIN, 14));
 
        txtAmount = new JTextField();
        txtAmount.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAmount.setPreferredSize(new Dimension(0, 32));
 
        lblCurrentBalance = new JLabel("Current Balance: —");
        lblCurrentBalance.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCurrentBalance.setForeground(new Color(26, 60, 100));
 
        // Account selector row
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 0;
        pnlForm.add(new JLabel("Select Account:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        pnlForm.add(cmbAccount, gbc);
 
        // Refresh button
        btnRefreshAccounts = new JButton("↻ Refresh");
        btnRefreshAccounts.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefreshAccounts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        pnlForm.add(btnRefreshAccounts, gbc);
 
        // Balance display
        gbc.gridy = 2;
        pnlForm.add(lblCurrentBalance, gbc);
 
        // Separator
        gbc.gridy = 3;
        pnlForm.add(new JSeparator(), gbc);
 
        // Amount row
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.weightx = 0;
        pnlForm.add(new JLabel("Amount (₱):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        pnlForm.add(txtAmount, gbc);
 
        // Deposit / Withdraw buttons
        btnDeposit  = makeButton("⬇ Deposit",  new Color(39, 174, 96));
        btnWithdraw = makeButton("⬆ Withdraw", new Color(192, 57, 43));
 
        JPanel pnlBtns = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBtns.setBorder(new EmptyBorder(10, 0, 0, 0));
        pnlBtns.add(btnDeposit);
        pnlBtns.add(btnWithdraw);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        pnlForm.add(pnlBtns, gbc);
 
        // Quick-tip label
        JLabel lblTip = new JLabel("<html><i>Tip: Select an account then<br>enter an amount to transact.</i></html>");
        lblTip.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblTip.setForeground(Color.GRAY);
        gbc.gridy = 6;
        pnlForm.add(lblTip, gbc);
 
        // ── CENTER: Transaction history table ─────────────────
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 8));
        pnlCenter.setBorder(new EmptyBorder(10, 0, 10, 10));
 
        JLabel lblTableTitle = new JLabel("  Recent Transactions");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
 
        tblTransactions = new JTable();
        tblTransactions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblTransactions.setRowHeight(26);
        tblTransactions.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblTransactions.getTableHeader().setBackground(new Color(26, 60, 100));
        tblTransactions.getTableHeader().setForeground(Color.WHITE);
        tblTransactions.setDefaultEditor(Object.class, null); // not editable
 
        lblStatus = new JLabel(" Ready");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(Color.GRAY);
 
        pnlCenter.add(lblTableTitle,           BorderLayout.NORTH);
        pnlCenter.add(new JScrollPane(tblTransactions), BorderLayout.CENTER);
        pnlCenter.add(lblStatus,               BorderLayout.SOUTH);
 
        // ── Assemble ──────────────────────────────────────────
        add(pnlTitle,  BorderLayout.NORTH);
        add(pnlForm,   BorderLayout.WEST);
        add(pnlCenter, BorderLayout.CENTER);
 
        // ── Wire events ───────────────────────────────────────
        btnDeposit.addActionListener(e  -> btnDepositActionPerformed());
        btnWithdraw.addActionListener(e -> btnWithdrawActionPerformed());
        btnRefreshAccounts.addActionListener(e -> loadAccountIds());
 
        // Update balance display whenever user picks a different account
        cmbAccount.addActionListener(e -> updateBalanceDisplay());
    }
 
    // ─────────────────────────────────────────────────────────
    //  BUTTON ACTIONS
    // ─────────────────────────────────────────────────────────
 
    /** DEPOSIT — Add money to the selected account. */
    private void btnDepositActionPerformed() {
        if (cmbAccount.getSelectedItem() == null) {
            showError("No accounts available. Please create an account first.");
            return;
        }
        double amount = parseAmount();
        if (amount <= 0) return;
 
        int accountId = (Integer) cmbAccount.getSelectedItem();
 
        // 1. Get current balance
        double currentBalance = accountDAO.getBalance(accountId);
 
        // 2. Update balance in account table
        accountDAO.updateBalance(accountId, currentBalance + amount);
 
        // 3. Record the transaction
        transactionDAO.addTransaction(new Transaction(accountId, "Deposit", amount));
 
        showSuccess("Deposited ₱" + formatMoney(amount) + " to Account #" + accountId);
        txtAmount.setText("");
        updateBalanceDisplay();
        loadAllTransactions();
    }
 
    /** WITHDRAW — Deduct money from the selected account. */
    private void btnWithdrawActionPerformed() {
        if (cmbAccount.getSelectedItem() == null) {
            showError("No accounts available. Please create an account first.");
            return;
        }
        double amount = parseAmount();
        if (amount <= 0) return;
 
        int    accountId      = (Integer) cmbAccount.getSelectedItem();
        double currentBalance = accountDAO.getBalance(accountId);
 
        // ✔ Rule: prevent withdrawal if balance is insufficient
        if (amount > currentBalance) {
            showError("Insufficient balance!\n" +
                      "Current balance: ₱" + formatMoney(currentBalance) + "\n" +
                      "Requested:       ₱" + formatMoney(amount));
            return;
        }
 
        // 1. Deduct balance
        accountDAO.updateBalance(accountId, currentBalance - amount);
 
        // 2. Record the transaction
        transactionDAO.addTransaction(new Transaction(accountId, "Withdraw", amount));
 
        showSuccess("Withdrew ₱" + formatMoney(amount) + " from Account #" + accountId);
        txtAmount.setText("");
        updateBalanceDisplay();
        loadAllTransactions();
    }
 
    // ─────────────────────────────────────────────────────────
    //  DATA HELPERS
    // ─────────────────────────────────────────────────────────
 
    /** Populate the JComboBox with account IDs from the database. */
    private void loadAccountIds() {
        cmbAccount.removeAllItems();
        for (int id : accountDAO.getAllAccountIds()) {
            cmbAccount.addItem(id);
        }
        updateBalanceDisplay();
    }
 
    /** Show the live balance next to the account selector. */
    private void updateBalanceDisplay() {
        if (cmbAccount.getSelectedItem() == null) {
            lblCurrentBalance.setText("Current Balance: —");
            return;
        }
        int    accountId = (Integer) cmbAccount.getSelectedItem();
        double balance   = accountDAO.getBalance(accountId);
        lblCurrentBalance.setText("Current Balance: ₱" + formatMoney(balance));
        lblCurrentBalance.setForeground(balance > 0 ? new Color(26, 60, 100) : new Color(192, 57, 43));
    }
 
    /** Load all transactions into the history table. */
    private void loadAllTransactions() {
        List<Transaction> txList = transactionDAO.getAllTransactions();
        DefaultTableModel model  = new DefaultTableModel(TX_COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Transaction tx : txList) {
            model.addRow(new Object[]{
                tx.getTransactionId(),
                tx.getAccountId(),
                tx.getTransactionType(),
                "₱ " + formatMoney(tx.getAmount()),
                tx.getTransactionDate()
            });
        }
        tblTransactions.setModel(model);
        lblStatus.setText(" Showing " + txList.size() + " transaction(s)");
    }
 
    // ─────────────────────────────────────────────────────────
    //  UTILITIES
    // ─────────────────────────────────────────────────────────
 
    /** Parse and validate the amount field. Returns -1 on invalid input. */
    private double parseAmount() {
        try {
            double amount = Double.parseDouble(txtAmount.getText().trim());
            if (amount <= 0) {
                showError("Amount must be greater than zero.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException ex) {
            showError("Please enter a valid amount (numbers only).");
            return -1;
        }
    }
 
    private String formatMoney(double amount) {
        return NumberFormat.getNumberInstance().format(amount);
    }
 
    private void showSuccess(String msg) {
        lblStatus.setText(" ✔ " + msg);
        lblStatus.setForeground(new Color(39, 174, 96));
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void showError(String msg) {
        lblStatus.setText(" ✘ Error");
        lblStatus.setForeground(new Color(192, 57, 43));
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 40));
        return btn;
    }
}