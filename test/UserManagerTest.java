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
    public void setUp() throws IOException { // Explicitly throw exceptions to avoid silent failures
        userManager = new UserManager();
        testUser = new User("1", "testUser", "testPassword");
        testUser.setBalance(100.0);

        // Force delete user file to ensure test independence
        Path userFilePath = Paths.get(Constants.USERS_FILE);
        Files.deleteIfExists(userFilePath); // Directly delete the file, ignoring non-existent cases
    }

    @Test
    public void testRegister() {
        // Test registration functionality
        boolean result = userManager.register(testUser);
        assertTrue(result);

        // Attempt to register with the same username again
        User duplicateUser = new User("2", "testUser", "newPassword");
        result = userManager.register(duplicateUser);
        assertFalse(result);
    }

    @Test
    public void testLogin() {
        // Register user first
        userManager.register(testUser);

        // Test login functionality
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

        // Verify if balance is updated
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

        // Verify if new password takes effect
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

        // Verify if user information is updated
        User retrievedUser = userManager.getUserById("1");
        assertEquals("newUsername", retrievedUser.getUsername());
        assertEquals("newPassword", retrievedUser.getPassword());
        assertEquals(200.0, retrievedUser.getBalance(), 0.001);
    }
}
