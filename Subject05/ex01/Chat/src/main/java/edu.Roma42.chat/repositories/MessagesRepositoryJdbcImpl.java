package edu.Roma42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		String query = "SELECT * FROM chat.Message WHERE id = ?";

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

		String text = null;
		String date = null;
		Long id = null;
		User author = null;
		Chatroom room = null;
		try {

			id = rs.getLong("id");
			Long authorID = rs.getLong("author");
			Long chatroomID = rs.getLong("room");
			text = rs.getString("text");
			date = rs.getString("date");
			Optional<User> userOptional = userRepo.findById(authorID);
            if (userOptional.isPresent()) {
                author = userOptional.get();
            }
			Optional<Chatroom> chatroomOptional = chatroomRepo.findById(chatroomID);
			if (chatroomOptional.isPresent()) {
				room = chatroomOptional.get();
			}
		} catch (Exception e) {
		}
		return (new Message(id, author, room, text, date));
	}


}
