import java.sql.*;

public class BalanceDAO {
    public static double getBalance(int userID) {
        String sql = "SELECT SUM(CASE WHEN type = false THEN amount ELSE -amount END) AS balance FROM transactions WHERE userID = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // If no balance found
    }

    public static double negativeSum(int userID) {
        String sql = "SELECT SUM(CASE WHEN type = true THEN -amount END) AS balance FROM transactions WHERE userID = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // If no balance found
    }

    public static double positiveSum(int userID) {
        String sql = "SELECT SUM(CASE WHEN type = false THEN amount END) AS balance FROM transactions WHERE userID = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // If no balance found
    }
}
