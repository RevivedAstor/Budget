import java.sql.*;

public class DatabaseManager {
    private static Connection c = null;

    public static Connection getConnection() throws SQLException {
        // Check if the connection is open. If not, re-establish the connection
        if (c == null || c.isClosed()) {
            initializeConnection();
        }
        return c;
    }

    public static void initializeConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");

            // Set the connection to a static member
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "ast0r401");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to initialize the connection.", e);

        }
    }

    public static void closeConnection() throws SQLException {
        if (c != null && !c.isClosed()) {
            c.close();
        }
    }
}

