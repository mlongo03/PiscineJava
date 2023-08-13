package edu.Roma42.chat.repositories;

import java.sql.Connection;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.User;

public class UserRepositoryJdbcImpl implements UserRepository {

    private Map<Long, User> userCache = new HashMap<>();
	private ChatroomRepositoryJdbcImpl chatroomRepo;
	private Connection connection;

	public UserRepositoryJdbcImpl(HikariDataSource dataSource, ChatroomRepositoryJdbcImpl room) {

		this.chatroomRepo = room;
		try {
			this.connection = dataSource.getConnection();
		} catch (Exception e) {
			System.out.println("error during connection");
		}
	}

	public void setChatroomRepo(ChatroomRepositoryJdbcImpl room) {
		this.chatroomRepo = room;
	}

    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM chat.user LIMIT ? OFFSET ?";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, size);
			ps.setInt(2, page * size);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					User user = mapUser(rs);
					users.add(user);
				}
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
	}

	@Override
	public Optional<User> findById(Long id) {

		if (userCache.containsKey(id)) {
            return Optional.of(userCache.get(id));
        }

		User placeholderUser = new User(id, null, null, null, null);
        userCache.put(id, placeholderUser);

		String query = "SELECT * FROM chat.user WHERE id = ?";

		try {

		PreparedStatement ps = this.connection.prepareStatement(query);

		ps.setLong(1, id);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				User user = mapUser(rs);
            	userCache.put(id, user);
				return (Optional.of(user));
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return (Optional.empty());
	}

	public User mapUser(ResultSet rs) {

		Long id = null;
		String login = null;
		String password = null;
		ArrayList<Chatroom> own_rooms = null;
		ArrayList<Chatroom> rooms = null;

		try {
			id = rs.getLong("id");
			login = rs.getString("login");
			password = rs.getString("password");
			own_rooms = getOwnChatroomsForUser(id);
			rooms = getChatroomsForUser(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (new User (id, login, password, own_rooms, rooms));
	}


	public ArrayList<Chatroom> getOwnChatroomsForUser(long UserId) {
		ArrayList<Chatroom> chats = new ArrayList<>();

        String sql = "SELECT id FROM chat.chatroom WHERE owner = ?";
        try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setLong(1, UserId);

            try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Long roomId = resultSet.getLong("id");
                    Optional<Chatroom> chatroomOptional = chatroomRepo.findById(roomId);
                    if (chatroomOptional.isPresent()) {
                        Chatroom chatroom = chatroomOptional.get();
                        chats.add(chatroom);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
    	}

        return chats;
    }

    public ArrayList<Chatroom> getChatroomsForUser(long userId) {
        Set<Chatroom> chatroomsSet = new HashSet<>();

        String sql = "SELECT room FROM chat.message WHERE author = ?";
        try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
					Long roomId = resultSet.getLong("room");
                    Optional<Chatroom> chatroomOptional = chatroomRepo.findById(roomId);
                    if (chatroomOptional.isPresent()) {
                        Chatroom chatroom = chatroomOptional.get();
                        chatroomsSet.add(chatroom);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(chatroomsSet);
    }
}
