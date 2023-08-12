package edu.Roma42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Optional;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.User;
import edu.Roma42.chat.models.Message;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private Map<Long, Message> messageCache = new HashMap<>();
	private UserRepositoryJdbcImpl userRepo;
	private ChatroomRepositoryJdbcImpl chatroomRepo;
	private Connection connection;

	public MessagesRepositoryJdbcImpl(Connection con, UserRepositoryJdbcImpl us, ChatroomRepositoryJdbcImpl room) {

		this.connection = con;
		this.userRepo = us;
		this.chatroomRepo = room;
	}

	public void setUserRepo(UserRepositoryJdbcImpl us) {
		this.userRepo = us;
	}

	public void setChatroomRepo(ChatroomRepositoryJdbcImpl room) {
		this.chatroomRepo = room;
	}

	@Override
	public Optional<Message> findById(Long id) {

		if (messageCache.containsKey(id)) {
            return Optional.of(messageCache.get(id));
        }

		Message placeholderMessage = new Message(id, null, null, null, null);
        messageCache.put(id, placeholderMessage);

		String query = "SELECT * FROM chat.message WHERE id = ?";

		try {

		PreparedStatement ps = this.connection.prepareStatement(query);

		ps.setLong(1, id);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Message msg = mapMessage(rs);
        		messageCache.put(id, msg);
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
