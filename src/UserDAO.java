import java.sql.*;

public class UserDAO {

    //Register function
    public static boolean register(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Login function
    public static boolean login(String username, String password) {
        String sql = "SELECT id, username, email FROM users WHERE username =? AND password =?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String rUsername = rs.getString("username");
                    String rEmail = rs.getString("email");
                    int rID = rs.getInt("id");
                    Session.setCurrentUser(rID, rUsername, rEmail);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    //This function finds whether a user exists by the username
    public static boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if the username exists in the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
