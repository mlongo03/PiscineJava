package edu.school21.tank.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import edu.school21.tank.models.Player;
import edu.school21.tank.repositories.GameRepository;
import edu.school21.tank.repositories.PlayerRepository;
import edu.school21.tank.repositories.PlayerRepositoryImpl;
import edu.school21.tank.repositories.GameRepositoryImpl;
import edu.school21.tank.server.Server;

public interface Main {
    final static String connectionString = "jdbc:postgresql://localhost:5432/postgres";
    final static String user = "alessiolongo";
    final static String pass = "test";

    public static void main(String[] args) {
        DBConnection connection = new DBConnection(connectionString, user, pass);
        connection.connect();
        connection.process("schema.sql");

        PlayerRepository PL = new PlayerRepositoryImpl(connection.getConnection());
        GameRepository gRepo = new GameRepositoryImpl(connection.getConnection(), PL);
        // Player player1 = new Player(0L, 0L, 0L, 0L);
        // PL.save(player1);
        new Server(4242, gRepo, PL);
    }
}
