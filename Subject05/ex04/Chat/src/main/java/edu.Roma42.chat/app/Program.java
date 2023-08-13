package edu.Roma42.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.*;
import edu.Roma42.chat.repositories.*;
import edu.Roma42.chat.models.*;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("alessiolongo");
        config.setPassword("test");

        HikariDataSource dataSource = new HikariDataSource(config);

        String fileName = "./target/classes/schema.sql";
        File sqlFile = new File(fileName);
        StringBuilder sqlStatements = new StringBuilder();
        String datafileName = "./target/classes/data.sql";
        File datasqlFile = new File(datafileName);
        StringBuilder datasqlStatements = new StringBuilder();

        try (Scanner scanner = new Scanner(sqlFile)) {
            while (scanner.hasNextLine()) {
                sqlStatements.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            e.printStackTrace();
            return;
        }

        try (Scanner scanner = new Scanner(datasqlFile)) {
            while (scanner.hasNextLine()) {
                datasqlStatements.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + datafileName);
            e.printStackTrace();
            return;
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sqlStatements.toString());
            statement.execute(datasqlStatements.toString());
            System.out.println("SQL statements executed successfully!");
        } catch (SQLException e) {
            System.err.println("Error executing SQL statements:");
            e.printStackTrace();
        }

            Message msgs = null;
            UserRepositoryJdbcImpl userRepo = new UserRepositoryJdbcImpl(dataSource, null);
            ChatroomRepositoryJdbcImpl chatroomRepo = new ChatroomRepositoryJdbcImpl(dataSource, null, userRepo);
            MessagesRepositoryJdbcImpl messageRepo = new MessagesRepositoryJdbcImpl(dataSource, userRepo, chatroomRepo);

            userRepo.setChatroomRepo(chatroomRepo);
            chatroomRepo.setUserRepo(userRepo);

            List<User> users = userRepo.findAll(0, 5);
            for(User us : users) {
                System.out.println("Username : " + us.getlogin() + ", User id : " + us.getID());
            }
    }
}

