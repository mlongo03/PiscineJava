package edu.Roma42.chat.models;

import edu.Roma42.chat.models.Message;
import edu.Roma42.chat.models.User;

import java.util.*;

public class Chatroom {

	private long id;
	private String name;
	private User owner;
	private List<Message> messages;

	public Chatroom(long id, String name, User owner, List<Message> messages) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.messages = messages;
	}

	public long getID() {
		return (this.id);
	}

	public String getname() {
		return (this.name);
	}

	public User getowner() {
		return (this.owner);
	}

	public List<Message> getmessages() {
		return (this.messages);
	}

	@Override
	public boolean equals(Object Chatroom) {

		if (this == Chatroom) {
			return (true);
		}

		if (Chatroom == null || getClass() != Chatroom.getClass()) {
			return (false);
		}

		Chatroom tmp = (Chatroom) Chatroom;

		if (this.id == tmp.getID() && this.owner.equals(tmp.getowner())
			&& this.messages.equals(tmp.getmessages()) && this.name.equals(tmp.getname())) {
			return (true);
		}
		return (false);
	}

	@Override
	public int hashCode() {
		return (Objects.hash(this.id, this.owner, this.name, this.messages));
	}

	@Override
	public String toString() {
		return ("Chatroom name : " + this.name + ", owner : " + this.owner.getlogin());
	}

}
