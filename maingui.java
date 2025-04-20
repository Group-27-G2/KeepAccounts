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

    public MainGUI(User user, UserManager userManager) {  
        this.currentUser = user;  
        this.userManager = userManager;  
        this.transactionManager = new TransactionManager(user.getId(), userManager);  
        this.categoryManager = new CategoryManager();  

        // Load categories  
        categoryManager.loadCategoriesFromFile(Constants.getCategoriesFilePath(user.getId()));  

        initializeUI();  
    }  

    private void initializeUI() {  
        setTitle("Personal Finance Accounting Software - User ID: " + currentUser.getId());  
        setSize(1200, 800);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLayout(new BorderLayout());  

        // Left navigation panel  
        JPanel leftPanel = createLeftPanel();  

        // Right content panel (using CardLayout)  
        cardLayout = new CardLayout();  
        contentPanel = new JPanel(cardLayout);  
        contentPanel.setOpaque(false);  

        // Create various function panels  
        createPanels();  

        // Set background  
        setContentPane(new BackgroundPanel());  
        ((BackgroundPanel)getContentPane()).setLayout(new BorderLayout());  
        ((BackgroundPanel)getContentPane()).add(leftPanel, BorderLayout.WEST);  
        ((BackgroundPanel)getContentPane()).add(contentPanel, BorderLayout.CENTER);  

        // Default to showing the transaction panel  
        cardLayout.show(contentPanel, "transaction");  
    }  

    private JPanel createLeftPanel() {  
        JPanel leftPanel = new JPanel();  
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));  
        leftPanel.setLayout(new GridLayout(7, 1, 0, 10));  
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));  
        leftPanel.setBackground(new Color(70, 130, 180, 150));  

        // Title  
        JLabel titleLabel = new JLabel("Financial Management System");  
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));  
        titleLabel.setForeground(Color.WHITE);  
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        leftPanel.add(titleLabel);  

        // Function buttons  
        String[] buttonNames = {"Record Transaction", "Add Category", "Statistics", "User Center", "Bill Suggestions"};  
        String[] cardNames = {"transaction", "category", "statistics", "user", "advice"};  

        for (int i = 0; i < buttonNames.length; i++) {  
            JButton button = new JButton(buttonNames[i]);  
            button.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));  
            button.setForeground(Color.BLACK);  
            button.setBackground(new Color(255, 255, 255, 150));  
            button.setOpaque(true);  
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  

            final String cardName = cardNames[i];  
            button.addActionListener(e -> {  
                cardLayout.show(contentPanel, cardName);  
                refreshPanel(cardName);  
            });  

            leftPanel.add(button);  
        }  

        // User information  
        JLabel userLabel = new JLabel("User: " + currentUser.getId());  
        userLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));  
        userLabel.setForeground(Color.BLACK);  
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        leftPanel.add(userLabel);  

        return leftPanel;  
    }  

    private void createPanels() {  
        // Create instances of each function panel  
        TransactionPanel transactionPanel = new TransactionPanel(currentUser, transactionManager, categoryManager);  
        CategoryPanel categoryPanel = new CategoryPanel(currentUser, transactionManager, categoryManager);  
        StatisticsPanel statisticsPanel = new StatisticsPanel(currentUser, transactionManager, userManager);  
        UserPanel userPanel = new UserPanel(currentUser, userManager);  
        AdvicePanel advicePanel = new AdvicePanel(currentUser, transactionManager);  

        // Add panels to the content area  
        contentPanel.add(transactionPanel, "transaction");  
        contentPanel.add(categoryPanel, "category");  
        contentPanel.add(statisticsPanel, "statistics");  
        contentPanel.add(userPanel, "user");  
        contentPanel.add(advicePanel, "advice");  
    }  

    private void refreshPanel(String panelName) {  
        Component[] components = contentPanel.getComponents();  
        for (Component comp : components) {  
            if (comp instanceof RefreshablePanel && contentPanel.getComponentZOrder(comp) == contentPanel.getComponentCount() - 1) {  
                ((RefreshablePanel) comp).refreshData();  
            }  
        }  
    }  

    public void refreshAllData() {  
        // Reload transaction data  
        transactionManager.loadTransactionsFromFile();  
        // Reload category data  
        categoryManager.loadCategoriesFromFile(Constants.getCategoriesFilePath(currentUser.getId()));  

        // Refresh all panels  
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

    // Background panel inner class  
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