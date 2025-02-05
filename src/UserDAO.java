import java.sql.*;

//Add some polymorphisim, like adding an interface that gives a description function into both DAO functions

public class UserDAO implements Description {

    //Register function
    public static boolean register(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username.trim());
            pstmt.setString(2, email.trim());
            pstmt.setString(3, password.trim());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Login function
    public static boolean login(String username, String password) {
        String sql = "SELECT id, username, email, password FROM users WHERE username =? AND password =?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int rID = rs.getInt("id");
                    String rUsername = rs.getString("username");
                    String rEmail = rs.getString("email");
                    String rPassword = rs.getString("password");
                    Session.setCurrentUser(rID, rUsername, rEmail, rPassword);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void description() {
        StringBuilder sb = new StringBuilder();
        sb.append("Profile Information\n");
        sb.append("Username: ").append(Session.getCurrentUsername()).append("\n");
        sb.append("Email: ").append(Session.getCurrentEmail());

        System.out.println(sb);
    }

    public static boolean updateUsername(String username) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username.trim());
            pstmt.setInt(2, Session.getCurrentID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateEmail(String email) {
        String sql = "UPDATE users SET email = ? WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, email.trim());
            pstmt.setInt(2, Session.getCurrentID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePassword(String password) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, password.trim());
            pstmt.setInt(2, Session.getCurrentID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
