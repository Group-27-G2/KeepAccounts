import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class UtilitiesTest {

    // Test the method for generating unique IDs
    @Test
    public void testGenerateUniqueId() {
        String id1 = Utilities.generateUniqueId();
        String id2 = Utilities.generateUniqueId();
        // Ensure the generated IDs are different
        assertNotEquals(id1, id2);
    }

    // Test the method for parsing valid date formats
    @Test
    public void testParseDateWithValidFormat() {
        String dateStr = "2024-01-01";
        LocalDate date = Utilities.parseDate(dateStr);
        // Verify the parsed result matches the expected value
        assertEquals(LocalDate.of(2024, 1, 1), date);
    }

    // Test that an exception is thrown for invalid date formats
    @Test(expected = IllegalArgumentException.class)
    public void testParseDateWithInvalidFormat() {
        String dateStr = "01-01-2024";
        // This call should throw an IllegalArgumentException
        Utilities.parseDate(dateStr);
    }
}
