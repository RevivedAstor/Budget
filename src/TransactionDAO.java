import java.sql.*;


public class TransactionDAO implements Description{

    //TODO: remove username and user userID
    public static boolean addTransaction(boolean type, double amount, String description) {
        String sql = "INSERT INTO transactions (userID, type, amount, description) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection()) {
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, Session.getCurrentID());
            pstmt.setBoolean(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, description);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //To THINK: Maybe I would need this function for filtering transaction by date
    public static void description() {
        StringBuilder sb = new StringBuilder();

    }

    public static void listTransactions(int userID) { //TODO: use userID instead of username
        String sql = "SELECT date, type, amount, description FROM transactions WHERE userID =?";

        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

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
