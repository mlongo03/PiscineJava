package edu.Roma42.chat.repositories;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.Message;
import edu.Roma42.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.Roma42.chat.repositories.UserRepositoryJdbcImpl;
import edu.Roma42.chat.models.User;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {

	HikariDataSource dataSource;
	UserRepositoryJdbcImpl userRepo;
	MessagesRepositoryJdbcImpl messageRepo;

	public ChatroomRepositoryJdbcImpl(HikariDataSource ds) {

		this.dataSource = ds;
		this.userRepo = new UserRepositoryJdbcImpl(ds);
		this.messageRepo = new MessagesRepositoryJdbcImpl(ds);
	}

	@Override
	public Optional<Chatroom> findById(Long id) {

		String query = "SELECT * FROM chat.Chatroom WHERE id = ?";

		try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);

		ps.setLong(1, id);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Chatroom chat = mapChatroom(rs);
				return (Optional.of(chat));
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return (Optional.empty());
	}

	public Chatroom mapChatroom(ResultSet rs) {

		Long id = null;
		String name = null;
		User  owner = null;
		ArrayList<Message> msgs = null;

		try {
			id = rs.getLong("id");
			name = rs.getString("name");
			Long ownerID = rs.getLong("owner");
			Optional<User> userOptional = userRepo.findById(ownerID);
            if (userOptional.isPresent()) {
                owner = userOptional.get();
            }
			msgs = getMessagesForChatroom(id);
		} catch (SQLException e){
		}
		return (new Chatroom(id, name, owner, msgs));
	}


   public ArrayList<Message> getMessagesForChatroom(long chatroomId) {
        ArrayList<Message> messages = new ArrayList<>();

        String sql = "SELECT id, author, text, date FROM Message WHERE room = ?";
		try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatroomId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long messageId = resultSet.getLong("id");
                    Long authorId = resultSet.getLong("author");
                    String text = resultSet.getString("text");
                    String date = resultSet.getString("date");
					Optional<User> userOptional = userRepo.findById(authorId);
					User author = null;
					if (userOptional.isPresent()) {
                    	author = userOptional.get();
                    }
					Optional<Chatroom> chatroomOptional = this.findById(chatroomId);
					Chatroom chatroom = null;
					if (chatroomOptional.isPresent()) {
                        chatroom = chatroomOptional.get();
                    }

                    Message message = new Message(messageId, author, chatroom, text, date);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
