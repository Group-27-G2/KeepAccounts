public class Constants {
    public static final String USERS_FILE = "data/users.txt";

    // Remove the original CATEGORIES_FILE constant
    // The transaction file path is changed to be generated dynamically based on the
    // user ID
    public static String getTransactionsFilePath(String userId) {
        return "data/transactions_" + userId + ".txt";
    }

    // A new method for obtaining the path of the category file has been added,
    // based on the user ID
    public static String getCategoriesFilePath(String userId) {
        return "data/categories_" + userId + ".txt";
    }
}