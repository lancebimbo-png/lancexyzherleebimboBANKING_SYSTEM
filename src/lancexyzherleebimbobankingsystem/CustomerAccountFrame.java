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
import java.util.List;
 
public class CustomerAccountFrame extends JFrame {
 
    // ── DAO instances ─────────────────────────────────────────
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO  accountDAO  = new AccountDAO();
 
    // ── Column names for the table ────────────────────────────
    private static final String[] COLUMNS = {
        "Account ID", "Customer ID", "First Name", "Last Name",
        "Email", "Phone", "Type", "Balance (₱)"
    };
 
    private JTextField  txtFirstName, txtLastName, txtEmail, txtPhone, txtBalance, txtSearch;
    private JComboBox<String> cmbAccountType;
    private JButton     btnAdd, btnUpdate, btnDelete, btnClear, btnSearch, btnOpenTransactions, btnOpenHistory;
    private JTable      tblAccounts;
    private JScrollPane scrollPane;
    private JLabel      lblStatus;
 
    private int selectedCustomerId = -1;
    private int selectedAccountId  = -1;
 
    public CustomerAccountFrame() {
        initComponents();
        styleComponents();
        loadAccounts();                    // Populate table on startup
        attachRowClickListener();          // Auto-fill fields when row clicked
    }
 
    private void initComponents() {
        setTitle("Banking System — Customer & Account Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
 
        JPanel pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setBackground(new Color(26, 60, 100));
        pnlTitle.setBorder(new EmptyBorder(15, 20, 15, 20));
 
        JLabel lblTitle = new JLabel("Customer & Account Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
 
        JPanel pnlNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pnlNav.setOpaque(false);
        btnOpenTransactions = makeNavButton("Transactions");
        btnOpenHistory      = makeNavButton("History");
        pnlNav.add(btnOpenTransactions);
        pnlNav.add(btnOpenHistory);
 
        pnlTitle.add(lblTitle, BorderLayout.WEST);
        pnlTitle.add(pnlNav,   BorderLayout.EAST);
 
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 0, 10, 10),
            BorderFactory.createTitledBorder("Account Details")));
        pnlForm.setPreferredSize(new Dimension(280, 0));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
 
        txtFirstName   = new JTextField();
        txtLastName    = new JTextField();
        txtEmail       = new JTextField();
        txtPhone       = new JTextField();
        txtBalance     = new JTextField();
        cmbAccountType = new JComboBox<>(new String[]{"Savings", "Current"});
 
        addFormRow(pnlForm, gbc, 0, "First Name:",   txtFirstName);
        addFormRow(pnlForm, gbc, 1, "Last Name:",    txtLastName);
        addFormRow(pnlForm, gbc, 2, "Email:",        txtEmail);
        addFormRow(pnlForm, gbc, 3, "Phone:",        txtPhone);
        addFormRow(pnlForm, gbc, 4, "Account Type:", cmbAccountType);
        addFormRow(pnlForm, gbc, 5, "Balance (₱):",  txtBalance);
 
        // ── Buttons ───────────────────────────────────────────
        btnAdd    = makeActionButton("Add",    new Color(39, 174, 96));
        btnUpdate = makeActionButton("Update", new Color(41, 128, 185));
        btnDelete = makeActionButton("Delete", new Color(192, 57, 43));
        btnClear  = makeActionButton("Clear",  new Color(127, 140, 141));
 
