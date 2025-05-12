import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private List<String> categories;

    public CategoryManager() {
        categories = new ArrayList<>();
    }

    public void addCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public void deleteCategory(String category) {
        categories.remove(category);
    }

    public void updateCategory(String oldCategory, String newCategory) {
        int index = categories.indexOf(oldCategory);
        if (index != -1) {
            categories.set(index, newCategory);
        }
    }

    public List<String> getCategories() {
        return categories;
    }

    public void saveCategoriesToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String category : categories) {
                writer.write(category + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loadCategoriesFromFile(String filePath) {
        categories.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                categories.add(line.trim());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
