import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private List<Transaction> transactions;
    private String currentUserId;
    private UserManager userManager;

    public TransactionManager(String userId, UserManager userManager) {
        this.currentUserId = userId;
        this.userManager = userManager;
        this.transactions = new ArrayList<>();
        loadTransactionsFromFile();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBalance(transaction.getAmount(), transaction.getType());
        saveTransactionsToFile();
    }

    public void updateTransaction(String id, Transaction updatedTransaction) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                // First reverse the original transaction's effect
                updateBalance(-transaction.getAmount(), transaction.getType());
                // Then apply the new transaction's effect
                updateBalance(updatedTransaction.getAmount(), updatedTransaction.getType());
                
                transaction.setDate(updatedTransaction.getDate());
                transaction.setType(updatedTransaction.getType());
                transaction.setCategory(updatedTransaction.getCategory());
                transaction.setAmount(updatedTransaction.getAmount());
                transaction.setDescription(updatedTransaction.getDescription());
                break;
            }
        }
        saveTransactionsToFile();
    }

    public void deleteTransaction(String id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                // Reverse the transaction's effect on balance
                updateBalance(-transaction.getAmount(), transaction.getType());
                transactions.remove(transaction);
                break;
            }
        }
        saveTransactionsToFile();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> getTransactionsByCategory(String category) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equals(category)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    public void deleteTransactionsByCategory(String category) {
        List<Transaction> toRemove = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equals(category)) {
                // Reverse each transaction's effect on balance
                updateBalance(-transaction.getAmount(), transaction.getType());
                toRemove.add(transaction);
            }
        }
        transactions.removeAll(toRemove);
        saveTransactionsToFile();
    }

    public void updateTransactionsCategory(String oldCategory, String newCategory) {
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equals(oldCategory)) {
                transaction.setCategory(newCategory);
            }
        }
        saveTransactionsToFile();
    }

    private void updateBalance(double amount, String type) {
        User user = userManager.getUserById(currentUserId);
        if (user == null) return;
        
        if ("Income".equals(type)) {
            user.setBalance(user.getBalance() + amount);
        } else if ("Expense".equals(type)) {
            user.setBalance(user.getBalance() - amount);
        }
        userManager.saveUsersToFile();
    }

    public void loadTransactionsFromFile() {
        transactions.clear();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Constants.getTransactionsFilePath(currentUserId)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Transaction transaction = new Transaction(
                            parts[0],
                            LocalDate.parse(parts[1]),
                            parts[2],
                            parts[3],
                            Double.parseDouble(parts[4]),
                            parts.length > 5 ? parts[5] : "");
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            // Create file if it does not exist
            saveTransactionsToFile();
        }
    }

    private void saveTransactionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(Constants.getTransactionsFilePath(currentUserId)))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getId() + ",");
                writer.write(transaction.getDate().toString() + ",");
                writer.write(transaction.getType() + ",");
                writer.write(transaction.getCategory() + ",");
                writer.write(transaction.getAmount() + ",");
                writer.write(transaction.getDescription() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}