import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CategoryManagerTest {
    private CategoryManager categoryManager;
    private String testFilePath = "test_categories.txt";

    @Before
    public void setUp() {
        categoryManager = new CategoryManager();
        // Delete previous test file to ensure clean test environment
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testAddCategory() {
        String category = "Test Category";
        categoryManager.addCategory(category);
        assertTrue("Category should be added successfully",
                categoryManager.getCategories().contains(category));
    }

    @Test
    public void testDeleteCategory() {
        String category = "Test Category";
        categoryManager.addCategory(category);
        categoryManager.deleteCategory(category);
        assertFalse("Category should be deleted successfully",
                categoryManager.getCategories().contains(category));
    }

    @Test
    public void testUpdateCategory() {
        String oldCategory = "Old Category";
        String newCategory = "New Category";
        categoryManager.addCategory(oldCategory);
        categoryManager.updateCategory(oldCategory, newCategory);
        assertFalse("Old category should not exist after update",
                categoryManager.getCategories().contains(oldCategory));
        assertTrue("New category should be updated successfully",
                categoryManager.getCategories().contains(newCategory));
    }

    @Test
    public void testSaveAndLoadCategories() {
        String category1 = "Category 1";
        String category2 = "Category 2";
        categoryManager.addCategory(category1);
        categoryManager.addCategory(category2);
        categoryManager.saveCategoriesToFile(testFilePath);

        CategoryManager newCategoryManager = new CategoryManager();
        assertTrue("Loading from existing file should succeed",
                newCategoryManager.loadCategoriesFromFile(testFilePath));

        List<String> newCategories = newCategoryManager.getCategories();
        assertTrue("Loaded categories should contain Category 1",
                newCategories.contains(category1));
        assertTrue("Loaded categories should contain Category 2",
                newCategories.contains(category2));
    }

    @Test
    public void testLoadCategoriesFromNonExistentFile() {
        String nonExistentFilePath = "non_existent_file.txt";
        assertFalse("Loading from non-existent file should fail",
                categoryManager.loadCategoriesFromFile(nonExistentFilePath));
    }

    @Test
    public void testSaveCategoriesToFileContent() throws IOException {
        String category1 = "Category 1";
        String category2 = "Category 2";
        categoryManager.addCategory(category1);
        categoryManager.addCategory(category2);
        categoryManager.saveCategoriesToFile(testFilePath);

        List<String> lines = Files.readAllLines(new File(testFilePath).toPath());
        assertTrue("File content should contain Category 1",
                lines.contains(category1));
        assertTrue("File content should contain Category 2",
                lines.contains(category2));
    }
}
