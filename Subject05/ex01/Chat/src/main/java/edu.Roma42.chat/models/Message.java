package edu.Roma42.chat.models;

import edu.Roma42.chat.models.Chatroom;
import edu.Roma42.chat.models.User;

import java.util.*;

public class Message {

	private long id;
	private User author;
	private Chatroom room;
	private String text;
	private String date;

	public Message(long id, User author, Chatroom room, String text, String date) {

		this.id = id;
		this.author = author;
		this.room = room;
		this.text = text;
		this.date = date;
	}

	public long getID() {
		return (this.id);
	}

	public Chatroom getroom() {
		return (this.room);
	}

	public User getauthor() {
		return (this.author);
	}

	public String gettext() {
		return (this.text);
	}

	public String getdate() {
		return (this.date);
	}

	@Override
	public boolean equals(Object message) {

		if (this == message) {
			return (true);
		}

		if (message == null || getClass() != message.getClass()) {
			return (false);
		}

		Message tmp = (Message) message;

		if (this.id == tmp.getID() && this.author.equals(tmp.getauthor())
			&& this.room.equals(tmp.getroom()) && this.date.equals(tmp.getdate())
			&& this.text.equals(tmp.gettext())) {
			return (true);
		}
		return (false);
	}

	@Override
	public int hashCode() {
		return (Objects.hash(this.id, this.author, this.date, this.room, this.text));
	}

	@Override
	public String toString() {
		return ("Message text: " + this.text + ", author : " + this.author.getlogin() + ", chatroom : " + this.room.getname());
	}
}
