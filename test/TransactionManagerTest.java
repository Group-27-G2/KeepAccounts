import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

public class TransactionManagerTest {
    private TransactionManager transactionManager;
    private UserManager userManager;
    private final String testUserId = "test-user";
    private final String testUsername = "testuser";
    private final String testPassword = "password";

    @Before
    public void setUp() {
        // Setup test user
        userManager = new UserManager();
        userManager.register(new User(testUserId, testUsername, testPassword));

        // Setup transaction manager
        transactionManager = new TransactionManager(testUserId, userManager);

        // Clear any existing transactions
        transactionManager.getTransactions().forEach(t -> transactionManager.deleteTransaction(t.getId()));
    }

    @After
    public void tearDown() {
        // Clean up test files
        new File(Constants.getTransactionsFilePath(testUserId)).delete();
        new File(Constants.USERS_FILE).delete();
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");

        transactionManager.addTransaction(transaction);

        List<Transaction> transactions = transactionManager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.get(0));

        // Verify balance was updated
        User user = userManager.getUserById(testUserId);
        assertEquals(1000.0, user.getBalance(), 0.001);
    }

    @Test
    public void testUpdateTransaction() {
        // Add initial transaction
        Transaction original = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");
        transactionManager.addTransaction(original);

        // Update transaction (change from Income to Expense)
        Transaction updated = new Transaction(
                "t1", LocalDate.now().plusDays(1), "Expense", "Food", 50.0, "Lunch");
        transactionManager.updateTransaction("t1", updated);

        List<Transaction> transactions = transactionManager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals(updated.getAmount(), transactions.get(0).getAmount(), 0.001);

        // Verify balance was updated correctly:
        // Original: +1000 (Income)
        // Update: -1000 (reverse Income) -50 (new Expense) = -50 total
        User user = userManager.getUserById(testUserId);
        assertEquals(-50.0, user.getBalance(), 0.001);
    }

    @Test
    public void testDeleteTransaction() {
        Transaction transaction = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");
        transactionManager.addTransaction(transaction);

        transactionManager.deleteTransaction("t1");

        List<Transaction> transactions = transactionManager.getTransactions();
        assertTrue(transactions.isEmpty());

        // Verify balance was updated (added 1000 then subtracted 1000)
        User user = userManager.getUserById(testUserId);
        assertEquals(0.0, user.getBalance(), 0.001);
    }

    @Test
    public void testGetTransactionsByCategory() {
        Transaction t1 = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");
        Transaction t2 = new Transaction(
                "t2", LocalDate.now(), "Expense", "Food", 50.0, "Lunch");
        Transaction t3 = new Transaction(
                "t3", LocalDate.now(), "Expense", "Food", 30.0, "Dinner");

        transactionManager.addTransaction(t1);
        transactionManager.addTransaction(t2);
        transactionManager.addTransaction(t3);

        List<Transaction> foodTransactions = transactionManager.getTransactionsByCategory("Food");
        assertEquals(2, foodTransactions.size());
        assertTrue(foodTransactions.stream().allMatch(t -> t.getCategory().equals("Food")));
    }

    @Test
    public void testDeleteTransactionsByCategory() {
        Transaction t1 = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");
        Transaction t2 = new Transaction(
                "t2", LocalDate.now(), "Expense", "Food", 50.0, "Lunch");
        Transaction t3 = new Transaction(
                "t3", LocalDate.now(), "Expense", "Food", 30.0, "Dinner");

        transactionManager.addTransaction(t1);
        transactionManager.addTransaction(t2);
        transactionManager.addTransaction(t3);

        transactionManager.deleteTransactionsByCategory("Food");

        List<Transaction> transactions = transactionManager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("Salary", transactions.get(0).getCategory());

        // Verify balance was updated correctly:
        // Original: +1000 (Income) -50 (Expense) -30 (Expense) = 920
        // After delete: +50 (reverse Expense) +30 (reverse Expense) = +80
        // Total: 920 + 80 = 1000
        User user = userManager.getUserById(testUserId);
        assertEquals(1000.0, user.getBalance(), 0.001);
    }

    @Test
    public void testUpdateTransactionsCategory() {
        Transaction t1 = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");
        Transaction t2 = new Transaction(
                "t2", LocalDate.now(), "Expense", "Food", 50.0, "Lunch");

        transactionManager.addTransaction(t1);
        transactionManager.addTransaction(t2);

        transactionManager.updateTransactionsCategory("Food", "Dining");

        List<Transaction> transactions = transactionManager.getTransactions();
        assertEquals(2, transactions.size());
        assertTrue(transactions.stream().anyMatch(t -> t.getCategory().equals("Dining")));
        assertFalse(transactions.stream().anyMatch(t -> t.getCategory().equals("Food")));
    }

    @Test
    public void testFilePersistence() {
        Transaction transaction = new Transaction(
                "t1", LocalDate.now(), "Income", "Salary", 1000.0, "Monthly salary");
        transactionManager.addTransaction(transaction);

        // Create new manager which should load from file
        TransactionManager newManager = new TransactionManager(testUserId, userManager);
        List<Transaction> transactions = newManager.getTransactions();

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).getId());
    }
}