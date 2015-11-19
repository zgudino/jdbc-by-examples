import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");

            try(Connection connection = DriverManager.getConnection("jdbc:sqlite:devel.db");
                Statement statement = connection.createStatement()) {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                String dropTableQuery = "DROP TABLE Users";
                String createTableQuery = "CREATE TABLE Users (" +
                        "id INTEGER PRIMARY KEY ASC, " +
                        "firstName CHAR(512), " +
                        "lastName CHAR(512) " +
                        ")";
                boolean tableExists = false;

                try(ResultSet tableMetaData = databaseMetaData.getTables(null, null, "Users", null)) {
                    while (tableMetaData.next()) {
                        tableExists = tableMetaData.getString(3).contentEquals("Users");
                    }
                }

                if (tableExists) {
                    statement.executeUpdate(dropTableQuery);
                    statement.executeUpdate(createTableQuery);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
