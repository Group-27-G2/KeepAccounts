import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserManagerTest {

    private UserManager userManager;
    private User testUser;

    @Before
    public void setUp() throws IOException {
        userManager = new UserManager();
        testUser = new User("1", "testUser", "testPassword");
        testUser.setBalance(100.0);

        // Force delete user file to ensure test independence
        Path userFilePath = Paths.get(Constants.USERS_FILE);
        boolean deleted = Files.deleteIfExists(userFilePath);
        System.out.println("User file deletion status before test: " + deleted);
    }

    @Test
    public void testRegister() {
        System.out.println("=== Starting user registration test ===");

        // First registration
        System.out.println("Attempting to register user: " + testUser.getUsername());
        boolean firstResult = userManager.register(testUser);
        System.out.println("First registration result: " + (firstResult ? "Success" : "Failure"));

        // Check if user exists
        User registeredUser = userManager.getUserByUsername("testUser");
        if (registeredUser != null) {
            System.out.println("Registered user information: ID=" + registeredUser.getId()
                    + ", Username=" + registeredUser.getUsername()
                    + ", Balance=" + registeredUser.getBalance());
        } else {
            System.out.println("Registered user not found!");
        }

        // Fix: Declare and initialize duplicateUser before use
        User duplicateUser = new User("2", "testUser", "newPassword"); // Declared before use
        System.out.println("Attempting to register with duplicate username: " + duplicateUser.getUsername());

        boolean secondResult = userManager.register(duplicateUser);
        System.out.println("Duplicate registration result: " + (secondResult ? "Success" : "Failure"));

        // Check user information again
        User finalUser = userManager.getUserByUsername("testUser");
        if (finalUser != null) {
            System.out.println("Final user information: ID=" + finalUser.getId()
                    + ", Username=" + finalUser.getUsername()
                    + ", Password modified?=" + (!"testPassword".equals(finalUser.getPassword())));
        } else {
            System.out.println("Final user not found!");
        }
    }

    @Test
    public void testLogin() {
        // Register user first
        userManager.register(testUser);

        // Test login function
        User loggedInUser = userManager.login("testUser", "testPassword");
        assertNotNull(loggedInUser);

        // Test login with wrong password
        loggedInUser = userManager.login("testUser", "wrongPassword");
        assertNull(loggedInUser);
    }

    @Test
    public void testGetUserByUsername() {
        // Register user
        userManager.register(testUser);

        // Test getting user by username
        User foundUser = userManager.getUserByUsername("testUser");
        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    public void testGetUserById() {
        // Register user
        userManager.register(testUser);

        // Test getting user by user ID
        User foundUser = userManager.getUserById("1");
        assertNotNull(foundUser);
        assertEquals("1", foundUser.getId());
    }

    @Test
    public void testUpdateBalance() {
        // Register user
        userManager.register(testUser);

        // Update user balance
        userManager.updateBalance("1", 200.0);

        // Verify balance update
        User updatedUser = userManager.getUserById("1");
        assertEquals(200.0, updatedUser.getBalance(), 0.001);
    }

    @Test
    public void testChangePassword() {
        // Register user
        userManager.register(testUser);

        // Test changing password
        boolean result = userManager.changePassword("1", "testPassword", "newPassword");
        assertTrue(result);

        // Verify new password takes effect
        User updatedUser = userManager.login("testUser", "newPassword");
        assertNotNull(updatedUser);
    }

    @Test
    public void testUpdateUser() {
        // Register user
        userManager.register(testUser);

        // Create an updated user object
        User updatedUser = new User("1", "newUsername", "newPassword");
        updatedUser.setBalance(200.0);

        // Test updating user information
        boolean result = userManager.updateUser(updatedUser);
        assertTrue(result);

        // Verify user information update
        User retrievedUser = userManager.getUserById("1");
        assertEquals("newUsername", retrievedUser.getUsername());
        assertEquals("newPassword", retrievedUser.getPassword());
        assertEquals(200.0, retrievedUser.getBalance(), 0.001);
    }
}
