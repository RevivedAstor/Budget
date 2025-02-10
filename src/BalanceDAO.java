import java.sql.*;

public class BalanceDAO {
    public static double getBalance() {
        String sql = "SELECT balance FROM users WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, Session.getCurrentID());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // If no balance found
    }

    //Maybe making so that this function would check for whether the balance is negative?
    public static void changeBalance(double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);  // The amount to add or subtract
            pstmt.setInt(2, Session.getCurrentID());     // The user ID

            int rowsAffected = pstmt.executeUpdate();
            // rowsAffected > 0;     // Returns true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return false;  // If something went wrong
    }

    public static void changeBalance(int id, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);  // The amount to add or subtract
            pstmt.setInt(2, id);     // The user ID

            int rowsAffected = pstmt.executeUpdate();
            // rowsAffected > 0;     // Returns true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return false;  // If something went wrong
    }




//Util

//These functions take the entirety of all the transactions made
    public static double negativeSum(int userID) {
        String sql = "SELECT SUM(CASE WHEN amount < 0 THEN amount END) AS balance FROM transactions WHERE userID = ?";
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
        String sql = "SELECT SUM(CASE WHEN amount > 0 THEN amount END) AS balance FROM transactions WHERE userID = ?";
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

//Whilst these take only the range specified
public static double negativeSum(int userID, Date startDate, Date endDate) {
    String sql = "SELECT SUM(CASE WHEN amount < 0 THEN amount END) AS balance FROM transactions WHERE userID = ? AND date BETWEEN ? AND ?";
    try (Connection c = DatabaseManager.getConnection();
         PreparedStatement pstmt = c.prepareStatement(sql)) {

        pstmt.setInt(1, userID);
        pstmt.setDate(2, startDate);
        pstmt.setDate(3, endDate);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getDouble("balance");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0; // If no balance found
    }

    public static double positiveSum(int userID, Date startDate, Date endDate) {
        String sql = "SELECT SUM(CASE WHEN amount > 0 THEN amount END) AS balance FROM transactions WHERE userID = ? AND date BETWEEN ? AND ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);
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
