import java.sql.*;

public class DatabaseManager {
    private static Connection c = null;

    // Initialize the connection to the database (Singleton pattern)
    public static void initializeConnection() throws SQLException {
        if (c == null) {
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres", "postgres", "ast0r401");
            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException("Error while initializing the connection", e);
            }
        }
    }

    // Get the existing connection (returns the connection)
    public static Connection getConnection() throws SQLException {
        if (c == null) {
            initializeConnection();
        }
        return c;
    }

    // Close the connection (Singleton pattern)
    public static void closeConnection() throws SQLException {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                c = null; // Reset the connection to null after closing it
            }
        }
    }
}
