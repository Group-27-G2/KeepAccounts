import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        loadUsersFromFile();
    }

    public boolean register(User newUser) {
        if (getUserByUsername(newUser.getUsername()) != null) {
            return false; // Username already exists
        }
        users.add(newUser);
        saveUsersToFile();
        return true;
    }

    public User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    User user = new User(parts[0], parts[1], parts[2]);
                    user.setBalance(Double.parseDouble(parts[3]));
                    users.add(user);
                }
            }
        } catch (IOException e) {
            // Create file if it does not exist
            saveUsersToFile();
        }
    }

    public void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.USERS_FILE))) {
            for (User user : users) {
                writer.write(
                        user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getBalance());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBalance(String userId, double newBalance) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                user.setBalance(newBalance);
                break;
            }
        }
        saveUsersToFile();
    }

    // Change user password
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (user == null) {
            return false; // User does not exist
        }
        if (!user.getPassword().equals(oldPassword)) {
            return false; // Old password is incorrect
        }
        user.setPassword(newPassword);
        saveUsersToFile();
        return true;
    }

    // Update user information
    public boolean updateUser(User user) {
        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                existingUser.setBalance(user.getBalance());
                saveUsersToFile();
                return true;
            }
        }
        return false; // User does not exist
    }
}
