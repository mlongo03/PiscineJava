package edu.school21.tank.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.zaxxer.hikari.HikariDataSource;

import edu.school21.tank.models.Player;
import edu.school21.tank.repositories.PlayerRepository;

public class PlayerRepositoryImpl implements PlayerRepository {

    private Connection connection;

    public PlayerRepositoryImpl(Connection con) {
        this.connection = con;
    }

    @Override
    public void save(Player player) {

        String query = "INSERT INTO tank.player (shots, hits, misses) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, player.getShots());
            ps.setInt(2, player.getHits());
            ps.setInt(3, player.getMisses());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    player.setID(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating player failed, no ID obtained.");
                }
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Error saving player", ex);
        }
    }

    @Override
    public void update(Player player) {

        String query = "UPDATE tank.player SET " + "shots = ?, hits = ?, misses = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, player.getShots());
            ps.setInt(2, player.getHits());
            ps.setInt(3, player.getMisses());
            ps.setLong(4, player.getID());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DataAccessException("No rows matched the update criteria");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error updating player", ex);
        }
    }

    @Override
    public Optional<Player> findById(Long id) {

        String query = "SELECT * FROM tank.player WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Player player = mapPlayer(rs);
                    return (Optional.of(player));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (Optional.empty());
    }

    private Player mapPlayer(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        int shots = rs.getInt("shots");
        int hits = rs.getInt("hits");
        int misses = rs.getInt("misses");

        return (new Player(id, shots, hits, misses));
    }
}