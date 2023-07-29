import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        // HikariCP configuration
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydatabase");
        config.setUsername("myuser");
        config.setPassword("mypassword");

        // Create the HikariDataSource
        HikariDataSource dataSource = new HikariDataSource(config);

        // Read SQL statements from the file
        String fileName = "schema.sql";
        File sqlFile = new File(fileName);
        StringBuilder sqlStatements = new StringBuilder();

        try (Scanner scanner = new Scanner(sqlFile)) {
            while (scanner.hasNextLine()) {
                sqlStatements.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            e.printStackTrace();
            return;
        }

        // Execute SQL statements using HikariCP connection
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sqlStatements.toString());
            System.out.println("SQL statements executed successfully!");
        } catch (SQLException e) {
            System.err.println("Error executing SQL statements:");
            e.printStackTrace();
        } finally {
            // Close the HikariDataSource when the application shuts down
            dataSource.close();
        }
    }
}

