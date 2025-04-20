import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private double balance; // Add balance field

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = 0.0; // Initialize balance to 0
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + balance; // Include balance information
    }
}
