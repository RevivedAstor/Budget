import java.sql.*;

public class UserDAO {

    //Register function
    public static boolean register(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Login function
    public static boolean login(String username, String password) {
        String sql = "SELECT username, email FROM users WHERE username =? AND password =?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username.trim());
            statement.setString(2, password.trim());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String rUsername = rs.getString("username");
                    String rEmail = rs.getString("email");
                    Session.setCurrentUser(rUsername, rEmail);
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
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next(); // Returns true if the username exists in the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
