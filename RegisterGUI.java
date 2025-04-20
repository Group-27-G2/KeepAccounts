import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RegisterGUI extends JFrame {
    private UserManager userManager;
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private BufferedImage backgroundImage;

    public RegisterGUI(UserManager userManager) {
        this.userManager = userManager;
        initializeUI();
    }

    private void initializeUI() {
        // Load background image
        try {
            URL imageUrl = getClass().getResource("bg3.jpg"); // Make sure the image path is correct
            if (imageUrl == null) {
                System.err.println("Image file not found: bg3.jpg");
            } else {
                backgroundImage = ImageIO.read(imageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set window properties
        setTitle("Personal Finance System - Register");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default border
        setShape(new RoundRectangle2D.Double(0, 0, 450, 400, 20, 20)); // Rounded corners

        // Main panel setup - custom panel for background drawing
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw background image (use background color if no image)
                if (backgroundImage != null) {
                    // Maintain image aspect ratio and center it
                    int imgWidth = backgroundImage.getWidth();
                    int imgHeight = backgroundImage.getHeight();
                    float imgRatio = (float) imgWidth / imgHeight;
                    float panelRatio = (float) getWidth() / getHeight();

                    int drawWidth, drawHeight;
                    if (imgRatio > panelRatio) {
                        drawHeight = getHeight();
                        drawWidth = (int) (drawHeight * imgRatio);
                    } else {
                        drawWidth = getWidth();
                        drawHeight = (int) (drawWidth / imgRatio);
                    }

                    int x = (getWidth() - drawWidth) / 2;
                    int y = (getHeight() - drawHeight) / 2;

                    g.drawImage(backgroundImage, x, y, drawWidth, drawHeight, this);

                    // Add semi-transparent overlay to ensure text visibility
                    g.setColor(new Color(240, 242, 245, 200));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setBackground(new Color(245, 248, 250)); // Default background if no image
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 120, 215));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Registration form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Username input
        formPanel.add(createInputField("Username", "Enter username", false));
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password input
        formPanel.add(createInputField("Password", "Enter password", true));
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);

        // Register button
        JButton registerButton = createGradientButton("Register", new Color(0, 120, 215), new Color(0, 150, 255));
        registerButton.addActionListener(this::registerAction);

        // Back button
        JButton backButton = createGradientButton("Back", new Color(150, 150, 150), new Color(180, 180, 180));
        backButton.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("×");
        styleCloseButton(closeButton);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(closeButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);
    }

    private JPanel createInputField(String labelText, String placeholder, boolean isPassword) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        label.setForeground(new Color(80, 80, 80));
        panel.add(label, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        if (isPassword) {
            newPasswordField = new JPasswordField();
            newPasswordField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            newPasswordField.setBorder(BorderFactory.createEmptyBorder());
            newPasswordField.setOpaque(false);
            inputPanel.add(newPasswordField);
        } else {
            newUsernameField = new JTextField();
            newUsernameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            newUsernameField.setBorder(BorderFactory.createEmptyBorder());
            newUsernameField.setOpaque(false);
            inputPanel.add(newUsernameField);
        }

        panel.add(inputPanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor,
                        0, getHeight(), endColor);
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Text
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };

        button.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.repaint();
            }
        });

        return button;
    }

    private void styleCloseButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.GRAY);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> System.exit(0));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.GRAY);
            }
        });
    }

    private void registerAction(ActionEvent e) {
        String username = newUsernameField.getText();
        String password = new String(newPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showErrorDialog("Username and password cannot be empty!", "Registration Failed");
            return;
        }
        String userId = Utilities.generateUniqueId();
        User newUser = new User(userId, username, password);

        if (userManager.register(newUser)) {
            try {
                new File(Constants.getTransactionsFilePath(userId)).createNewFile();
                new File(Constants.getCategoriesFilePath(userId)).createNewFile();
                showSuccessDialog("Registration successful!", "Success");
                dispose();
                new LoginGUI().setVisible(true);
            } catch (IOException ex) {
                showErrorDialog("Failed to create user files!", "Error");
            }
        } else {
            showErrorDialog("Username already exists!", "Registration Failed");
        }
    }

    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new RegisterGUI(new UserManager()).setVisible(true);
        });
    }
}
