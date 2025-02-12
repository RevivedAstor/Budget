package utils;

import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUtils {
    public static int findIDByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username =?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String findUserByID(int id) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Not found";
    }

    public static boolean usernameExists(String username) {
        // SQL query to check if a username exists in the database
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setString(1, username);  // Set the username parameter in the query
            ResultSet rs = stmt.executeQuery();

            // If the result contains a value greater than 0, the username exists
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }

        return false;  // If any error occurs or no match is found, return false
    }
}
