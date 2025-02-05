public class Session {
    private static String currentUsername = null;
    private static String currentEmail = null;
    private static int currentID;
    private static String currentPassword;

    public static void setCurrentUser(int id, String username, String email, String password) {
        currentID = id;
        currentUsername = username;
        currentEmail = email;
        currentPassword = password;
    }

    public static int getCurrentID() {return currentID;}

    public static String getCurrentUsername() {return currentUsername;}

    public static String getCurrentEmail() {return currentEmail;}

    public static String getCurrentPassword() {return currentPassword;}

    public static void clearSession() {
        currentID = 0;
        currentUsername = null;
        currentEmail = null;
        currentPassword = null;
    }
}
