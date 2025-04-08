/*public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String registerDate;
    private String lastLoginDate;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.registerDate = java.time.LocalDate.now().toString();
    }

    // Getter和Setter方法
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getRegisterDate() { return registerDate; }
    public void setRegisterDate(String registerDate) { this.registerDate = registerDate; }
    public String getLastLoginDate() { return lastLoginDate; }
    public void setLastLoginDate(String lastLoginDate) { this.lastLoginDate = lastLoginDate; }

    // 将用户信息转换为字符串，用逗号分隔
    public String toFileString() {
        return String.format("%s,%s,%s,%s,%s,%s",
                username,
                password,
                email != null ? email : "",
                phone != null ? phone : "",
                registerDate,
                lastLoginDate != null ? lastLoginDate : "");
    }

    // 从字符串解析用户信息
    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        User user = new User();
        user.setUsername(parts[0]);
        user.setPassword(parts[1]);
        user.setEmail(parts[2].isEmpty() ? null : parts[2]);
        user.setPhone(parts[3].isEmpty() ? null : parts[3]);
        user.setRegisterDate(parts[4]);
        if (parts.length > 5 && !parts[5].isEmpty()) {
            user.setLastLoginDate(parts[5]);
        }
        return user;
    }
}*/
// User.java
public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String registrationDate;
    private String lastLoginDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.registrationDate = java.time.LocalDate.now().toString();
        this.lastLoginDate = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String date) { this.registrationDate = date; }

    public String getLastLoginDate() { return lastLoginDate; }
    public void setLastLoginDate(String date) { this.lastLoginDate = date; }

    public void updateLastLogin() {
        this.lastLoginDate = java.time.LocalDateTime.now().toString();
    }
}

