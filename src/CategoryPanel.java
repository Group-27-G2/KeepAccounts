import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CategoryPanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final TransactionManager transactionManager;
    private final CategoryManager categoryManager;

    private JPanel categoriesContainer;
    private String selectedCategory; // Track currently selected category

    // Define low-saturation semi-transparent colors
    private static final Color COLOR_ADD = new Color(76, 175, 80, 204); // Green with 80% opacity
    private static final Color COLOR_DELETE = new Color(244, 67, 54, 204); // Red with 80% opacity
    private static final Color COLOR_UPDATE = new Color(33, 150, 243, 204); // Blue with 80% opacity

    public CategoryPanel(User user, TransactionManager transactionManager, CategoryManager categoryManager) {
        this.currentUser = user;
        this.transactionManager = transactionManager;
        this.categoryManager = categoryManager;

        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initializeComponents();
    }

    @Override
    public void refreshData() {
        categoriesContainer.removeAll();
        categoryManager.getCategories().forEach(this::addCategoryBox);
        categoriesContainer.revalidate();
        categoriesContainer.repaint();
    }

    private void initializeComponents() {
        // Initialize category container with grid layout (4 columns)
        categoriesContainer = new JPanel(new GridLayout(0, 4, 15, 15));
        categoriesContainer.setOpaque(false);

        // Add existing categories
        refreshData();

        JScrollPane scrollPane = new JScrollPane(categoriesContainer);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Existing Categories"));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Action buttons panel (now at the bottom with horizontal layout)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton addButton = createStyledButton("Add Category", COLOR_ADD);
        JButton deleteButton = createStyledButton("Delete Selected", COLOR_DELETE);
        JButton updateButton = createStyledButton("Edit Category", COLOR_UPDATE);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        // Event handlers
        addButton.addActionListener(e -> addCategory());
        deleteButton.addActionListener(e -> deleteCategory());
        updateButton.addActionListener(e -> updateCategory());

        // Layout configuration
        setLayout(new BorderLayout(10, 20));
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Adjust color saturation
                Color color = baseColor;
                if (getModel().isPressed()) {
                    color = adjustSaturation(color, 0.8f);
                } else if (getModel().isRollover()) {
                    color = adjustSaturation(color, 1.2f);
                }

                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                FontMetrics metrics = g.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                g2d.drawString(getText(), x, y);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 50);
            }
        };

        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);

        return button;
    }

    // Adjust color saturation
    private Color adjustSaturation(Color color, float factor) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[1] = Math.max(0, Math.min(1, hsb[1] * factor));
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]), color.getAlpha(), factor);
    }

    private void addCategoryBox(String category) {
        JPanel categoryBox = new JPanel(new BorderLayout());
        categoryBox.setOpaque(false);

        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        categoryLabel.setForeground(Color.BLACK);
        categoryLabel.setHorizontalAlignment(JLabel.CENTER);
        categoryLabel.setVerticalAlignment(JLabel.CENTER);

        // Create semi-transparent background panel with rounded corners
        JPanel backgroundPanel = new RoundedPanel(15) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (category.equals(selectedCategory)) {
                    g.setColor(new Color(30, 144, 255, 180));
                } else {
                    g.setColor(new Color(255, 255, 255, 180));
                }
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArcWidth(), getArcHeight());
            }
        };

        backgroundPanel.setOpaque(false);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(categoryLabel, BorderLayout.CENTER);

        // Add mouse hover effect
        categoryBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (!category.equals(selectedCategory)) {
                    ((RoundedPanel) backgroundPanel).setBorderColor(new Color(30, 144, 255));
                    categoryLabel.setForeground(new Color(30, 144, 255));
                }
                backgroundPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (!category.equals(selectedCategory)) {
                    ((RoundedPanel) backgroundPanel).setBorderColor(new Color(70, 130, 180));
                    categoryLabel.setForeground(Color.BLACK);
                }
                backgroundPanel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                // Clear previous selection
                if (selectedCategory != null) {
                    refreshData(); // Simple way: redraw all categories
                }

                // Set new selection
                selectedCategory = category;
                ((RoundedPanel) backgroundPanel).setBorderColor(new Color(30, 144, 255));
                categoryLabel.setForeground(new Color(30, 144, 255));
                backgroundPanel.repaint();
            }
        });

        categoryBox.add(backgroundPanel, BorderLayout.CENTER);
        categoriesContainer.add(categoryBox);
    }

    // Get currently selected category
    private String getSelectedCategory() {
        return selectedCategory;
    }

    private void addCategory() {
        String newCategory = JOptionPane.showInputDialog(this, "Enter new category name:");
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            if (categoryManager.getCategories().contains(newCategory.trim())) {
                JOptionPane.showMessageDialog(this, "Category already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            categoryManager.addCategory(newCategory.trim());
            categoryManager.saveCategoriesToFile(Constants.getCategoriesFilePath(currentUser.getId()));
            selectedCategory = null; // Clear selection
            ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
            JOptionPane.showMessageDialog(this, "Category added successfully!");
        }
    }

    private void deleteCategory() {
        String selectedCategory = getSelectedCategory();
        if (selectedCategory != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Confirm delete category: '" + selectedCategory + "'?\nAll related transactions will be removed!",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                categoryManager.deleteCategory(selectedCategory);
                transactionManager.deleteTransactionsByCategory(selectedCategory);
                categoryManager.saveCategoriesToFile(Constants.getCategoriesFilePath(currentUser.getId()));
                this.selectedCategory = null; // Clear selection
                ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
                JOptionPane.showMessageDialog(this, "Category deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category first!", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateCategory() {
        String oldCategory = getSelectedCategory();
        if (oldCategory != null) {
            String newCategory = JOptionPane.showInputDialog(this, "Enter new category name:", oldCategory);
            if (newCategory != null && !newCategory.trim().isEmpty() && !newCategory.equals(oldCategory)) {
                if (categoryManager.getCategories().contains(newCategory.trim())) {
                    JOptionPane.showMessageDialog(this, "Category already exists!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                categoryManager.updateCategory(oldCategory, newCategory.trim());
                transactionManager.updateTransactionsCategory(oldCategory, newCategory.trim());
                categoryManager.saveCategoriesToFile(Constants.getCategoriesFilePath(currentUser.getId()));
                selectedCategory = newCategory.trim(); // Update selection
                ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
                JOptionPane.showMessageDialog(this, "Category updated successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category first!", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Custom rounded panel class
    private static class RoundedPanel extends JPanel {
        private int arcWidth = 20;
        private int arcHeight = 20;
        private Color borderColor = new Color(70, 130, 180);

        public RoundedPanel(int radius) {
            this.arcWidth = radius;
            this.arcHeight = radius;
            setOpaque(false);
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw border
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        }

        public int getArcWidth() {
            return arcWidth;
        }

        public int getArcHeight() {
            return arcHeight;
        }
    }
}
