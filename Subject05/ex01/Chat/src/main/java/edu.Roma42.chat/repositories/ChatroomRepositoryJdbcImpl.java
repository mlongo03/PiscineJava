package edu.Roma42.chat.repositories;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.repositories.ChatroomRepositoryJdbcImpl;
import edu.Roma42.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.Roma42.chat.models.User;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {

	HikariDataSource dataSource;
	UserRepositoryJdbcImpl userRepo;
	MessagesRepositoryJdbcImpl messageRepo;

	public ChatroomRepositoryJdbcImpl(HikariDataSource ds) {

		this.dataSource = ds;
		this.userRepo = new UserRepositoryJdbcImpl(ds);
		this.chatroomRepo = new ChatroomRepositoryJdbcImpl(ds);
	}

	@Override
	public Optional<Chatroom> findById(Long id) {

		String query = "SELECT * FROM chat.user WHERE id = ?";

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

		User owner = null;
		ArrayList<Message> msgs = null;
		Long id = null;
		String name = null;
		try {
			id = rs.getLong("id");
			name = rs.getString("name");
			Long ownerID = rs.getLong("owner");
			owner = userRepo.findById(ownerID).orElseThrow(() -> new SQLException("owner not found"));
			Array messages = rs.getArray("messages");
			if (messages != null) {
				Long[] mess = (Long[]) messages.getArray();
				msgs = new ArrayList<Chatroom>();
				for (Long ids : msgs) {
					own_room.add(messageRepo.findById(ids));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (new Chatroom(id, name, owner, msgs));
	}


}
