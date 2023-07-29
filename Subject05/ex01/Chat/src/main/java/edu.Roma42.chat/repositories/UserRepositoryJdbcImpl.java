package edu.Roma42.chat.repositories;

import java.sql.Connection;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.User;

public class UserRepositoryJdbcImpl implements UserRepository {

	HikariDataSource dataSource;
	ChatroomRepositoryJdbcImpl chatroomRepo;

	public UserRepositoryJdbcImpl(HikariDataSource ds) {

		this.dataSource = ds;
		chatroomRepo = new ChatroomRepositoryJdbcImpl(ds);
	}

	@Override
	public Optional<User> findById(Long id) {

		String query = "SELECT * FROM chat.user WHERE id = ?";

		try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);

		ps.setLong(1, id);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				User user = mapUser(rs);
				return (Optional.of(user));
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return (Optional.empty());
	}

	public User mapUser(ResultSet rs) {

		ArrayList<Chatroom> own_room = null;
		ArrayList<Chatroom> room = null;
		Long id = null;
		String login = null;
		String password = null;
		try {
			id = rs.getLong("id");
			login = rs.getString("login");
			password = rs.getString("password");
			Array ownChatroomsArray = rs.getArray("own_chatrooms");
			if (ownChatroomsArray != null) {
				Long[] ownChatrooms = (Long[]) ownChatroomsArray.getArray();
				own_room = new ArrayList<Chatroom>();
				for (Long ids : ownChatrooms) {
					Optional<Chatroom> optionalChatroom = chatroomRepo.findById(ids);
        			if (optionalChatroom.isPresent()) {
     			       Chatroom chatroom = optionalChatroom.get();
    	   			   room.add(chatroom);
					}
				}
			}
			Array rooms = rs.getArray("Chatrooms");
			if (rooms != null) {
				Long[] Chatrooms = (Long[]) rooms.getArray();
				room = new ArrayList<Chatroom>();
				for (Long ids : Chatrooms) {
					Optional<Chatroom> optionalChatroom = chatroomRepo.findById(ids);
        			if (optionalChatroom.isPresent()) {
      		   		    Chatroom chatroom = optionalChatroom.get();
    		        	room.add(chatroom);
					}
				}
			}
		} catch (Exception e) {

		}
		return (new User (id, login, password, own_room, room));
	}
}
