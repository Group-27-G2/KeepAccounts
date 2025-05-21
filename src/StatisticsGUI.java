import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatisticsGUI extends JFrame {
    private User currentUser;
    private List<Transaction> transactions;
    private UserManager userManager;

    public StatisticsGUI(User user, List<Transaction> transactions, UserManager userManager) {
        this.currentUser = user;
        this.transactions = transactions;
        this.userManager = userManager;

        setTitle("Statistics - User ID: " + user.getId());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Statistics panel
        JPanel statisticsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {
            if ("Income".equals(transaction.getType())) {
                totalIncome += transaction.getAmount();
            } else if ("Expense".equals(transaction.getType())) {
                totalExpense += transaction.getAmount();
            }
        }

        statisticsPanel.add(new JLabel("Total Income:"));
        statisticsPanel.add(new JLabel(String.format("%.2f", totalIncome)));
        statisticsPanel.add(new JLabel("Total Expense:"));
        statisticsPanel.add(new JLabel(String.format("%.2f", totalExpense)));
        statisticsPanel.add(new JLabel("Current Balance:"));
        statisticsPanel.add(new JLabel(String.format("%.2f", user.getBalance())));

        // Update balance panel
        JPanel balancePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        balancePanel.setBorder(BorderFactory.createTitledBorder("Update Balance"));

        JTextField balanceField = new JTextField();
        JButton updateBalanceButton = new JButton("Update Balance");

        balancePanel.add(new JLabel("New Balance:"));
        balancePanel.add(balanceField);
        balancePanel.add(new JLabel());
        balancePanel.add(updateBalanceButton);

        updateBalanceButton.addActionListener(e -> {
            try {
                double newBalance = Double.parseDouble(balanceField.getText());
                userManager.updateBalance(user.getId(), newBalance); // Update balance
                JOptionPane.showMessageDialog(this, "Balance updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid balance!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(statisticsPanel, BorderLayout.NORTH);
        mainPanel.add(balancePanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}