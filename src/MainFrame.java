/*import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainFrame extends JFrame {
    private JPanel contentPanel;

    public MainFrame() {
        setTitle("AI财务管理系统 - 主页");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建顶部面板
        createTopPanel();

        // 创建左侧导航面板
        createNavPanel();

        // 创建主要内容面板
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // 默认显示首页
        showHomePage();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(51, 122, 183));
        topPanel.setPreferredSize(new Dimension(1000, 60));

        JLabel titleLabel = new JLabel("AI财务管理系统", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // 添加用户信息按钮
        JButton userInfoBtn = new JButton("当前用户");
        userInfoBtn.setPreferredSize(new Dimension(100, 40));
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(51, 122, 183));
        rightPanel.add(userInfoBtn);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(200, 640));
        navPanel.setBackground(new Color(242, 242, 242));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // 导航按钮列表
        String[] navItems = {
                "首页", "数据导入", "分类界面", "AI功能",
                "日历", "账目明细", "其他功能", "用户信息"
        };

        // 创建导航按钮
        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            navPanel.add(Box.createVerticalStrut(10));
            navPanel.add(navButton);
        }

        add(navPanel, BorderLayout.WEST);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 50));
        button.setPreferredSize(new Dimension(180, 50));
        button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        button.setBackground(new Color(255, 255, 255));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(0, 20, 0, 0));

        button.addActionListener(e -> {
            switch(text) {
                case "首页": showHomePage(); break;
                case "数据导入": showDataImportPage(); break;
                case "分类界面": showCategoryPage(); break;
                case "AI功能": showAIPage(); break;
                case "日历": showCalendarPage(); break;
                case "账目明细": showAccountDetailsPage(); break;
                case "其他功能": showOtherFunctionsPage(); break;
                case "用户信息": showUserInfoPage(); break;
            }
        });

        return button;
    }

    private void showHomePage() {
        contentPanel.removeAll();
        JPanel homePanel = new JPanel(new BorderLayout());

        // 创建统计卡片面板
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加统计卡片
        addStatsCard(statsPanel, "本月支出", "¥3,500", new Color(251, 119, 147));
        addStatsCard(statsPanel, "本月收入", "¥8,000", new Color(240, 253, 16));
        addStatsCard(statsPanel, "结余", "¥4,500", new Color(35, 178, 196));
        addStatsCard(statsPanel, "预算使用", "43.75%", new Color(236, 162, 90));

        homePanel.add(statsPanel, BorderLayout.NORTH);

        // 添加最近交易记录
        JPanel recentTransactions = createRecentTransactionsPanel();
        homePanel.add(recentTransactions, BorderLayout.CENTER);

        contentPanel.add(homePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addStatsCard(JPanel panel, String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(color, 2));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(20));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(20));

        panel.add(card);
    }

    private JPanel createRecentTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                "最近交易",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 16)
        ));

        String[] columns = {"日期", "类别", "描述", "金额"};
        Object[][] data = {
                {"2024-03-15", "餐饮", "午餐", "-￥35"},
                {"2024-03-14", "交通", "地铁", "-￥4"},
                {"2024-03-14", "购物", "超市", "-￥126"},
                {"2024-03-13", "工资", "月薪", "+￥8000"}
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void showDataImportPage() {
        contentPanel.removeAll();
        JPanel dataImportPanel = new JPanel(new BorderLayout());

        // 创建顶部按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        buttonPanel.add(new JButton("导入Excel"));
        buttonPanel.add(new JButton("导入图片"));
        buttonPanel.add(new JButton("手动输入"));

        dataImportPanel.add(buttonPanel, BorderLayout.NORTH);

        // 创建中央预览区域
        JPanel previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createTitledBorder("数据预览"));
        dataImportPanel.add(previewPanel, BorderLayout.CENTER);

        contentPanel.add(dataImportPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCategoryPage() {
        contentPanel.removeAll();
        JPanel categoryPanel = new JPanel(new BorderLayout());

        // 创建分类树
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("支出分类");
        DefaultMutableTreeNode food = new DefaultMutableTreeNode("餐饮");
        food.add(new DefaultMutableTreeNode("早餐"));
        food.add(new DefaultMutableTreeNode("午餐"));
        food.add(new DefaultMutableTreeNode("晚餐"));
        root.add(food);

        DefaultMutableTreeNode transport = new DefaultMutableTreeNode("交通");
        transport.add(new DefaultMutableTreeNode("公交"));
        transport.add(new DefaultMutableTreeNode("地铁"));
        transport.add(new DefaultMutableTreeNode("打车"));
        root.add(transport);

        JTree tree = new JTree(root);
        JScrollPane treeScroll = new JScrollPane(tree);
        treeScroll.setPreferredSize(new Dimension(200, 0));
        categoryPanel.add(treeScroll, BorderLayout.WEST);

        // 创建右侧详情面板
        JPanel detailPanel = new JPanel();
        detailPanel.setBorder(BorderFactory.createTitledBorder("分类详情"));
        categoryPanel.add(detailPanel, BorderLayout.CENTER);

        contentPanel.add(categoryPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAIPage() {
        contentPanel.removeAll();
        JPanel aiPanel = new JPanel(new BorderLayout());

        // 创建功能按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addFeatureButton(buttonPanel, "智能记账", "自动识别票据和收据");
        addFeatureButton(buttonPanel, "支出预测", "基于历史数据预测未来支出");
        addFeatureButton(buttonPanel, "消费分析", "智能分析消费习惯");
        addFeatureButton(buttonPanel, "理财建议", "个性化理财方案推荐");

        aiPanel.add(buttonPanel, BorderLayout.NORTH);

        contentPanel.add(aiPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addFeatureButton(JPanel panel, String title, String description) {
        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));
        featurePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        featurePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        featurePanel.add(Box.createVerticalStrut(20));
        featurePanel.add(titleLabel);
        featurePanel.add(Box.createVerticalStrut(10));
        featurePanel.add(descLabel);
        featurePanel.add(Box.createVerticalStrut(20));

        panel.add(featurePanel);
    }

    private void showCalendarPage() {
        contentPanel.removeAll();
        JPanel calendarPanel = new JPanel(new BorderLayout());

        // 创建日历控件（这里使用简化版本）
        JPanel calendar = new JPanel(new GridLayout(7, 7));
        calendar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加星期标签
        String[] weekdays = {"日", "一", "二", "三", "四", "五", "六"};
        for (String day : weekdays) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            calendar.add(label);
        }

        // 添加日期按钮
        for (int i = 1; i <= 31; i++) {
            JButton dateBtn = new JButton(String.valueOf(i));
            calendar.add(dateBtn);
        }

        calendarPanel.add(calendar, BorderLayout.CENTER);

        contentPanel.add(calendarPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAccountDetailsPage() {
        contentPanel.removeAll();
        JPanel detailsPanel = new JPanel(new BorderLayout());

        // 创建搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("开始日期："));
        searchPanel.add(new JTextField(10));
        searchPanel.add(new JLabel("结束日期："));
        searchPanel.add(new JTextField(10));
        searchPanel.add(new JButton("搜索"));

        detailsPanel.add(searchPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columns = {"日期", "类别", "描述", "收入", "支出", "余额"};
        Object[][] data = {
                {"2024-03-15", "餐饮", "午餐", "", "35.00", "7965.00"},
                {"2024-03-14", "交通", "地铁", "", "4.00", "8000.00"},
                {"2024-03-13", "工资", "月薪", "8000.00", "", "8000.00"}
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(detailsPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showOtherFunctionsPage() {
        contentPanel.removeAll();
        JPanel otherPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        otherPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加其他功能按钮
        addFeatureButton(otherPanel, "数据备份", "备份所有财务数据");
        addFeatureButton(otherPanel, "数据导出", "导出Excel报表");
        addFeatureButton(otherPanel, "预算设置", "设置月度预算");
        addFeatureButton(otherPanel, "提醒设置", "设置账单提醒");
        addFeatureButton(otherPanel, "系统设置", "修改系统配置");
        addFeatureButton(otherPanel, "帮助文档", "查看使用说明");

        contentPanel.add(otherPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // 添加用户信息页面方法
    private void showUserInfoPage() {
        contentPanel.removeAll();
        JPanel userInfoPanel = new JPanel(new BorderLayout(20, 20));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建个人信息面板
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 添加用户信息字段
        String[][] userInfo = {
                {"用户名：", "admin"},
                {"注册时间：", "2024-03-15"},
                {"上次登录：", "2024-03-20 10:30"},
                {"邮箱地址：", "example@email.com"},
                {"手机号码：", "138****1234"}
        };

        gbc.gridy = 0;
        for (String[] info : userInfo) {
            gbc.gridx = 0;
            JLabel keyLabel = new JLabel(info[0]);
            keyLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
            infoPanel.add(keyLabel, gbc);

            gbc.gridx = 1;
            JLabel valueLabel = new JLabel(info[1]);
            valueLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            infoPanel.add(valueLabel, gbc);

            gbc.gridy++;
        }

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editButton = new JButton("修改信息");
        JButton passwordButton = new JButton("修改密码");
        JButton logoutButton = new JButton("退出登录");

        buttonPanel.add(editButton);
        buttonPanel.add(passwordButton);
        buttonPanel.add(logoutButton);

        // 添加事件监听
        editButton.addActionListener(e -> showEditUserInfoDialog());
        passwordButton.addActionListener(e -> showChangePasswordDialog());
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "确定要退出登录吗？",
                    "退出确认",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                dispose();
                // 这里需要你创建LoginFrame类
                new LoginFrame().setVisible(true);
            }
        });

        userInfoPanel.add(infoPanel, BorderLayout.NORTH);
        userInfoPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPanel.add(userInfoPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showEditUserInfoDialog() {
        JDialog dialog = new JDialog(this, "修改个人信息", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 添加表单字段
        String[] fields = {"邮箱地址：", "手机号码："};
        JTextField[] textFields = new JTextField[fields.length];

        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(fields[i]), gbc);

            gbc.gridx = 1;
            textFields[i] = new JTextField(20);
            formPanel.add(textFields[i], gbc);
        }

        // 添加按钮
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");

        saveButton.addActionListener(e -> {
            // 这里添加保存逻辑
            JOptionPane.showMessageDialog(dialog, "信息已更新");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog(this, "修改密码", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 添加密码字段
        String[] labels = {"当前密码：", "新密码：", "确认新密码："};
        JPasswordField[] passwordFields = new JPasswordField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            passwordFields[i] = new JPasswordField(20);
            formPanel.add(passwordFields[i], gbc);
        }

        // 添加按钮
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("确认修改");
        JButton cancelButton = new JButton("取消");

        saveButton.addActionListener(e -> {
            // 这里添加修改密码的逻辑
            JOptionPane.showMessageDialog(dialog, "密码已修改");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }}*/
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private UserManager userManager = new UserManager();
    private String currentUsername;
    private Color primaryColor = new Color(0, 120, 215);
    private Color secondaryColor = new Color(0, 150, 255);
    private Color accentColor = new Color(255, 183, 77);
    private Font mainFont = new Font("Arial", Font.PLAIN, 14);

    public MainFrame(String username) {
        this.currentUsername = username;
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Group27 AI Financial Management System - Home");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set background
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/main_background.jpg"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

                // Add semi-transparent overlay for better readability
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });

        setLayout(new BorderLayout());

        createTopPanel();
        createNavPanel();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.CENTER);

        showHomePage();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 0, 0, 160));
        topPanel.setPreferredSize(new Dimension(1200, 70));

        // System title
        JLabel titleLabel = new JLabel("AI Financial Management System", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // User info and notification area
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        // Notification button
        JButton notificationBtn = createIconButton("🔔", "Notifications");

        // User profile button
        JButton userProfileBtn = createIconButton("👤", currentUsername);

        rightPanel.add(notificationBtn);
        rightPanel.add(userProfileBtn);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private JButton createIconButton(String icon, String tooltip) {
        JButton button = new JButton(icon);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(null);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setToolTipText(tooltip);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(accentColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }

    private void createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(250, getHeight()));
        navPanel.setBackground(new Color(0, 0, 0, 160));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Add padding at the top
        navPanel.add(Box.createVerticalStrut(20));

        // Navigation buttons
        String[] navItems = {
                "🏠 Dashboard",
                "📊 Statistics",
                "📁 Categories",
                "🤖 AI Analysis",
                "📅 Calendar",
                "📝 Transactions",
                "⚙️ Settings",
                "👤 Profile"
        };

        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            navPanel.add(Box.createVerticalStrut(10));
            navPanel.add(navButton);
        }

        add(navPanel, BorderLayout.WEST);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(230, 50));
        button.setPreferredSize(new Dimension(230, 50));
        button.setFont(mainFont);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(0, 20, 0, 0));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 30));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
            }
        });

        button.addActionListener(e -> {
            String cmd = text.split(" ")[1];
            switch(cmd) {
                case "Dashboard": showHomePage(); break;
                case "Statistics": showStatisticsPage(); break;
                case "Categories": showCategoryPage(); break;
                case "Analysis": showAIPage(); break;
                case "Calendar": showCalendarPage(); break;
                case "Transactions": showTransactionsPage(); break;
                case "Settings": showSettingsPage(); break;
                case "Profile": showUserInfoPage(); break;
            }
        });

        return button;
    }
    private void showHomePage() {
        contentPanel.removeAll();
        JPanel homePanel = new JPanel(new GridBagLayout());
        homePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Overview Cards Panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsPanel.setOpaque(false);

        createOverviewCard(cardsPanel, "Total Balance", "$12,500.00", primaryColor);
        createOverviewCard(cardsPanel, "Monthly Income", "$8,000.00", new Color(46, 204, 113));
        createOverviewCard(cardsPanel, "Monthly Expenses", "$3,500.00", new Color(231, 76, 60));
        createOverviewCard(cardsPanel, "Savings", "$4,500.00", new Color(155, 89, 182));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        homePanel.add(cardsPanel, gbc);

        // Recent Transactions
        JPanel recentTransactionsPanel = createRecentTransactionsPanel();
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        homePanel.add(recentTransactionsPanel, gbc);

        contentPanel.add(homePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showStatisticsPage() {
        contentPanel.removeAll();
        JPanel statsPanel = new JPanel(new BorderLayout(20, 20));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Chart selection panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setOpaque(false);

        JComboBox<String> chartType = new JComboBox<>(new String[]{
                "Expense Distribution",
                "Income vs Expenses",
                "Monthly Trend",
                "Category Analysis"
        });
        styleComboBox(chartType);

        JComboBox<String> timeRange = new JComboBox<>(new String[]{
                "Last 7 Days",
                "Last 30 Days",
                "This Month",
                "This Year"
        });
        styleComboBox(timeRange);

        controlPanel.add(new JLabel("Chart Type: "));
        controlPanel.add(chartType);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(new JLabel("Time Range: "));
        controlPanel.add(timeRange);

        statsPanel.add(controlPanel, BorderLayout.NORTH);

        // Placeholder for charts
        JPanel chartPanel = new JPanel();
        chartPanel.setBackground(new Color(255, 255, 255, 50));
        chartPanel.setBorder(BorderFactory.createLineBorder(primaryColor));
        statsPanel.add(chartPanel, BorderLayout.CENTER);

        contentPanel.add(statsPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showTransactionsPage() {
        contentPanel.removeAll();
        JPanel transactionsPanel = new JPanel(new BorderLayout(20, 20));
        transactionsPanel.setOpaque(false);
        transactionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Search and filter panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setOpaque(false);

        JTextField searchField = new JTextField(20);
        styleTextField(searchField);
        searchField.setPreferredSize(new Dimension(200, 30));

        JButton addButton = new JButton("Add Transaction");
        styleButton(addButton);

        JComboBox<String> filterType = new JComboBox<>(new String[]{
                "All Transactions",
                "Income Only",
                "Expenses Only"
        });
        styleComboBox(filterType);

        controlPanel.add(searchField);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(filterType);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(addButton);

        // Transactions table
        String[] columns = {"Date", "Category", "Description", "Amount", "Type"};
        Object[][] data = {
                {"2024-03-20", "Salary", "Monthly Salary", "$8,000.00", "Income"},
                {"2024-03-19", "Food", "Restaurant", "$45.00", "Expense"},
                {"2024-03-18", "Transport", "Uber", "$25.00", "Expense"},
                {"2024-03-17", "Shopping", "Groceries", "$150.00", "Expense"}
        };

        JTable table = new JTable(data, columns);
        stylizeTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        transactionsPanel.add(controlPanel, BorderLayout.NORTH);
        transactionsPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(transactionsPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showSettingsPage() {
        contentPanel.removeAll();
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create settings sections
        String[][] settings = {
                {"General Settings", "Language", "Theme", "Currency"},
                {"Notification Settings", "Email Notifications", "Push Notifications", "Reminder Frequency"},
                {"Security Settings", "Two-Factor Authentication", "Password Change", "Login History"},
                {"Data Settings", "Auto Backup", "Export Data", "Clear Data"}
        };

        int gridy = 0;
        for (String[] section : settings) {
            JPanel sectionPanel = createSettingsSection(section[0],
                    Arrays.copyOfRange(section, 1, section.length));
            gbc.gridy = gridy++;
            settingsPanel.add(sectionPanel, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        contentPanel.add(scrollPane);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showUserInfoPage() {
        contentPanel.removeAll();
        JPanel userInfoPanel = new JPanel(new BorderLayout(20, 20));
        userInfoPanel.setOpaque(false);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // User profile section
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setOpaque(false);

        // Profile header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);

        JLabel profileIcon = new JLabel("👤");
        profileIcon.setFont(new Font("Arial", Font.PLAIN, 48));

        JLabel usernameLabel = new JLabel(currentUsername);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        usernameLabel.setForeground(Color.WHITE);

        headerPanel.add(profileIcon);
        headerPanel.add(usernameLabel);

        // Profile details
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        User user = userManager.getUserByUsername(currentUsername);
        addUserInfoField(detailsPanel, "Email:", user.getEmail(), gbc, 0);
        addUserInfoField(detailsPanel, "Phone:", user.getPhone(), gbc, 1);
        addUserInfoField(detailsPanel, "Registration Date:", user.getRegistrationDate(), gbc, 2);
        addUserInfoField(detailsPanel, "Last Login:", user.getLastLoginDate(), gbc, 3);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton editButton = new JButton("Edit Profile");
        JButton passwordButton = new JButton("Change Password");
        JButton logoutButton = new JButton("Logout");

        styleButton(editButton);
        styleButton(passwordButton);
        styleButton(logoutButton);
        logoutButton.setBackground(new Color(231, 76, 60));

        buttonPanel.add(editButton);
        buttonPanel.add(passwordButton);
        buttonPanel.add(logoutButton);

        // Add action listeners
        editButton.addActionListener(e -> showEditUserInfoDialog());
        passwordButton.addActionListener(e -> showChangePasswordDialog());
        logoutButton.addActionListener(e -> logout());

        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(detailsPanel, BorderLayout.CENTER);
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);

        userInfoPanel.add(profilePanel, BorderLayout.NORTH);

        contentPanel.add(userInfoPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Helper methods for styling components
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(255, 255, 255, 220));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setForeground(Color.BLACK);
    }

    private void styleButton(JButton button) {
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(mainFont);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(secondaryColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(primaryColor);
            }
        });
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(mainFont);
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
    }

    private void stylizeTable(JTable table) {
        table.setFont(mainFont);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(primaryColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(232, 242, 254));
        table.setSelectionForeground(Color.BLACK);
    }
    private void showAIPage() {
        contentPanel.removeAll();
        JPanel aiPanel = new JPanel(new BorderLayout(20, 20));
        aiPanel.setOpaque(false);
        aiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // AI Features Grid
        JPanel featuresGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        featuresGrid.setOpaque(false);

        // Add AI feature cards
        addAIFeatureCard(featuresGrid, "Smart Receipt Scanner",
                "Automatically scan and process receipts using AI",
                "📷");

        addAIFeatureCard(featuresGrid, "Expense Prediction",
                "Predict future expenses based on historical data",
                "📊");

        addAIFeatureCard(featuresGrid, "Budget Optimization",
                "Get AI-powered suggestions for budget optimization",
                "💡");

        addAIFeatureCard(featuresGrid, "Anomaly Detection",
                "Detect unusual spending patterns automatically",
                "🔍");

        aiPanel.add(featuresGrid, BorderLayout.NORTH);

        // AI Assistant Chat Panel
        JPanel chatPanel = createAIChatPanel();
        aiPanel.add(chatPanel, BorderLayout.CENTER);

        contentPanel.add(aiPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addAIFeatureCard(JPanel panel, String title, String description, String icon) {
        JPanel card = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 160));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Description
        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" +
                description + "</div></html>");
        descLabel.setFont(mainFont);
        descLabel.setForeground(Color.WHITE);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout
        JPanel textPanel = new JPanel(new BorderLayout(5, 5));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descLabel, BorderLayout.CENTER);

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accentColor, 2),
                        BorderFactory.createEmptyBorder(13, 13, 13, 13)
                ));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            }
        });

        panel.add(card);
    }

    private void showCategoryPage() {
        contentPanel.removeAll();
        JPanel categoryPanel = new JPanel(new BorderLayout(20, 20));
        categoryPanel.setOpaque(false);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Category control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setOpaque(false);

        JButton addCategoryBtn = new JButton("Add Category");
        JButton editCategoryBtn = new JButton("Edit");
        JButton deleteCategoryBtn = new JButton("Delete");

        styleButton(addCategoryBtn);
        styleButton(editCategoryBtn);
        styleButton(deleteCategoryBtn);
        deleteCategoryBtn.setBackground(new Color(231, 76, 60));

        controlPanel.add(addCategoryBtn);
        controlPanel.add(editCategoryBtn);
        controlPanel.add(deleteCategoryBtn);

        // Category tree and details split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOpaque(false);

        // Category tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Categories");
        createCategoryTree(root);
        JTree categoryTree = new JTree(root);
        categoryTree.setBackground(new Color(255, 255, 255, 50));
        categoryTree.setFont(mainFont);

        JScrollPane treeScroll = new JScrollPane(categoryTree);
        treeScroll.setOpaque(false);
        treeScroll.getViewport().setOpaque(false);

        // Category details panel
        JPanel detailsPanel = createCategoryDetailsPanel();

        splitPane.setLeftComponent(treeScroll);
        splitPane.setRightComponent(detailsPanel);
        splitPane.setDividerLocation(300);

        categoryPanel.add(controlPanel, BorderLayout.NORTH);
        categoryPanel.add(splitPane, BorderLayout.CENTER);

        contentPanel.add(categoryPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCalendarPage() {
        contentPanel.removeAll();
        JPanel calendarPanel = new JPanel(new BorderLayout(20, 20));
        calendarPanel.setOpaque(false);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Calendar control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setOpaque(false);

        JButton prevMonth = new JButton("◀");
        JButton nextMonth = new JButton("▶");
        JLabel monthYearLabel = new JLabel("March 2025", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 20));
        monthYearLabel.setForeground(Color.WHITE);

        styleButton(prevMonth);
        styleButton(nextMonth);

        controlPanel.add(prevMonth);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(monthYearLabel);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(nextMonth);

        // Calendar grid
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7, 5, 5));
        calendarGrid.setOpaque(false);

        // Add day labels
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            dayLabel.setForeground(Color.WHITE);
            calendarGrid.add(dayLabel);
        }

        // Add date buttons
        for (int i = 1; i <= 31; i++) {
            JButton dateBtn = createDateButton(String.valueOf(i));
            calendarGrid.add(dateBtn);
        }

        // Transaction preview panel
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setOpaque(false);
        previewPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Transactions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        calendarPanel.add(controlPanel, BorderLayout.NORTH);
        calendarPanel.add(calendarGrid, BorderLayout.CENTER);
        calendarPanel.add(previewPanel, BorderLayout.SOUTH);

        contentPanel.add(calendarPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JButton createDateButton(String date) {
        JButton button = new JButton(date) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(secondaryColor);
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(255, 255, 255, 80));
                } else {
                    g.setColor(new Color(255, 255, 255, 50));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };

        button.setForeground(Color.WHITE);
        button.setFont(mainFont);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(60, 60));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(accentColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private JPanel createAIChatPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "AI Assistant",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Chat display area
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(mainFont);
        chatArea.setBackground(new Color(255, 255, 255, 50));
        chatArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Input panel
        // 继续 createAIChatPanel() 方法
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setOpaque(false);

        JTextField inputField = new JTextField();
        styleTextField(inputField);

        JButton sendButton = new JButton("Send");
        styleButton(sendButton);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void createCategoryTree(DefaultMutableTreeNode root) {
        // Income categories
        DefaultMutableTreeNode income = new DefaultMutableTreeNode("Income");
        income.add(new DefaultMutableTreeNode("Salary"));
        income.add(new DefaultMutableTreeNode("Investment"));
        income.add(new DefaultMutableTreeNode("Bonus"));
        income.add(new DefaultMutableTreeNode("Other Income"));
        root.add(income);

        // Expense categories
        DefaultMutableTreeNode expenses = new DefaultMutableTreeNode("Expenses");
        expenses.add(new DefaultMutableTreeNode("Food & Dining"));
        expenses.add(new DefaultMutableTreeNode("Transportation"));
        expenses.add(new DefaultMutableTreeNode("Shopping"));
        expenses.add(new DefaultMutableTreeNode("Bills & Utilities"));
        expenses.add(new DefaultMutableTreeNode("Healthcare"));
        expenses.add(new DefaultMutableTreeNode("Entertainment"));
        root.add(expenses);
    }

    private JPanel createCategoryDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Category Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Details form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add form fields
        addFormField(formPanel, "Category Name:", new JTextField(20), gbc, 0);
        addFormField(formPanel, "Type:", createTypeComboBox(), gbc, 1);
        addFormField(formPanel, "Description:", new JTextArea(3, 20), gbc, 2);
        addFormField(formPanel, "Budget Limit:", new JTextField(20), gbc, 3);

        panel.add(formPanel, BorderLayout.NORTH);

        // Transaction history for this category
        JTable historyTable = new JTable(new Object[][]{},
                new String[]{"Date", "Description", "Amount"});
        stylizeTable(historyTable);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void addFormField(JPanel panel, String label, JComponent field,
                              GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        if (field instanceof JTextField) {
            styleTextField((JTextField)field);
        } else if (field instanceof JTextArea) {
            JTextArea textArea = (JTextArea)field;
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBackground(new Color(255, 255, 255, 220));
            textArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
        panel.add(field, gbc);
    }

    private JComboBox<String> createTypeComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Income", "Expense"});
        styleComboBox(comboBox);
        return comboBox;
    }

    private void createOverviewCard(JPanel panel, String title, String amount, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(color.getRed(), color.getGreen(),
                        color.getBlue(), 160));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.WHITE);

        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        amountLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(amountLabel, BorderLayout.CENTER);

        panel.add(card);
    }

    private JPanel createRecentTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Recent Transactions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Sample data
        String[] columns = {"Date", "Category", "Description", "Amount", "Type"};
        Object[][] data = {
                {"2024-03-20", "Salary", "Monthly Salary", "$8,000.00", "Income"},
                {"2024-03-19", "Food", "Restaurant", "$45.00", "Expense"},
                {"2024-03-18", "Transport", "Uber", "$25.00", "Expense"},
                {"2024-03-17", "Shopping", "Groceries", "$150.00", "Expense"}
        };

        JTable table = new JTable(data, columns);
        stylizeTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void showEditUserInfoDialog() {
        JDialog dialog = new JDialog(this, "Edit Profile", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 0, 0, 160));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        User user = userManager.getUserByUsername(currentUsername);

        JTextField emailField = new JTextField(user.getEmail(), 20);
        JTextField phoneField = new JTextField(user.getPhone(), 20);

        styleTextField(emailField);
        styleTextField(phoneField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            userManager.updateUserInfo(currentUsername,
                    emailField.getText(), phoneField.getText());
            dialog.dispose();
            showUserInfoPage(); // Refresh the user info page
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog(this, "Change Password", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 0, 0, 160));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPasswordField currentPass = new JPasswordField(20);
        JPasswordField newPass = new JPasswordField(20);
        JPasswordField confirmPass = new JPasswordField(20);

        styleTextField(currentPass);
        styleTextField(newPass);
        styleTextField(confirmPass);

        // Add fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Current Password:"), gbc);
        gbc.gridx = 1;
        panel.add(currentPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        panel.add(newPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        panel.add(confirmPass, gbc);

        JButton saveButton = new JButton("Change Password");
        styleButton(saveButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String currentPassword = new String(currentPass.getPassword());
            String newPassword = new String(newPass.getPassword());
            String confirmPassword = new String(confirmPass.getPassword());

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog,
                        "New passwords do not match!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userManager.changePassword(currentUsername, currentPassword, newPassword)) {
                JOptionPane.showMessageDialog(dialog,
                        "Password changed successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog,
                        "Current password is incorrect!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }



    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    // 新添加的方法
    private JPanel createSettingsSection(String title, String[] options) {
        JPanel sectionPanel = new JPanel(new BorderLayout(10, 10));
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        JPanel optionsPanel = new JPanel(new GridLayout(options.length, 1, 5, 5));
        optionsPanel.setOpaque(false);

        for (String option : options) {
            JPanel optionPanel = new JPanel(new BorderLayout(10, 0));
            optionPanel.setOpaque(false);

            JLabel optionLabel = new JLabel(option);
            optionLabel.setForeground(Color.WHITE);
            optionLabel.setFont(mainFont);

            JComboBox<String> optionCombo = new JComboBox<>();
            switch (option) {
                case "Language":
                    optionCombo.setModel(new DefaultComboBoxModel<>(
                            new String[]{"English", "Chinese", "Spanish"}));
                    break;
                case "Theme":
                    optionCombo.setModel(new DefaultComboBoxModel<>(
                            new String[]{"Dark", "Light", "System"}));
                    break;
                case "Currency":
                    optionCombo.setModel(new DefaultComboBoxModel<>(
                            new String[]{"USD", "EUR", "CNY", "JPY"}));
                    break;
                default:
                    optionCombo.setModel(new DefaultComboBoxModel<>(
                            new String[]{"Enabled", "Disabled"}));
            }
            styleComboBox(optionCombo);

            optionPanel.add(optionLabel, BorderLayout.WEST);
            optionPanel.add(optionCombo, BorderLayout.EAST);
            optionsPanel.add(optionPanel);
        }

        sectionPanel.add(optionsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private void addUserInfoField(JPanel panel, String label, String value,
                                  GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(mainFont);
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        JLabel valueComponent = new JLabel(value != null ? value : "Not set");
        valueComponent.setForeground(Color.WHITE);
        valueComponent.setFont(mainFont);
        panel.add(valueComponent, gbc);
    }
}

