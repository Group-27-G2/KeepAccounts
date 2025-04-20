import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsPanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final TransactionManager transactionManager;
    private final UserManager userManager;
    private JTextArea statsArea;
    private JComboBox<Integer> startYearCombo, endYearCombo;
    private JComboBox<Integer> startMonthCombo, endMonthCombo;
    private JComboBox<Integer> startDayCombo, endDayCombo;

    public StatisticsPanel(User user, TransactionManager transactionManager, UserManager userManager) {
        this.currentUser = user;
        this.transactionManager = transactionManager;
        this.userManager = userManager;

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initializeComponents();
        refreshData();
    }

    @Override
    public void refreshData() {
        updateStatistics(null, null);
    }

    private void initializeComponents() {
        // Create date range selection panel
        JPanel dateRangePanel = createDateRangePanel();

        // Create statistics display area
        statsArea = new JTextArea();
        statsArea.setFont(new Font("微软雅黑", Font.BOLD, 14));
        statsArea.setForeground(Color.BLACK);
        statsArea.setEditable(false);
        statsArea.setBackground(Color.WHITE);
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(statsArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Statistics"));

        // Create query button
        JButton queryButton = new JButton("Query Statistics");
        queryButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        queryButton.setForeground(Color.BLACK);
        queryButton.setBackground(new Color(255, 255, 255, 200));
        queryButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        queryButton.addActionListener(e -> queryStatistics());

        // Main layout
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(dateRangePanel, BorderLayout.CENTER);
        topPanel.add(queryButton, BorderLayout.EAST);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createDateRangePanel() {
        JPanel dateRangePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        dateRangePanel.setOpaque(false);
        dateRangePanel.setBorder(BorderFactory.createTitledBorder("Select Date Range"));

        // Start date panel
        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        startDatePanel.setOpaque(false);
        startDatePanel.add(new JLabel("Start Date:"));

        startYearCombo = createYearComboBox();
        startMonthCombo = createMonthComboBox();
        startDayCombo = createDayComboBox((int) startYearCombo.getSelectedItem(),
                (int) startMonthCombo.getSelectedItem());

        startDatePanel.add(startYearCombo);
        startDatePanel.add(new JLabel("Year"));
        startDatePanel.add(startMonthCombo);
        startDatePanel.add(new JLabel("Month"));
        startDatePanel.add(startDayCombo);
        startDatePanel.add(new JLabel("Day"));

        // End date panel
        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        endDatePanel.setOpaque(false);
        endDatePanel.add(new JLabel("End Date:"));

        endYearCombo = createYearComboBox();
        endMonthCombo = createMonthComboBox();
        endDayCombo = createDayComboBox((int) endYearCombo.getSelectedItem(),
                (int) endMonthCombo.getSelectedItem());

        endDatePanel.add(endYearCombo);
        endDatePanel.add(new JLabel("Year"));
        endDatePanel.add(endMonthCombo);
        endDatePanel.add(new JLabel("Month"));
        endDatePanel.add(endDayCombo);
        endDatePanel.add(new JLabel("Day"));

        // Add date linkage listeners
        startYearCombo.addActionListener(e -> updateStartDayComboBox());
        startMonthCombo.addActionListener(e -> updateStartDayComboBox());
        endYearCombo.addActionListener(e -> updateEndDayComboBox());
        endMonthCombo.addActionListener(e -> updateEndDayComboBox());

        dateRangePanel.add(startDatePanel);
        dateRangePanel.add(endDatePanel);

        return dateRangePanel;
    }

    private JComboBox<Integer> createYearComboBox() {
        JComboBox<Integer> combo = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear - 10; year <= currentYear; year++) {
            combo.addItem(year);
        }
        combo.setSelectedItem(currentYear);
        return combo;
    }

    private JComboBox<Integer> createMonthComboBox() {
        JComboBox<Integer> combo = new JComboBox<>();
        for (int month = 1; month <= 12; month++) {
            combo.addItem(month);
        }
        combo.setSelectedItem(LocalDate.now().getMonthValue());
        return combo;
    }

    private JComboBox<Integer> createDayComboBox(int year, int month) {
        JComboBox<Integer> combo = new JComboBox<>();
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        for (int day = 1; day <= maxDay; day++) {
            combo.addItem(day);
        }
        combo.setSelectedItem(LocalDate.now().getDayOfMonth());
        return combo;
    }

    private void updateStartDayComboBox() {
        int year = (int) startYearCombo.getSelectedItem();
        int month = (int) startMonthCombo.getSelectedItem();
        startDayCombo.setModel(new DefaultComboBoxModel<>());
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        for (int day = 1; day <= maxDay; day++) {
            startDayCombo.addItem(day);
        }
    }

    private void updateEndDayComboBox() {
        int year = (int) endYearCombo.getSelectedItem();
        int month = (int) endMonthCombo.getSelectedItem();
        endDayCombo.setModel(new DefaultComboBoxModel<>());
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        for (int day = 1; day <= maxDay; day++) {
            endDayCombo.addItem(day);
        }
    }

    private void queryStatistics() {
        LocalDate startDate = LocalDate.of(
                (int) startYearCombo.getSelectedItem(),
                (int) startMonthCombo.getSelectedItem(),
                (int) startDayCombo.getSelectedItem());

        LocalDate endDate = LocalDate.of(
                (int) endYearCombo.getSelectedItem(),
                (int) endMonthCombo.getSelectedItem(),
                (int) endDayCombo.getSelectedItem());

        if (startDate.isAfter(endDate)) {
            JOptionPane.showMessageDialog(this, "Start date cannot be later than end date", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        updateStatistics(startDate, endDate);
    }

    private void updateStatistics(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionManager.getTransactions();

        double totalIncome = 0;
        double totalExpense = 0;
        int transactionCount = 0;

        // For storing income and expenses by category
        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            LocalDate transactionDate = t.getDate();

            // Check if transaction is within selected range
            if (startDate != null && transactionDate.isBefore(startDate)) {
                continue;
            }
            if (endDate != null && transactionDate.isAfter(endDate)) {
                continue;
            }

            if ("Income".equals(t.getType())) {
                totalIncome += t.getAmount();
                // Update income category statistics
                incomeByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
            } else if ("Expense".equals(t.getType())) {
                totalExpense += t.getAmount();
                // Update expense category statistics
                expenseByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
            }
            transactionCount++;
        }

        double balance = totalIncome - totalExpense;

        String dateRangeText = "";
        if (startDate != null && endDate != null) {
            dateRangeText = String.format("Time period: %s to %s\n\n",
                    startDate.toString(), endDate.toString());
        }

        // Build category statistics
        StringBuilder categoryStats = new StringBuilder();

        // Income category statistics
        if (!incomeByCategory.isEmpty()) {
            categoryStats.append("\nIncome by category:\n");
            for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
                double percentage = totalIncome > 0 ? (entry.getValue() / totalIncome) * 100 : 0;
                categoryStats.append(String.format("  %s: ¥%.2f (%.1f%%)\n",
                        entry.getKey(), entry.getValue(), percentage));
            }
        }

        // Expense category statistics
        if (!expenseByCategory.isEmpty()) {
            categoryStats.append("\nExpenses by category:\n");
            for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
                double percentage = totalExpense > 0 ? (entry.getValue() / totalExpense) * 100 : 0;
                categoryStats.append(String.format("  %s: ¥%.2f (%.1f%%)\n",
                        entry.getKey(), entry.getValue(), percentage));
            }
        }

        statsArea.setText(String.format(
                "%sStatistics:\n\n" +
                        "Total Income: ¥%.2f\n" +
                        "Total Expense: ¥%.2f\n" +
                        "Current Balance: ¥%.2f\n\n" +
                        "Total Transactions: %d" +
                        "%s",
                dateRangeText, totalIncome, totalExpense, balance, transactionCount,
                categoryStats.toString()));
    }
}