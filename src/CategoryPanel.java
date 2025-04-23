import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel implements MainGUI.RefreshablePanel {
    private final User currentUser;
    private final TransactionManager transactionManager;
    private final CategoryManager categoryManager;

    private DefaultListModel<String> listModel;
    private JList<String> categoryList;

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
        listModel.clear();
        categoryManager.getCategories().forEach(listModel::addElement);
    }

    private void initializeComponents() {
        // Initialize category list
        listModel = new DefaultListModel<>();
        categoryManager.getCategories().forEach(listModel::addElement);

        categoryList = new JList<>(listModel);
        categoryList.setFont(new Font("Arial", Font.BOLD, 14)); // Changed font
        categoryList.setForeground(Color.BLACK);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setBackground(Color.WHITE);
        categoryList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                setForeground(isSelected ? Color.WHITE : Color.BLACK);
                setFont(getFont().deriveFont(Font.BOLD));
                setBackground(isSelected ? new Color(70, 130, 180) : Color.WHITE);
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(categoryList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Existing Categories")); // Modified
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Action buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton addButton = new JButton("Add Category"); // Modified
        JButton deleteButton = new JButton("Delete Selected"); // Modified
        JButton updateButton = new JButton("Edit Category"); // Modified

        // Button styling
        for (JButton button : new JButton[] { addButton, deleteButton, updateButton }) {
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Changed font
            button.setForeground(Color.BLACK);
            button.setBackground(new Color(255, 255, 255, 200));
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(70, 130, 180)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        }

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        // Event handlers
        addButton.addActionListener(e -> addCategory());
        deleteButton.addActionListener(e -> deleteCategory());
        updateButton.addActionListener(e -> updateCategory());

        // Layout configuration
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void addCategory() {
        String newCategory = JOptionPane.showInputDialog(this, "Enter new category name:"); // Modified
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            if (categoryManager.getCategories().contains(newCategory.trim())) {
                JOptionPane.showMessageDialog(this, "Category already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            categoryManager.addCategory(newCategory.trim());
            categoryManager.saveCategoriesToFile(Constants.getCategoriesFilePath(currentUser.getId()));
            ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
            JOptionPane.showMessageDialog(this, "Category added successfully!"); // Modified
        }
    }

    private void deleteCategory() {
        String selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Confirm delete category: '" + selectedCategory + "'?\nAll related transactions will be removed!", // Modified
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION); // Modified
            if (confirm == JOptionPane.YES_OPTION) {
                categoryManager.deleteCategory(selectedCategory);
                transactionManager.deleteTransactionsByCategory(selectedCategory);
                categoryManager.saveCategoriesToFile(Constants.getCategoriesFilePath(currentUser.getId()));
                ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
                JOptionPane.showMessageDialog(this, "Category deleted successfully!"); // Modified
            }
        }
    }

    private void updateCategory() {
        String oldCategory = categoryList.getSelectedValue();
        if (oldCategory != null) {
            String newCategory = JOptionPane.showInputDialog(this, "Enter new category name:", oldCategory); // Modified
            if (newCategory != null && !newCategory.trim().isEmpty() && !newCategory.equals(oldCategory)) {
                if (categoryManager.getCategories().contains(newCategory.trim())) {
                    JOptionPane.showMessageDialog(this, "Category already exists!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                categoryManager.updateCategory(oldCategory, newCategory.trim());
                transactionManager.updateTransactionsCategory(oldCategory, newCategory.trim());
                categoryManager.saveCategoriesToFile(Constants.getCategoriesFilePath(currentUser.getId()));
                ((MainGUI) SwingUtilities.getWindowAncestor(this)).refreshAllData();
                JOptionPane.showMessageDialog(this, "Category updated successfully!"); // Modified
            }
        }
    }
}
