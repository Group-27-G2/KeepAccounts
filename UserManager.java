import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.*;
import java.nio.file.*;

/*public class UserManager {
    private static final String FILE_PATH = "users.txt";
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return;
            }
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    users.add(User.fromFileString(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try {
            List<String> lines = new ArrayList<>();
            for (User user : users) {
                lines.add(user.toFileString());
            }
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user) {
        // 检查用户名是否已存在
        if (getUserByUsername(user.getUsername()) != null) {
            return false;
        }
        users.add(user);
        saveUsers();
        return true;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean validateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            updateLastLoginDate(username);
            return true;
        }
        return false;
    }

    private void updateLastLoginDate(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.setLastLoginDate(java.time.LocalDateTime.now().toString());
            saveUsers();
        }
    }

    public boolean updateUserInfo(String username, String email, String phone) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.setEmail(email);
            user.setPhone(phone);
            saveUsers();
            return true;
        }
        return false;
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            saveUsers();
            return true;
        }
        return false;
    }
}*/
import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, User> users;
    private static final String USER_FILE = "users.txt";
    private static final String LOGIN_INFO_FILE = "login_info.txt";

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    User user = new User(parts[0], parts[1]);
                    if (parts.length > 2) user.setEmail(parts[2]);
                    if (parts.length > 3) user.setPhone(parts[3]);
                    if (parts.length > 4) user.setRegistrationDate(parts[4]);
                    if (parts.length > 5) user.setLastLoginDate(parts[5]);
                    users.put(parts[0], user);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing users file found. Starting with empty user list.");
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s%n",
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail() != null ? user.getEmail() : "",
                        user.getPhone() != null ? user.getPhone() : "",
                        user.getRegistrationDate(),
                        user.getLastLoginDate()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        saveUsers();
        return true;
    }

    public boolean validateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            user.updateLastLogin();
            saveUsers();
            return true;
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public boolean updateUserInfo(String username, String email, String phone) {
        User user = users.get(username);
        if (user != null) {
            user.setEmail(email);
            user.setPhone(phone);
            saveUsers();
            return true;
        }
        return false;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            saveUsers();
            return true;
        }
        return false;
    }

    public void saveRememberedLogin(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_INFO_FILE))) {
            writer.write(username + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getRememberedLogin() {
        Map<String, String> loginInfo = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_INFO_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    loginInfo.put("username", parts[0]);
                    loginInfo.put("password", parts[1]);
                }
            }
        } catch (IOException e) {
            // File doesn't exist or can't be read
        }
        return loginInfo;
    }

    public void clearRememberedLogin() {
        File file = new File(LOGIN_INFO_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}