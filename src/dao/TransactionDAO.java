package dao;

import database.DatabaseManager;
import utils.Session;
import utils.UserUtils;

import java.sql.*;
import java.util.Objects;


public class TransactionDAO implements Description {

    public static boolean addTransaction(double amount, String tag, String description) {
        String sql = "INSERT INTO transactions (userID, amount, tags, description) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection()) {
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, Session.getCurrentID());
            pstmt.setDouble(2, amount);
            pstmt.setString(3, tag);
            pstmt.setString(4, description);

            int rowsAffected = pstmt.executeUpdate();

            BalanceDAO.changeBalance(amount);

            return rowsAffected > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addTransaction(int id, double amount, String tag, String description) {
        String sql = "INSERT INTO transactions (userID, amount, tags, description) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, tag);
            pstmt.setString(4, description);

            int rowsAffected = pstmt.executeUpdate();
            BalanceDAO.changeBalance(id, amount);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean TransferTo(int idReceiver, double amount, String description) {
        int id = Session.getCurrentID();
        addTransaction(id, -amount, "Sending to "+UserUtils.findUserByID(idReceiver), "");
        return addTransaction(idReceiver, amount, "Receiving from " + UserUtils.findUserByID(id), description);
    }

    public static void listTransactions(int userID) {
        String sql = "SELECT date, amount, tags, description FROM transactions WHERE userID =?";

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                double amount = rs.getDouble("amount");
                String tags = rs.getString("tags");
                String description = rs.getString("description");


                if (Objects.equals(description, "")) {
                    System.out.printf("%s | %.2f | %s%n", date, amount, tags);
                } else {
                    System.out.printf("%s | %.2f | %s | %s%n", date, amount, tags, description);
                }

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
