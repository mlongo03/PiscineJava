package edu.Roma42.chat.repositories;

import java.sql.Connection;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

		String query = "SELECT * FROM chat.User WHERE id = ?";

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
		}

		return (new User (id, login, password, own_rooms, rooms));
	}


	public ArrayList<Chatroom> getOwnChatroomsForUser(long UserId) {
        ArrayList<Chatroom> chats = new ArrayList<>();

        String sql = "SELECT id FROM Chatroom WHERE owner = ?";
        try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
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

        String sql = "SELECT room FROM Message WHERE author = ?";
        try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
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
