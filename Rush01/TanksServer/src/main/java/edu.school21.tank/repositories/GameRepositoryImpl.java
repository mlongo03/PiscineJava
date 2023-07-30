package edu.school21.tank.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;

import com.zaxxer.hikari.HikariDataSource;

import edu.school21.tank.models.Game;
import edu.school21.tank.models.Player;

public class GameRepositoryImpl implements GameRepository {

	private Connection connection;

	private PlayerRepository playerRepo;

	public GameRepositoryImpl(Connection con, PlayerRepository pRepo) {
		this.connection = con;
		this.playerRepo = pRepo;
	}

	@Override
	public void save(Game game) {

		String query = "INSERT INTO tank.game (player1, player2, date, status) VALUES (?,?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, game.getPlayer1().getID());
			if (game.getPlayer2() == null)
				ps.setNull(2, Types.BIGINT);
			else
				ps.setLong(2, game.getPlayer2().getID());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, game.getStatus());
			ps.executeUpdate();
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

				if (generatedKeys.next()) {
					game.setID(generatedKeys.getLong(1));

				} else {
					throw new SQLException("Creating player failed, no ID obtained.");
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataAccessException("Error saving game", ex);
		}
	}

	@Override
	public void update(Game game) {

		String query = "UPDATE tank.game SET " + "player1 = ?, player2 = ?, date = ?, status = ? WHERE id = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, game.getPlayer1().getID());
			ps.setLong(2, game.getPlayer2().getID());
			ps.setTimestamp(3, new Timestamp(game.getDate().getTime()));
			ps.setInt(4, game.getStatus());
			ps.setLong(5, game.getID());

			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated == 0) {
				throw new DataAccessException("No rows matched the update criteria");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("Error updating player", ex);
		}
	}

	@Override
	public Optional<Game> findById(Long id) {

		String query = "SELECT * FROM tank.game WHERE id = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					Game game = mapGame(rs);
					return (Optional.of(game));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Optional.empty());
	}

	public Optional<Game> findWaitingGame() {

		String query = "SELECT * FROM tank.game WHERE status = 0";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					Game game = mapGame(rs);
					return (Optional.of(game));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Optional.empty());
	}

	private Game mapGame(ResultSet rs) {
		Player player1 = null;
		Player player2 = null;
		Long id = 0L;
		Timestamp date = new Timestamp(System.currentTimeMillis());
		int status = 0;
		try {
			id = rs.getLong("id");
			date = rs.getTimestamp("date");
			Long player1Id = rs.getLong("player1");
			Long player2Id = rs.getLong("player2");
			status = rs.getInt("status");
			player1 = playerRepo.findById(player1Id).orElseThrow(() -> new SQLException("Player not found"));
			player2 = playerRepo.findById(player2Id).orElseThrow(() -> new SQLException("Player not found"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new Game(id, player1, player2, date, status);
	}
}