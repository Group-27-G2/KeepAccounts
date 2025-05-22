import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class TransactionTest {
    private Transaction transaction;
    private final String id = "test-id";
    private final LocalDate date = LocalDate.now();
    private final String type = "Income";
    private final String category = "Salary";
    private final double amount = 1000.0;
    private final String description = "Monthly salary";

    @Before
    public void setUp() {
        transaction = new Transaction(id, date, type, category, amount, description);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(id, transaction.getId());
        assertEquals(date, transaction.getDate());
        assertEquals(type, transaction.getType());
        assertEquals(category, transaction.getCategory());
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals(description, transaction.getDescription());
    }

    @Test
    public void testSetters() {
        String newId = "new-id";
        LocalDate newDate = LocalDate.now().plusDays(1);
        String newType = "Expense";
        String newCategory = "Food";
        double newAmount = 50.0;
        String newDescription = "Lunch";

        transaction.setId(newId);
        transaction.setDate(newDate);
        transaction.setType(newType);
        transaction.setCategory(newCategory);
        transaction.setAmount(newAmount);
        transaction.setDescription(newDescription);

        assertEquals(newId, transaction.getId());
        assertEquals(newDate, transaction.getDate());
        assertEquals(newType, transaction.getType());
        assertEquals(newCategory, transaction.getCategory());
        assertEquals(newAmount, transaction.getAmount(), 0.001);
        assertEquals(newDescription, transaction.getDescription());
    }

    @Test
    public void testToString() {
        String expected = String.format("ID: %s, Date: %s, Type: %s, Category: %s, Amount: %.2f, Description: %s",
                id, date, type, category, amount, description);
        assertEquals(expected, transaction.toString());
    }
}