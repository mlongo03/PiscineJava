package edu.Roma42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.User;
import edu.Roma42.chat.models.Message;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

	HikariDataSource dataSource;
	UserRepositoryJdbcImpl userRepo;
	ChatroomRepositoryJdbcImpl chatroomRepo;

	public MessagesRepositoryJdbcImpl(HikariDataSource ds) {

		this.dataSource = ds;
		userRepo = new UserRepositoryJdbcImpl(ds);
		chatroomRepo = new ChatroomRepositoryJdbcImpl(ds);
	}

	@Override
	public Optional<Message> findById(Long id) {

		String query = "SELECT * FROM chat.user WHERE id = ?";

		try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);

		ps.setLong(1, id);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Message msg = mapMessage(rs);
				return (Optional.of(msg));
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return (Optional.empty());
	}

	public Message mapMessage(ResultSet rs) {

		User user = null;
		Chatroom room = null;
		try {

			Long id = rs.getLong("id");
			Long authorid = rs.getLong("author");
			Long chatroomid = rs.getLong("room");
			String text = rs.getString("text");
			String date = rs.getString("date");
			user = userRepo.findById(authorID).orElseThrow(() -> new SQLException("User not found"));
			room = chatroomRepo.findById(chatroomID).orElseThrow(() -> new SQLException("Chatroom not found"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (new Message(id, author, room, text, date));
	}


}
