package edu.Roma42.chat.repositories;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
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

    private Map<Long, Chatroom> chatroomCache = new HashMap<>();
	private UserRepositoryJdbcImpl userRepo;
	private MessagesRepositoryJdbcImpl messageRepo;
	private Connection connection;

	public ChatroomRepositoryJdbcImpl(Connection con, MessagesRepositoryJdbcImpl ms, UserRepositoryJdbcImpl us) {

		this.connection = con;
		this.userRepo = us;
		this.messageRepo = ms;
	}

	public void setUserRepo(UserRepositoryJdbcImpl us) {
		this.userRepo = us;
	}

	public void setMessagesRepo(MessagesRepositoryJdbcImpl ms) {
		this.messageRepo = ms;
	}

	@Override
	public Optional<Chatroom> findById(Long id) {

        if (chatroomCache.containsKey(id)) {
            return Optional.of(chatroomCache.get(id));
        }

		Chatroom placeholderChatroom = new Chatroom(id, null, null, null);
        chatroomCache.put(id, placeholderChatroom);

		String query = "SELECT * FROM chat.chatroom WHERE id = ?";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Chatroom chat = mapChatroom(rs);
					chatroomCache.put(id, chat);
					return (Optional.of(chat));
				}
			} catch (Exception e){
				System.out.println("error during mapchatroom");
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

        String sql = "SELECT id, author, text, date FROM chat.message WHERE room = ?";
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
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
