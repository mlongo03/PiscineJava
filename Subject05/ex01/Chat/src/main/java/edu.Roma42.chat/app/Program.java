package edu.Roma42.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;
import edu.Roma42.chat.repositories.*;
import edu.Roma42.chat.models.Message;

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
            Connection con = null;
            try {
                con = dataSource.getConnection();
            } catch (Exception e) {
                System.out.println("error during connection");
            }
            UserRepositoryJdbcImpl userRepo = new UserRepositoryJdbcImpl(con, null);
            ChatroomRepositoryJdbcImpl chatroomRepo = new ChatroomRepositoryJdbcImpl(con, null, userRepo);
            MessagesRepositoryJdbcImpl messageRepo = new MessagesRepositoryJdbcImpl(con, userRepo, chatroomRepo);

            userRepo.setChatroomRepo(chatroomRepo);
            chatroomRepo.setUserRepo(userRepo);

            Optional<Message> ms = messageRepo.findById(1L);
            if (ms.isPresent()) {
                msgs = ms.get();
            }
            System.out.println(msgs);
            dataSource.close();

    }
}

