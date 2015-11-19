import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");

            try(Connection connection = DriverManager.getConnection("jdbc:sqlite:devel.db");
                Statement statement = connection.createStatement()
            ) {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                boolean tableExists = false;

                // Metadatos de conexion
                try(ResultSet tableMetaData = databaseMetaData.getTables(null, null, "Users", null)) {
                    while (tableMetaData.next()) {
                        tableExists = tableMetaData.getString(3).contentEquals("Users");
                    }
                }
                // Tumbar tabla `Users`
                if (tableExists) {
                    statement.executeUpdate("DROP TABLE Users");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
