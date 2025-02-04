import java.sql.*;


public class TransactionDAO {

    public static boolean addTransaction(String username, boolean type, double amount, String description) {
        String sql = "INSERT INTO transactions (userID, username, type, amount, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection()) {
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, Session.getCurrentID());
            pstmt.setString(2, username);
            pstmt.setBoolean(3, type);
            pstmt.setDouble(4, amount);
            pstmt.setString(5, description);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void listTransactions(String username) {
        String sql = "SELECT date, type, amount, description FROM transactions WHERE username = ?";

        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                boolean type = rs.getBoolean("type"); // TRUE = Outcome, FALSE = Income
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");

                //false and true correspond to + and - respectively
                String typeString = type ? "-" : "+";

                // Print formatted transaction details
                System.out.printf("%s | %s%.2f | %s%n", date, typeString, amount, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
