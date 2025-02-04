public class Session {
    private static String currentUsername = null;
    private static String currentEmail = null;
    private static int currentID;

    public static void setCurrentUser(int id, String username, String email) {
        currentID = id;
        currentUsername = username;
        currentEmail = email;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static String getCurrentEmail() {
        return currentEmail;
    }

    public static int getCurrentID() {return currentID;}

    public static void clearSession() {
        currentUsername = null;
        currentEmail = null;
        currentID = 0;
    }
}
