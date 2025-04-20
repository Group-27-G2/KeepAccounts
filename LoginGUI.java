import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class LoginGUI extends JFrame {
    private UserManager userManager;
    private BufferedImage backgroundImage;

    public LoginGUI() {
        userManager = new UserManager();

        // Set window properties
        setTitle("Personal Finance System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default border
        setShape(new RoundRectangle2D.Double(0, 0, 500, 400, 20, 20)); // Rounded corners

        // Main panel setup - custom panel for background drawing
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw background image (use background color if no image)
                if (backgroundImage != null) {
                    // Maintain aspect ratio and center the image
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

                    // Add semi-transparent overlay for better text visibility
                    g.setColor(new Color(240, 242, 245, 200));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        try {
            URL imageUrl = getClass().getResource("bg1.jpg");
            if (imageUrl == null) {
                System.err.println("Image file not found: image/bg1.jpg");
            } else {
                backgroundImage = ImageIO.read(imageUrl);
            }

            Graphics2D g2d = backgroundImage.createGraphics();
            g2d.setColor(new Color(240, 242, 245));
            g2d.fillRect(0, 0, 1, 1);
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Personal Finance System");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 120, 215));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Login form panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Username input
        JPanel usernamePanel = createInputPanel("Username", "");
        JTextField usernameField = (JTextField) ((JPanel) usernamePanel.getComponent(1)).getComponent(0);

        // Password input
        JPanel passwordPanel = createInputPanel("Password", "");
        JPasswordField passwordField = (JPasswordField) ((JPanel) passwordPanel.getComponent(1)).getComponent(0);

        loginPanel.add(usernamePanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);

        // Login button
        JButton loginButton = createStyledButton("Login", new Color(0, 120, 215));
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = userManager.login(username, password);
            if (user != null) {
                dispose();
                new MainGUI(user, userManager).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Register button
        JButton registerButton = createStyledButton("Register", new Color(100, 100, 100));
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterGUI(userManager).setVisible(true);
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginPanel.add(buttonPanel);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setForeground(Color.GRAY);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeButton.addActionListener(e -> System.exit(0));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(closeButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);
    }

    // Helper method to create input panel
    private JPanel createInputPanel(String labelText, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        if (labelText.equals("Password")) {
            JPasswordField passwordField = new JPasswordField(placeholder);
            passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            passwordField.setBorder(BorderFactory.createEmptyBorder());
            passwordField.setOpaque(false);
            inputPanel.add(passwordField);
        } else {
            JTextField textField = new JTextField(placeholder);
            textField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            textField.setBorder(BorderFactory.createEmptyBorder());
            textField.setOpaque(false);
            inputPanel.add(textField);
        }

        panel.add(inputPanel, BorderLayout.CENTER);
        return panel;
    }

    // Helper method to create styled button
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(240, 240, 240));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}