import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utilities {
public static String generateUniqueId() {
return UUID.randomUUID().toString();
}

public static LocalDate parseDate(String dateStr) {
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
try {
return LocalDate.parse(dateStr, formatter);
} catch (Exception e) {
throw new IllegalArgumentException("Invalid date format, please enter in format: yyyy-MM-dd");
}
}
}