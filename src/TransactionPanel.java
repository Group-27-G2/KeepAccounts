import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TransactionPanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final TransactionManager transactionManager;
    private final CategoryManager categoryManager;

    private JTextField dateField;
    private JTextField typeField;
    private JComboBox<String> categoryComboBox;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTable transactionsTable;
    private DefaultTableModel tableModel;

    public TransactionPanel(User user, TransactionManager transactionManager, CategoryManager categoryManager) {
        this.currentUser = user;
        this.transactionManager = transactionManager;
        this.categoryManager = categoryManager;

        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        displayTransactions();
    }

    @Override
    public void refreshData() {
        // Reload categories
        categoryComboBox.removeAllItems();
        for (String category : categoryManager.getCategories()) {
            categoryComboBox.addItem(category);
        }

        // Reload transaction data
        displayTransactions();
    }

    private void initializeComponents() {
        // 1. Input panel
        JPanel inputPanel = createInputPanel();

        // 2. Button panel
        JPanel buttonPanel = createTransactionButtonPanel();

        // 3. Table panel
        JPanel tablePanel = createTablePanel();

        // Assemble the main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Transaction Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(createStyledLabel("Date:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        dateField = new JTextField();
        inputPanel.add(dateField, gbc);

        // Type input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(createStyledLabel("Type (Income/Expense):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        typeField = new JTextField();
        inputPanel.add(typeField, gbc);

        // Category selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        inputPanel.add(createStyledLabel("Category:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        categoryComboBox = new JComboBox<>(categoryManager.getCategories().toArray(new String[0]));
        inputPanel.add(categoryComboBox, gbc);

        // Amount input
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        inputPanel.add(createStyledLabel("Amount:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        amountField = new JTextField();
        inputPanel.add(amountField, gbc);

        // Description input
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        inputPanel.add(createStyledLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        descriptionField = new JTextField();
        inputPanel.add(descriptionField, gbc);

        return inputPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("新罗马", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JPanel createTransactionButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Transaction Operations"));

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton queryButton = new JButton("Query");
        JButton showAllButton = new JButton("Show All");

        // Set button styles
        for (JButton button : new JButton[] { addButton, updateButton, deleteButton, queryButton, showAllButton }) {
            button.setFont(new Font("新罗马", Font.BOLD, 14));
            button.setForeground(Color.BLACK);
            button.setBackground(new Color(255, 255, 255, 150));
            button.setPreferredSize(new Dimension(100, 30));
        }

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(queryButton);
        buttonPanel.add(showAllButton);

        // Add button listeners
        addButton.addActionListener(e -> addTransaction());
        updateButton.addActionListener(e -> updateTransaction());
        deleteButton.addActionListener(e -> deleteTransaction());
        queryButton.addActionListener(e -> queryTransactions());
        showAllButton.addActionListener(e -> showAllTransactions());

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Transaction Record List"));

        // Initialize table model
        String[] columnNames = { "ID", "Date", "Type", "Category", "Amount", "Description" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionsTable = new JTable(tableModel);

        // Set table styles
        Font tableFont = new Font("新罗马", Font.BOLD, 14);
        transactionsTable.setFont(tableFont);
        transactionsTable.setRowHeight(30);
        transactionsTable.setBackground(new Color(255, 255, 255, 180));
        transactionsTable.setForeground(Color.BLACK);

        // Set table header styles
        JTableHeader header = transactionsTable.getTableHeader();
        header.setFont(new Font("新罗马", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.BLACK);

        // Set cell padding
        transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                setOpaque(isSelected);
                setForeground(Color.BLACK);
                setFont(getFont().deriveFont(Font.BOLD));
                return this;
            }
        });

        // Set selected row styles
        transactionsTable.setSelectionBackground(new Color(70, 130, 180, 200));
        transactionsTable.setSelectionForeground(Color.WHITE);

        // Set column widths
        transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        transactionsTable.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void addTransaction() {
        try {
            String id = Utilities.generateUniqueId();
            LocalDate date = Utilities.parseDate(dateField.getText());
            String type = typeField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            Transaction transaction = new Transaction(id, date, type, category, amount, description);
            transactionManager.addTransaction(transaction);
            ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Transaction added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransaction() {
        int selectedRow = transactionsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select the transaction record to modify!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String id = tableModel.getValueAt(selectedRow, 0).toString();
            LocalDate date = Utilities.parseDate(dateField.getText());
            String type = typeField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            Transaction updatedTransaction = new Transaction(id, date, type, category, amount, description);
            transactionManager.updateTransaction(id, updatedTransaction);
            ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
            JOptionPane.showMessageDialog(this, "Transaction modified successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTransaction() {
        int selectedRow = transactionsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select the transaction record to delete!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this transaction?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = tableModel.getValueAt(selectedRow, 0).toString();
            transactionManager.deleteTransaction(id);
            ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
            JOptionPane.showMessageDialog(this, "Transaction deleted successfully!");
        }
    }

    private void queryTransactions() {
        String category = (String) categoryComboBox.getSelectedItem();
        List<Transaction> filteredTransactions = transactionManager.getTransactionsByCategory(category);
        displayTransactions(filteredTransactions);
    }

    private void showAllTransactions() {
        transactionManager.loadTransactionsFromFile();
        displayTransactions();
        clearInputFields();
    }

    private void clearInputFields() {
        dateField.setText("");
        typeField.setText("");
        amountField.setText("");
        descriptionField.setText("");
    }

    private void displayTransactions() {
        displayTransactions(transactionManager.getTransactions());
    }

    private void displayTransactions(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[] {
                    transaction.getId(),
                    transaction.getDate(),
                    transaction.getType(),
                    transaction.getCategory(),
                    transaction.getAmount(),
                    transaction.getDescription()
            });
        }
    }
}