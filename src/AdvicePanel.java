import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvicePanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final TransactionManager transactionManager;
    private JTextArea adviceArea;

    public AdvicePanel(User user, TransactionManager transactionManager) {
        this.currentUser = user;
        this.transactionManager = transactionManager;

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initializeComponents();
        generateAdvice();
    }

    private void initializeComponents() {
        adviceArea = new JTextArea();
        adviceArea.setFont(new Font("Arial", Font.BOLD, 16)); // Changed font
        adviceArea.setForeground(Color.BLACK);
        adviceArea.setEditable(false);
        adviceArea.setBackground(Color.WHITE);
        adviceArea.setLineWrap(true);
        adviceArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(adviceArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Financial Advice")); // Modified

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void refreshData() {
        generateAdvice();
    }

    private void generateAdvice() {
        List<Transaction> transactions = transactionManager.getTransactions();

        if (transactions.isEmpty()) {
            adviceArea.setText("No transaction records found. Start tracking your income and expenses."); // Modified
            return;
        }

        // Calculate total expenses by category
        Map<String, Double> expenseByCategory = transactions.stream()
                .filter(t -> "expense".equals(t.getType())) // Modified
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));

        if (expenseByCategory.isEmpty()) {
            adviceArea.setText("Only income records available. No expenses found."); // Modified
            return;
        }

        // Find highest and lowest spending categories
        Map.Entry<String, Double> maxEntry = expenseByCategory.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .orElse(null);

        Map.Entry<String, Double> minEntry = expenseByCategory.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElse(null);

        // Generate advice
        StringBuilder advice = new StringBuilder();
        advice.append("===== Spending Analysis Report =====\n\n"); // Modified

        if (maxEntry != null && minEntry != null) {
            if (maxEntry.getKey().equals(minEntry.getKey())) {
                advice.append("Your spending is concentrated in category: [")
                        .append(maxEntry.getKey()).append("], total ")
                        .append(String.format("%.2f dollars.\n", maxEntry.getValue())) // Modified
                        .append("Recommendation: Monitor this category and optimize expenses.\n\n");
            } else {
                advice.append("Highest spending category: ").append(maxEntry.getKey())
                        .append(String.format(", total %.2f dollars.\n", maxEntry.getValue())) // Modified
                        .append("Suggestion: Reduce expenses in this category.\n\n");

                advice.append("Lowest spending category: ").append(minEntry.getKey())
                        .append(String.format(", total %.2f dollars.\n", minEntry.getValue())) // Modified
                        .append("Good spending habit detected. Maintain this pattern.\n\n");
            }
        }

        // Add general recommendations
        double totalExpense = expenseByCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        advice.append("General Recommendations:\n") // Modified
                .append(String.format("- Total expenses: %.2f dollars\n", totalExpense))
                .append("- Regularly review spending patterns\n")
                .append("- Allocate savings to investments when possible");

        adviceArea.setText(advice.toString());
    }
}
