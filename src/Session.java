public class Session {
    private static String currentUsername = null;
    private static String currentEmail = null;

    public static void setCurrentUser(String username, String email) {
        currentUsername = username;
        currentEmail = email;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static String getCurrentEmail() {
        return currentEmail;
    }

    public static void clearSession() {
        currentUsername = null;
        currentEmail = null;
    }
}
