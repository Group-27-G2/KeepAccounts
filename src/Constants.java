public class Constants {
    public static final String USERS_FILE = "data/users.txt";

    public static String getTransactionsFilePath(String userId) {
        return "data/transactions_" + userId + ".txt";
    }
  
    public static String getCategoriesFilePath(String userId) {
        return "data/categories_" + userId + ".txt";
    }
}