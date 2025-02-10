import java.sql.*;


public class TransactionDAO implements Description{

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

    public static boolean addTransaction(int id, double amount, String tags) {
        String sql = "INSERT INTO transactions (userID, amount, tags) VALUES (?, ?, ?)";
        try (Connection c = DatabaseManager.getConnection()) {
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, tags);

            int rowsAffected = pstmt.executeUpdate();

            BalanceDAO.changeBalance(id, amount);

            return rowsAffected > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean TransferTo(int idReceiver, double amount) {
        int id = Session.getCurrentID();
        addTransaction(id, -amount, "Sending to "+idReceiver);
        return addTransaction(idReceiver, amount, "Receiving from " + id);
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
