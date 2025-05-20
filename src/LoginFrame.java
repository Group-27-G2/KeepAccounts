/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton resetButton;
    private JCheckBox rememberPassword;
    private UserManager userManager = new UserManager();

    public LoginFrame() {
        // Set window title
        setTitle("AI Financial Management System - Login");
        // Set window size
        setSize(400, 350);
        // Center the window
        setLocationRelativeTo(null);
        // Set window close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set layout
        setLayout(new BorderLayout());

        // Set background image
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("background.jpg"); // Make sure to put your image in the project directory
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        });

        // Create main panel with transparent background
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 255, 255, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create title label
        JLabel titleLabel = new JLabel("AI Financial Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Username input
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password input
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Remember password checkbox
        rememberPassword = new JCheckBox("Remember Password");
        rememberPassword.setForeground(Color.WHITE);
        rememberPassword.setOpaque(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(rememberPassword, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        resetButton = new JButton("Reset");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(resetButton);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Add event listeners
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> showRegisterDialog());
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            rememberPassword.setSelected(false);
        });

        // Add main panel to window
        add(mainPanel, BorderLayout.CENTER);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userManager.validateUser(username, password)) {
            dispose();
            new MainFrame(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
    }

    private void showRegisterDialog() {
        JDialog dialog = new JDialog(this, "Register New User", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField newUsername = new JTextField(15);
        JPasswordField newPassword = new JPasswordField(15);
        JPasswordField confirmPassword = new JPasswordField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        dialog.add(newUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(newPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(confirmPassword, gbc);

        JButton confirmButton = new JButton("Confirm");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(confirmButton, gbc);

        confirmButton.addActionListener(e -> {
            String username = newUsername.getText();
            String password = new String(newPassword.getPassword());
            String confirm = new String(confirmPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Username and password cannot be empty!");
                return;
            }

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(dialog, "Passwords do not match!");
                return;
            }

            User newUser = new User(username, password);
            if (userManager.addUser(newUser)) {
                JOptionPane.showMessageDialog(dialog, "Registration successful!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Username already exists!");
            }
        });

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton resetButton;
    private JCheckBox rememberPassword;
    private UserManager userManager;

    public LoginFrame() {
        userManager = new UserManager();
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Group27 AI Financial Management System - Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background image
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/background.jpg"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        });

        // Create semi-transparent main panel
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 160));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title Label
        JLabel titleLabel = new JLabel("AI Financial Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);
        styleTextField(usernameField);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);
        styleTextField(passwordField);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Remember password checkbox
        rememberPassword = new JCheckBox("Remember Password");
        rememberPassword.setForeground(Color.WHITE);
        rememberPassword.setOpaque(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(rememberPassword, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        resetButton = new JButton("Reset");

        styleButton(loginButton);
        styleButton(registerButton);
        styleButton(resetButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(resetButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Add action listeners
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> showRegisterDialog());
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            rememberPassword.setSelected(false);
        });

        // Load remembered login info
        loadRememberedLogin();

        add(mainPanel);
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(255, 255, 255, 220));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setForeground(Color.BLACK);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 150, 255));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userManager.validateUser(username, password)) {
            if (rememberPassword.isSelected()) {
                userManager.saveRememberedLogin(username, password);
            } else {
                userManager.clearRememberedLogin();
            }
            dispose();
            new MainFrame(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisterDialog() {
        JDialog dialog = new JDialog(this, "Register New User", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 160));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField newUsername = new JTextField(15);
        JPasswordField newPassword = new JPasswordField(15);
        JPasswordField confirmPassword = new JPasswordField(15);

        styleTextField(newUsername);
        styleTextField(newPassword);
        styleTextField(confirmPassword);

        // Add components
        addLabelAndField(panel, "Username:", newUsername, gbc, 0);
        addLabelAndField(panel, "Password:", newPassword, gbc, 1);
        addLabelAndField(panel, "Confirm Password:", confirmPassword, gbc, 2);

        JButton confirmButton = new JButton("Confirm");
        styleButton(confirmButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(confirmButton, gbc);

        confirmButton.addActionListener(e -> {
            String username = newUsername.getText();
            String password = new String(newPassword.getPassword());
            String confirm = new String(confirmPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Username and password cannot be empty!",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 至少包含一个大写字母
            if (!password.matches(".*[A-Z].*")) {
                JOptionPane.showMessageDialog(dialog,
                        "密码必须包含至少一个大写字母",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

/* 至少包含一个小写字母
            if (!password.matches(".*[a-z].*")) {
                JOptionPane.showMessageDialog(dialog,
                        "密码必须包含至少一个小写字母",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

// 至少包含一个符号，假设可用符号包含 @￥！%*？&
            if (!password.matches(".*[@￥！%*？&].*")) {
                JOptionPane.showMessageDialog(dialog,
                        "密码必须包含至少一个符号(@￥！%*？&)",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
*/
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(dialog,
                        "Passwords do not match!",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(username, password);
            if (userManager.addUser(newUser)) {
                JOptionPane.showMessageDialog(dialog,
                        "Registration successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog,
                        "Username already exists!",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field,
                                  GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void loadRememberedLogin() {
        Map<String, String> loginInfo = userManager.getRememberedLogin();
        if (!loginInfo.isEmpty()) {
            usernameField.setText(loginInfo.get("username"));
            passwordField.setText(loginInfo.get("password"));
            rememberPassword.setSelected(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}