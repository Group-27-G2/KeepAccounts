import java.time.LocalDate;

public class Transaction {
    private String id;
    private LocalDate date;
    private String type;
    private String category;
    private double amount;
    private String description;

    public Transaction(String id, LocalDate date, String type, String category, double amount, String description) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Date: %s, Type: %s, Category: %s, Amount: %.2f, Description: %s",
                id, date, type, category, amount, description);
    }
}