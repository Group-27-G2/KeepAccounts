import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsPanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final TransactionManager transactionManager;
    private final UserManager userManager;
    private JTextArea statsArea;
    private JTextArea incomeStatsArea;
    private JTextArea expenseStatsArea;
    private JComboBox<Integer> startYearCombo, endYearCombo;
    private JComboBox<Integer> startMonthCombo, endMonthCombo;
    private JComboBox<Integer> startDayCombo, endDayCombo;
    private ChartPanel pieChartPanel;
    private ChartPanel barChartPanel;

    public StatisticsPanel(User user, TransactionManager transactionManager, UserManager userManager) {
        this.currentUser = user;
        this.transactionManager = transactionManager;
        this.userManager = userManager;

        setOpaque(false);
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255, 150)); // 设置整个面板半透明
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

        // Create statistics display areas
        statsArea = createTextArea();
        incomeStatsArea = createTextArea();
        expenseStatsArea = createTextArea();

        JScrollPane scrollPane = new JScrollPane(statsArea);
        JScrollPane incomeScrollPane = new JScrollPane(incomeStatsArea);
        JScrollPane expenseScrollPane = new JScrollPane(expenseStatsArea);

        // Make scroll panes semi-transparent
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder("General Statistics"));
        scrollPane.getViewport().setBackground(new Color(255, 255, 255, 150)); // 设置滚动视图半透明

        incomeScrollPane.setOpaque(false);
        incomeScrollPane.getViewport().setOpaque(false);
        incomeScrollPane.setBorder(BorderFactory.createTitledBorder("Income Statistics"));
        incomeScrollPane.getViewport().setBackground(new Color(255, 255, 255, 150)); // 设置滚动视图半透明

        expenseScrollPane.setOpaque(false);
        expenseScrollPane.getViewport().setOpaque(false);
        expenseScrollPane.setBorder(BorderFactory.createTitledBorder("Expense Statistics"));
        expenseScrollPane.getViewport().setBackground(new Color(255, 255, 255, 150)); // 设置滚动视图半透明

        // Create query button
        JButton queryButton = createButton("Query Statistics");
        queryButton.addActionListener(e -> queryStatistics());

        // Main layout
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);
        contentPanel.setBackground(new Color(255, 255, 255, 180)); // 设置内容面板半透明但更不透明

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBackground(new Color(255, 255, 255, 180)); // 设置顶部面板半透明但更不透明
        topPanel.add(dateRangePanel, BorderLayout.CENTER);
        topPanel.add(queryButton, BorderLayout.EAST);

        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        statsPanel.setOpaque(false);
        statsPanel.setBackground(new Color(255, 255, 255, 180)); // 设置统计面板半透明但更不透明
        statsPanel.add(scrollPane);
        statsPanel.add(incomeScrollPane);
        statsPanel.add(expenseScrollPane);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(statsPanel, BorderLayout.CENTER);

        // Create chart panel
        JPanel chartPanel = new JPanel(new GridLayout(2, 1));
        chartPanel.setOpaque(false);
        chartPanel.setBackground(new Color(255, 255, 255, 180)); // 设置图表面板半透明但更不透明
        pieChartPanel = new ChartPanel(createEmptyPieChart());
        pieChartPanel.setOpaque(false); // 设置饼图面板透明
        pieChartPanel.setBackground(new Color(255, 255, 255, 150)); // 设置饼图背景半透明

        barChartPanel = new ChartPanel(createEmptyBarChart());
        barChartPanel.setOpaque(false); // 设置柱状图面板透明
        barChartPanel.setBackground(new Color(255, 255, 255, 150)); // 设置柱状图背景半透明

        chartPanel.add(pieChartPanel);
        chartPanel.add(barChartPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contentPanel, chartPanel);
        splitPane.setDividerLocation(400);
        splitPane.setOpaque(false); // 设置分割面板透明

        add(splitPane, BorderLayout.CENTER);
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.BOLD, 14)); // 使用更美观的字体
        textArea.setForeground(Color.BLACK);
        textArea.setEditable(false);
        textArea.setBackground(new Color(255, 255, 255, 150)); // Make text area semi-transparent
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14)); // 使用更美观的字体
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(173, 216, 230, 204)); // Light blue with 80% transparency
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        button.setFocusPainted(false);

        // 添加按钮悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(70, 130, 180));
                button.setFont(new Font("Segoe UI", Font.BOLD, 15));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            }
        });

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        return button;
    }

    private JPanel createDateRangePanel() {
        JPanel dateRangePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        dateRangePanel.setOpaque(false);
        dateRangePanel.setBorder(BorderFactory.createTitledBorder("Select Date Range"));

        // Start date panel
        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        startDatePanel.setOpaque(false);
        startDatePanel.setBackground(new Color(255, 255, 255, 100)); // 设置开始日期面板半透明但更透明

        // 添加标签和设置样式
        JLabel startLabel = new JLabel("Start Date:");
        startLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startDatePanel.add(startLabel);

        startYearCombo = createYearComboBox();
        startMonthCombo = createMonthComboBox();
        startDayCombo = createDayComboBox((int) startYearCombo.getSelectedItem(),
                (int) startMonthCombo.getSelectedItem());

        // 设置下拉框样式
        startYearCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        startMonthCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        startDayCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        startDatePanel.add(startYearCombo);
        startDatePanel.add(new JLabel("Year"));
        startDatePanel.add(startMonthCombo);
        startDatePanel.add(new JLabel("Month"));
        startDatePanel.add(startDayCombo);
        startDatePanel.add(new JLabel("Day"));

        // End date panel
        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        endDatePanel.setOpaque(false);
        endDatePanel.setBackground(new Color(255, 255, 255, 100)); // 设置结束日期面板半透明但更透明

        // 添加标签和设置样式
        JLabel endLabel = new JLabel("End Date:");
        endLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        endDatePanel.add(endLabel);

        endYearCombo = createYearComboBox();
        endMonthCombo = createMonthComboBox();
        endDayCombo = createDayComboBox((int) endYearCombo.getSelectedItem(),
                (int) endMonthCombo.getSelectedItem());

        // 设置下拉框样式
        endYearCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        endMonthCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        endDayCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

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
        Map<Integer, Double> expenseByMonth = new HashMap<>();

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
                // Update expense by month statistics
                int month = transactionDate.getMonthValue();
                expenseByMonth.merge(month, t.getAmount(), Double::sum);
            }
            transactionCount++;
        }

        double balance = totalIncome - totalExpense;

        String dateRangeText = "";
        if (startDate != null && endDate != null) {
            dateRangeText = String.format("Time period: %s to %s\n\n",
                    startDate.toString(), endDate.toString());
        }

        // Build general statistics
        statsArea.setText(String.format(
                "%sStatistics:\n\n" +
                        "Total Income: ¥%.2f\n" +
                        "Total Expense: ¥%.2f\n" +
                        "Current Balance: ¥%.2f\n\n" +
                        "Total Transactions: %d",
                dateRangeText, totalIncome, totalExpense, balance, transactionCount));

        // Build income category statistics
        StringBuilder incomeStats = new StringBuilder();
        if (!incomeByCategory.isEmpty()) {
            incomeStats.append("Income by category:\n");
            for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
                double percentage = totalIncome > 0 ? (entry.getValue() / totalIncome) * 100 : 0;
                incomeStats.append(String.format("  %s: ¥%.2f (%.1f%%)\n",
                        entry.getKey(), entry.getValue(), percentage));
            }
        }
        incomeStatsArea.setText(incomeStats.toString());

        // Build expense category statistics
        StringBuilder expenseStats = new StringBuilder();
        if (!expenseByCategory.isEmpty()) {
            expenseStats.append("Expenses by category:\n");
            for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
                double percentage = totalExpense > 0 ? (entry.getValue() / totalExpense) * 100 : 0;
                expenseStats.append(String.format("  %s: ¥%.2f (%.1f%%)\n",
                        entry.getKey(), entry.getValue(), percentage));
            }
        }
        expenseStatsArea.setText(expenseStats.toString());

        // Update charts
        updatePieChart(expenseByCategory); // Use expense data for pie chart
        updateBarChart(expenseByMonth);
    }

    private JFreeChart createEmptyPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        return ChartFactory.createPieChart("Expenses by Category", dataset, true, true, false);
    }

    private JFreeChart createEmptyBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        return ChartFactory.createBarChart("Expenses by Month", "Month", "Amount", dataset);
    }

    private void updatePieChart(Map<String, Double> expenseByCategory) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart chart = ChartFactory.createPieChart("Expenses by Category", dataset, true, true, false);
        pieChartPanel.setChart(chart);
    }

    private void updateBarChart(Map<Integer, Double> expenseByMonth) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Double> entry : expenseByMonth.entrySet()) {
            dataset.addValue(entry.getValue(), "Expense", entry.getKey().toString());
        }
        JFreeChart chart = ChartFactory.createBarChart("Expenses by Month", "Month", "Amount", dataset);
        barChartPanel.setChart(chart);
    }
}