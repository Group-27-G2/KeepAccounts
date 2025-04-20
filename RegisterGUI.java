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
        // 加载背景图片
        try {
            URL imageUrl = getClass().getResource("bg3.jpg"); // 确保图片路径正确
            if (imageUrl == null) {
                System.err.println("图片文件未找到:bg3.jpg");
            } else {
                backgroundImage = ImageIO.read(imageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置窗口属性
        setTitle("个人财务记账系统 - 注册");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // 去除默认边框
        setShape(new RoundRectangle2D.Double(0, 0, 450, 400, 20, 20)); // 圆角窗口

        // 主面板设置 - 使用自定义面板绘制背景
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片（如果没有图片则使用当前背景色）
                if (backgroundImage != null) {
                    // 保持图片比例并居中显示
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

                    // 添加半透明遮罩层，确保文字清晰可见
                    g.setColor(new Color(240, 242, 245, 200));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setBackground(new Color(245, 248, 250)); // 如果没有背景图片，使用默认背景色
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // 标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("创建新账户");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 120, 215));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // 注册表单面板
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // 用户名输入
        formPanel.add(createInputField("用户名", "请输入用户名", false));
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 密码输入
        formPanel.add(createInputField("密码", "请输入密码", true));
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);

        // 注册按钮
        JButton registerButton = createGradientButton("注册", new Color(0, 120, 215), new Color(0, 150, 255));
        registerButton.addActionListener(this::registerAction);

        // 返回按钮
        JButton backButton = createGradientButton("返回", new Color(150, 150, 150), new Color(180, 180, 180));
        backButton.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 关闭按钮
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

        // 标签
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        label.setForeground(new Color(80, 80, 80));
        panel.add(label, BorderLayout.NORTH);

        // 输入面板
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        if (isPassword) {
            newPasswordField = new JPasswordField();
            newPasswordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            newPasswordField.setBorder(BorderFactory.createEmptyBorder());
            newPasswordField.setOpaque(false);
            inputPanel.add(newPasswordField);
        } else {
            newUsernameField = new JTextField();
            newUsernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
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

                // 渐变背景
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor,
                        0, getHeight(), endColor);
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // 文字
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };

        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 悬停效果
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
            showErrorDialog("用户名和密码不能为空！", "注册失败");
            return;
        }
        String userId = Utilities.generateUniqueId();
        User newUser = new User(userId, username, password);

        if (userManager.register(newUser)) {
            try {
                new File(Constants.getTransactionsFilePath(userId)).createNewFile();
                new File(Constants.getCategoriesFilePath(userId)).createNewFile();
                showSuccessDialog("注册成功！", "提示");
                dispose();
                new LoginGUI().setVisible(true);
            } catch (IOException ex) {
                showErrorDialog("创建用户文件失败！", "错误");
            }
        } else {
            showErrorDialog("用户名已存在！", "注册失败");
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
