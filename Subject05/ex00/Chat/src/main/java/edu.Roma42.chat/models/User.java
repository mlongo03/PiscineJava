package edu.Roma42.chat.models;

import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.Message;

import java.util.*;

public class User {

	private long id;
	private String login;
	private String password;
	private List<Chatroom> own_chatrooms;
	private List<Chatroom> chatrooms;

	public User(long id, String login, String password, List<Chatroom> own_chatrooms, List<Chatroom> chatrooms) {

		this.id = id;
		this.login = login;
		this.password = password;
		this.own_chatrooms = own_chatrooms;
		this.chatrooms = chatrooms;
	}

	public long getID() {
		return (this.id);
	}

	public String getlogin() {
		return (this.login);
	}

	public String getpassword() {
		return (this.password);
	}

	public List<Chatroom> get_own_chatrooms() {
		return (this.own_chatrooms);
	}

	public List<Chatroom> getchatrooms() {
		return (this.chatrooms);
	}

	@Override
	public boolean equals(Object user) {
		User tmp = (User) user;
		if (this == user) {
			return (true);
		}
		if (this.id == tmp.getID() && this.login.equals(tmp.getlogin())
			&& this.password.equals(tmp.getpassword()) && this.own_chatrooms.equals(tmp.get_own_chatrooms())
			&& this.chatrooms.equals(tmp.getchatrooms())) {
			return (true);
		}
		return (false);
	}

	@Override
	public int hashCode() {
		return (Objects.hash(this.id, this.login, this.password, this.own_chatrooms, this.chatrooms));
	}

	@Override
	public String toString() {
		return ("Username : " + this.login + ", User id : " + this.id);
	}
}
