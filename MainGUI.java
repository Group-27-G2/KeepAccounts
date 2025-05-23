import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainGUI extends JFrame {
    private User currentUser;
    private UserManager userManager;
    private TransactionManager transactionManager;
    private CategoryManager categoryManager;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Color constants
    private static final Color NAV_BACKGROUND = new Color(70, 130, 180); // Navigation panel background
    private static final Color BUTTON_NORMAL = new Color(240, 240, 240); // Normal button color
    private static final Color BUTTON_HOVER = new Color(220, 220, 220); // Button hover color
    private static final Color BUTTON_PRESSED = new Color(200, 200, 200); // Button pressed color

    public MainGUI(User user, UserManager userManager) {
        this.currentUser = user;
        this.userManager = userManager;
        this.transactionManager = new TransactionManager(user.getId(), userManager);
        this.categoryManager = new CategoryManager();

        // Load user-specific categories
        categoryManager.loadCategoriesFromFile(Constants.getCategoriesFilePath(user.getId()));

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Personal Finance Management - User: " + currentUser.getUsername());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = createLeftPanel();

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setOpaque(false);

        createPanels();

        setContentPane(new BackgroundPanel());
        ((BackgroundPanel) getContentPane()).setLayout(new BorderLayout());
        ((BackgroundPanel) getContentPane()).add(leftPanel, BorderLayout.WEST);
        ((BackgroundPanel) getContentPane()).add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "transaction");
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));
        leftPanel.setLayout(new GridLayout(7, 1, 0, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        leftPanel.setBackground(NAV_BACKGROUND);

        JLabel titleLabel = new JLabel("Finance System");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(titleLabel);

        String[] buttonNames = { "Transactions", "Add Category", "Statistics", "User Center", "Financial Advice" };
        String[] cardNames = { "transaction", "category", "statistics", "user", "advice" };

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = createNavigationButton(buttonNames[i]);

            final String cardName = cardNames[i];
            button.addActionListener(e -> {
                cardLayout.show(contentPanel, cardName);
                refreshPanel(cardName);
            });

            leftPanel.add(button);
        }

        JLabel userLabel = new JLabel("User: " + currentUser.getUsername());
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userLabel.setForeground(Color.BLACK);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(userLabel);

        return leftPanel;
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color;
                if (getModel().isPressed()) {
                    color = BUTTON_PRESSED;
                } else if (getModel().isRollover()) {
                    color = BUTTON_HOVER;
                } else {
                    color = BUTTON_NORMAL;
                }

                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                FontMetrics metrics = g.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2d.setColor(Color.BLACK);
                g2d.setFont(getFont());
                g2d.drawString(getText(), x, y);
            }
        };

        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

    private void createPanels() {
        TransactionPanel transactionPanel = new TransactionPanel(currentUser, transactionManager, categoryManager);
        CategoryPanel categoryPanel = new CategoryPanel(currentUser, transactionManager, categoryManager);
        StatisticsPanel statisticsPanel = new StatisticsPanel(currentUser, transactionManager, userManager);
        UserPanel userPanel = new UserPanel(currentUser, userManager);
        AdvicePanel advicePanel = new AdvicePanel(transactionManager);

        contentPanel.add(transactionPanel, "transaction");
        contentPanel.add(categoryPanel, "category");
        contentPanel.add(statisticsPanel, "statistics");
        contentPanel.add(userPanel, "user");
        contentPanel.add(advicePanel, "advice");
    }

    private void refreshPanel(String panelName) {
        Component[] components = contentPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof RefreshablePanel
                    && contentPanel.getComponentZOrder(comp) == contentPanel.getComponentCount() - 1) {
                ((RefreshablePanel) comp).refreshData();
            }
        }
    }

    public void refreshAllData() {
        transactionManager.loadTransactionsFromFile();
        categoryManager.loadCategoriesFromFile(Constants.getCategoriesFilePath(currentUser.getId()));

        Component[] components = contentPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof RefreshablePanel) {
                ((RefreshablePanel) comp).refreshData();
            }
        }
    }

    public interface RefreshablePanel {
        void refreshData();
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                File imageFile = new File("image/bg2.jpg");
                backgroundImage = new ImageIcon(imageFile.getAbsolutePath()).getImage();
            } catch (Exception e) {
                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(70, 130, 180);
                Color color2 = new Color(176, 224, 230);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}