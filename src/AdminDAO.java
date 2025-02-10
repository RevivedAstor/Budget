import java.sql.*;

public class AdminDAO {
    public static void viewAllUsers() {
        String sql = "SELECT id, username, email, balance FROM users ORDER BY id ASC";
        try (Connection c = DatabaseManager.getConnection();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int userID = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");

                System.out.println("ID: " + userID +
                                    " | Username: " + username +
                                    " | Email: " + email +
                                    " | Balance: " + balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewUser(int userID) {
        String sql = "SELECT username, email, balance FROM users WHERE id =?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");

                System.out.println("ID: " + userID +
                        " | Username: " + username +
                        " | Email: " + email +
                        " | Balance: " + balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewUserTransactions(int userID) {
        TransactionDAO.listTransactions(userID);
    }

    public static void deleteUser(int userID) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