        JPanel pnlButtons = new JPanel(new GridLayout(2, 2, 8, 8));
        pnlButtons.setBorder(new EmptyBorder(10, 8, 8, 8));
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);
 
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        pnlForm.add(pnlButtons, gbc);
 
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 8));
        pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 0));
 
        // Search bar
        JPanel pnlSearch = new JPanel(new BorderLayout(6, 0));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Search"));
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch = makeActionButton("🔍 Search", new Color(142, 68, 173));
        JButton btnShowAll = makeActionButton("Show All", new Color(100, 100, 100));
        JPanel pnlSearchBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        pnlSearchBtns.add(btnSearch);
        pnlSearchBtns.add(btnShowAll);
        pnlSearch.add(new JLabel(" Search by Name or Account ID: "), BorderLayout.WEST);
        pnlSearch.add(txtSearch,     BorderLayout.CENTER);
        pnlSearch.add(pnlSearchBtns, BorderLayout.EAST);
 
        tblAccounts = new JTable();
        tblAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAccounts.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblAccounts.setRowHeight(26);
        tblAccounts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblAccounts.getTableHeader().setBackground(new Color(26, 60, 100));
        tblAccounts.getTableHeader().setForeground(Color.WHITE);
        scrollPane = new JScrollPane(tblAccounts);
 
        lblStatus = new JLabel(" Ready");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(new Color(100, 100, 100));
 
        pnlCenter.add(pnlSearch, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        pnlCenter.add(lblStatus,  BorderLayout.SOUTH);
 
        add(pnlTitle,  BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlForm,   BorderLayout.EAST);
 
        btnAdd.addActionListener(e -> btnAddActionPerformed());
        btnUpdate.addActionListener(e -> btnUpdateActionPerformed());
        btnDelete.addActionListener(e -> btnDeleteActionPerformed());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> btnSearchActionPerformed());
        btnShowAll.addActionListener(e -> loadAccounts());
        btnOpenTransactions.addActionListener(e -> openTransactionWindow());
        btnOpenHistory.addActionListener(e -> openHistoryWindow());
 
        txtSearch.addActionListener(e -> btnSearchActionPerformed());
    }
 
    private void btnAddActionPerformed() {
        if (!validateForm()) return;
 
        Customer customer = new Customer(
            txtFirstName.getText().trim(),
            txtLastName.getText().trim(),
            txtEmail.getText().trim(),
            txtPhone.getText().trim()
        );
 
        int customerId = customerDAO.addCustomer(customer);
        if (customerId == -1) {
            showError("Failed to add customer. Email may already exist.");
            return;
        }
 
        Account account = new Account(
            customerId,
            cmbAccountType.getSelectedItem().toString(),
            Double.parseDouble(txtBalance.getText().trim())
        );
 
        int accountId = accountDAO.addAccount(account);
        if (accountId == -1) {
            showError("Customer saved but account creation failed.");
            return;
        }
 
        showSuccess("Account #" + accountId + " created for " + customer.getFirstName() + "!");
        clearForm();
        loadAccounts();
    }

    private void btnUpdateActionPerformed() {
        if (selectedAccountId == -1) {
            showError("Please select a row in the table to update.");
            return;
        }
        if (!validateForm()) return;
 
        Customer customer = new Customer(
            selectedCustomerId,
            txtFirstName.getText().trim(),
            txtLastName.getText().trim(),
            txtEmail.getText().trim(),
            txtPhone.getText().trim()
        );
        customerDAO.updateCustomer(customer);
 
        Account account = new Account(
            selectedAccountId,
            selectedCustomerId,
            cmbAccountType.getSelectedItem().toString(),
            Double.parseDouble(txtBalance.getText().trim())
        );
        boolean ok = accountDAO.updateAccount(account);
 
        if (ok) {
            showSuccess("Account #" + selectedAccountId + " updated successfully.");
            clearForm();
            loadAccounts();
        } else {
            showError("Update failed. Please try again.");
        }
    }
 
    private void btnDeleteActionPerformed() {
        if (selectedAccountId == -1) {
            showError("Please select a row in the table to delete.");
            return;
        }
 
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete Account #" + selectedAccountId + "?\nAll transactions for this account will also be removed.",
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
 
        if (confirm != JOptionPane.YES_OPTION) return;
 
        boolean ok = accountDAO.deleteAccount(selectedAccountId);
        if (ok) {
            showSuccess("Account #" + selectedAccountId + " deleted.");
            clearForm();
            loadAccounts();
        } else {
            showError("Delete failed. Please try again.");
        }
    }
 
    private void btnSearchActionPerformed() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadAccounts();
            return;
        }
 
        List<Object[]> results = accountDAO.searchAccounts(keyword);
        populateTable(results);
        lblStatus.setText(" Found " + results.size() + " result(s) for: \"" + keyword + "\"");
    }
 
    private void loadAccounts() {
        List<Object[]> data = accountDAO.getAllAccountsWithCustomer();
        populateTable(data);
        lblStatus.setText(" Total accounts: " + data.size());
    }
 
    private void populateTable(List<Object[]> data) {
        DefaultTableModel model = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        for (Object[] row : data) model.addRow(row);
        tblAccounts.setModel(model);
 
        tblAccounts.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblAccounts.getColumnModel().getColumn(1).setPreferredWidth(90);
    }
 
    private void attachRowClickListener() {
        tblAccounts.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblAccounts.getSelectedRow();
            if (row == -1) return;
 
            selectedAccountId  = (int) tblAccounts.getValueAt(row, 0);
            selectedCustomerId = (int) tblAccounts.getValueAt(row, 1);
 
            txtFirstName.setText((String) tblAccounts.getValueAt(row, 2));
            txtLastName.setText((String) tblAccounts.getValueAt(row, 3));
            txtEmail.setText((String) tblAccounts.getValueAt(row, 4));
            txtPhone.setText((String) tblAccounts.getValueAt(row, 5));
            cmbAccountType.setSelectedItem(tblAccounts.getValueAt(row, 6));
            txtBalance.setText(String.valueOf(tblAccounts.getValueAt(row, 7)));
 
            lblStatus.setText(" Selected: Account #" + selectedAccountId);
        });
    }
    
    private void openTransactionWindow() {
        TransactionFrame tf = new TransactionFrame();
        tf.setVisible(true);
    }
 
    private void openHistoryWindow() {
        TransactionHistoryFrame hf = new TransactionHistoryFrame();
        hf.setVisible(true);
    }

    private boolean validateForm() {
        if (txtFirstName.getText().trim().isEmpty() ||
            txtLastName.getText().trim().isEmpty()  ||
            txtEmail.getText().trim().isEmpty()     ||
            txtPhone.getText().trim().isEmpty()     ||
            txtBalance.getText().trim().isEmpty()) {
            showError("All fields are required.");
            return false;
        }
        try {
            double bal = Double.parseDouble(txtBalance.getText().trim());
            if (bal < 0) { showError("Balance cannot be negative."); return false; }
        } catch (NumberFormatException ex) {
            showError("Balance must be a valid number.");
            return false;
        }
        return true;
    }
 
    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtBalance.setText("");
        txtSearch.setText("");
        cmbAccountType.setSelectedIndex(0);
        selectedAccountId  = -1;
        selectedCustomerId = -1;
        tblAccounts.clearSelection();
        lblStatus.setText(" Ready");
    }
 
    private void showSuccess(String msg) {
        lblStatus.setText(" ✔ " + msg);
        lblStatus.setForeground(new Color(39, 174, 96));
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void showError(String msg) {
        lblStatus.setText(" ✘ " + msg);
        lblStatus.setForeground(new Color(192, 57, 43));
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void styleComponents() {
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        for (JTextField f : new JTextField[]{txtFirstName, txtLastName, txtEmail, txtPhone, txtBalance, txtSearch}) {
            f.setFont(fieldFont);
            f.setPreferredSize(new Dimension(0, 32));
        }
        cmbAccountType.setFont(fieldFont);
        cmbAccountType.setPreferredSize(new Dimension(0, 32));
    }
 
    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(field, gbc);
    }
 
    private JButton makeActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JButton makeNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(255, 255, 255, 40));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}