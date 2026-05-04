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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
 
public class TransactionHistoryFrame extends JFrame {
 
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final AccountDAO     accountDAO     = new AccountDAO();
 
    private static final String[] COLUMNS = {
        "Txn ID", "Account ID", "Type", "Amount (₱)", "Date & Time"
    };
 
    private JComboBox<String>  cmbFilterAccount;
    private JComboBox<String>  cmbFilterType;
    private JButton            btnFilter, btnReset;
    private JTable             tblHistory;
    private JLabel             lblSummary;
 
    public TransactionHistoryFrame() {
        initComponents();
        loadFilterOptions();
        loadHistory(0, "All"); // load all on startup
    }
 
    private void initComponents() {
        setTitle("Banking System — Transaction History");
        setSize(860, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
 
        // ── NORTH: Title bar ──────────────────────────────────
        JPanel pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setBackground(new Color(26, 60, 100));
        pnlTitle.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel("Transaction Logs / History");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        pnlTitle.add(lblTitle, BorderLayout.WEST);
 
        // ── FILTER BAR ────────────────────────────────────────
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(6, 10, 0, 10),
            BorderFactory.createTitledBorder("Filter Options")));
 
        cmbFilterAccount = new JComboBox<>();
        cmbFilterAccount.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbFilterAccount.setPreferredSize(new Dimension(160, 30));
 
        cmbFilterType = new JComboBox<>(new String[]{"All", "Deposit", "Withdraw"});
        cmbFilterType.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbFilterType.setPreferredSize(new Dimension(130, 30));
 
        btnFilter = makeButton("🔍 Apply Filter", new Color(142, 68, 173));
        btnReset  = makeButton("↺ Show All",      new Color(100, 100, 100));
 
        pnlFilter.add(new JLabel("Account ID:"));
        pnlFilter.add(cmbFilterAccount);
        pnlFilter.add(new JLabel("  Type:"));
        pnlFilter.add(cmbFilterType);
        pnlFilter.add(btnFilter);
        pnlFilter.add(btnReset);
 
        // ── CENTER: Table ─────────────────────────────────────
        tblHistory = new JTable();
        tblHistory.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblHistory.setRowHeight(26);
        tblHistory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblHistory.getTableHeader().setBackground(new Color(26, 60, 100));
        tblHistory.getTableHeader().setForeground(Color.WHITE);
        tblHistory.setDefaultEditor(Object.class, null); // read-only
 
        // Color-code rows: Deposit = light green, Withdraw = light red
        tblHistory.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    String type = (String) table.getValueAt(row, 2);
                    if ("Deposit".equals(type))  c.setBackground(new Color(212, 237, 218));
                    else                          c.setBackground(new Color(248, 215, 218));
                }
                return c;
            }
        });
 
        JScrollPane scroll = new JScrollPane(tblHistory);
 
        // ── SOUTH: Summary ────────────────────────────────────
        lblSummary = new JLabel("  Loading...");
        lblSummary.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSummary.setBorder(new EmptyBorder(6, 10, 8, 10));
 
        JPanel pnlContent = new JPanel(new BorderLayout(0, 6));
        pnlContent.setBorder(new EmptyBorder(0, 10, 10, 10));
        pnlContent.add(pnlFilter, BorderLayout.NORTH);
        pnlContent.add(scroll,    BorderLayout.CENTER);
        pnlContent.add(lblSummary, BorderLayout.SOUTH);
 
        // ── Assemble ──────────────────────────────────────────
        add(pnlTitle,   BorderLayout.NORTH);
        add(pnlContent, BorderLayout.CENTER);
 
        // ── Wire events ───────────────────────────────────────
        btnFilter.addActionListener(e -> applyFilter());
        btnReset.addActionListener(e  -> {
            cmbFilterAccount.setSelectedIndex(0);
            cmbFilterType.setSelectedIndex(0);
            loadHistory(0, "All");
        });
    }
 
    // ─────────────────────────────────────────────────────────
    //  FILTER ACTION
    // ─────────────────────────────────────────────────────────
 
    private void applyFilter() {
        String accountSel = (String) cmbFilterAccount.getSelectedItem();
        String typeSel    = (String) cmbFilterType.getSelectedItem();
 
        int accountId = 0; // 0 = no filter
        if (accountSel != null && !accountSel.equals("All")) {
            accountId = Integer.parseInt(accountSel);
        }
        loadHistory(accountId, typeSel);
    }
 
    // ─────────────────────────────────────────────────────────
    //  DATA
    // ─────────────────────────────────────────────────────────
 
    /** Populate account ID filter combo from DB. */
    private void loadFilterOptions() {
        cmbFilterAccount.addItem("All");
        for (int id : accountDAO.getAllAccountIds()) {
            cmbFilterAccount.addItem(String.valueOf(id));
        }
    }
 
    /**
     * Fetch and display transactions with optional filters.
     * accountId=0 means no account filter; type="All" means no type filter.
     */
    private void loadHistory(int accountId, String type) {
        List<Transaction> txList = transactionDAO.getFilteredTransactions(accountId, type);
 
        DefaultTableModel model = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        double totalDeposit  = 0;
        double totalWithdraw = 0;
 
        for (Transaction tx : txList) {
            model.addRow(new Object[]{
                tx.getTransactionId(),
                tx.getAccountId(),
                tx.getTransactionType(),
                "₱ " + formatMoney(tx.getAmount()),
                tx.getTransactionDate()
            });
            if ("Deposit".equals(tx.getTransactionType()))  totalDeposit  += tx.getAmount();
            else                                             totalWithdraw += tx.getAmount();
        }
 
        tblHistory.setModel(model);
 
        // Adjust column widths
        tblHistory.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblHistory.getColumnModel().getColumn(1).setPreferredWidth(90);
        tblHistory.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblHistory.getColumnModel().getColumn(3).setPreferredWidth(120);
 
        // Summary line
        lblSummary.setText(String.format(
            "  Records: %d   |   Total Deposits: ₱%s   |   Total Withdrawals: ₱%s   |   Net: ₱%s",
            txList.size(),
            formatMoney(totalDeposit),
            formatMoney(totalWithdraw),
            formatMoney(totalDeposit - totalWithdraw)
        ));
    }
 
    // ─────────────────────────────────────────────────────────
    //  UTILITIES
    // ─────────────────────────────────────────────────────────
 
    private String formatMoney(double amount) {
        return NumberFormat.getNumberInstance().format(amount);
    }
 
    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}