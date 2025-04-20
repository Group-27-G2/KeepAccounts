import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final UserManager userManager;

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JTextField balanceField;

    public UserPanel(User user, UserManager userManager) {
        this.currentUser = user;
        this.userManager = userManager;

        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initializeComponents();
    }

    @Override
    public void refreshData() {
        // Update balance display
        balanceField.setText(String.valueOf(currentUser.getBalance()));
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Personal Center", SwingConstants.CENTER);
        titleLabel.setFont(new Font("新罗马", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Current user info
        JLabel userInfoLabel = new JLabel("Current User: " + currentUser.getId());
        userInfoLabel.setFont(new Font("新罗马", Font.BOLD, 14));
        userInfoLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(userInfoLabel, gbc);

        // Change password section
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(new JLabel("Change Password:"), gbc);

        JPanel passwordPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        passwordPanel.setOpaque(false);

        oldPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        passwordPanel.add(new JLabel("Old Password:"));
        passwordPanel.add(oldPasswordField);
        passwordPanel.add(new JLabel("New Password:"));
        passwordPanel.add(newPasswordField);
        passwordPanel.add(new JLabel("Confirm New Password:"));
        passwordPanel.add(confirmPasswordField);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(passwordPanel, gbc);

        JButton changePasswordButton = new JButton("Confirm Change Password");
        changePasswordButton.setFont(new Font("新罗马", Font.BOLD, 14));
        changePasswordButton.setForeground(Color.BLACK);
        changePasswordButton.setBackground(new Color(255, 255, 255, 150));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(changePasswordButton, gbc);

        // Change balance section
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(new JLabel("Current Balance: "), gbc);

        balanceField = new JTextField(String.valueOf(currentUser.getBalance()));
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(balanceField, gbc);

        JButton changeBalanceButton = new JButton("Confirm Change Balance");
        changeBalanceButton.setFont(new Font("新罗马", Font.BOLD, 14));
        changeBalanceButton.setForeground(Color.BLACK);
        changeBalanceButton.setBackground(new Color(255, 255, 255, 150));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(changeBalanceButton, gbc);

        // Add event listeners
        changePasswordButton.addActionListener(e -> changePassword());
        changeBalanceButton.addActionListener(e -> changeBalance());
    }

    private void changePassword() {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "The new passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userManager.changePassword(currentUser.getId(), oldPassword, newPassword)) {
            JOptionPane.showMessageDialog(this, "Password changed successfully!");
            oldPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect old password or change failed!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeBalance() {
        try {
            double newBalance = Double.parseDouble(balanceField.getText());
            currentUser.setBalance(newBalance);
            userManager.updateUser(currentUser);
            ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
            JOptionPane.showMessageDialog(this, "Balance updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}