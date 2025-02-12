package dao;

import database.DatabaseManager;
import utils.Session;

import java.sql.*;


public class TransactionDAO implements Description {

    //TODO: remove username and user userID
    public static boolean addTransaction(double amount, String tag) {
        String sql = "INSERT INTO transactions (userID, amount, tags) VALUES (?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection()) {
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, Session.getCurrentID());
            pstmt.setDouble(2, amount);
            pstmt.setString(3, tag);

            int rowsAffected = pstmt.executeUpdate();

            BalanceDAO.changeBalance(amount);

            return rowsAffected > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addTransaction(int id, double amount, String tag) {
        String sql = "INSERT INTO transactions (userID, amount, tags) VALUES (?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, tag);

            int rowsAffected = pstmt.executeUpdate();
            BalanceDAO.changeBalance(id, amount);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean TransferTo(int idReceiver, double amount) {
        int id = Session.getCurrentID();
        addTransaction(id, -amount, "Sending to "+idReceiver);
        return addTransaction(idReceiver, amount, "Receiving from " + id);
    }

    public static void listTransactions(int userID) {
        String sql = "SELECT date, amount, tags FROM transactions WHERE userID =?";

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                double amount = rs.getDouble("amount");
                String tags = rs.getString("tags");


                // Print formatted transaction details
                System.out.printf("%s | %.2f | %s%n", date, amount, tags);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listTransactions(int userID, Date startDate, Date endDate) {
        String sql = "SELECT * FROM transactions WHERE userID = ? AND date BETWEEN ? AND ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                double amount = rs.getDouble("amount");
                String tags = rs.getString("tags");


                // Print formatted transaction details
                System.out.printf("%s | %.2f | %s%n", date, amount, tags);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
