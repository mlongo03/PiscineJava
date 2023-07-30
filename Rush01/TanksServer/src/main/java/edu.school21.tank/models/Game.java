package edu.school21.tank.models;

import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Game {

	private boolean start = false;
	private static Map<Socket, Player> tanks = new HashMap<Socket, Player>();
	private int width;
	private int height;
	private Socket[] clients;
	private List<Bullet> bullets;
	private Long id;
	private Player player1;
	private Player player2;
	private Timestamp date;
	private int status = 0;

	public Game(Long id, Player player1, Player player2, Timestamp date, int status) {
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.date = date;
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return (true);
		}

		if (obj == null || getClass() != obj.getClass()) {
			return (false);
		}

		Game tmp = (Game) obj;

		if (this.id == tmp.getID() && this.player1.equals(tmp.getPlayer1()) && this.player2.equals(tmp.getPlayer2())
				&& this.date.equals(tmp.getDate()) && this.status == tmp.getStatus()) {
			return (true);
		}
		return (false);
	}

	@Override
	public int hashCode() {
		return (Objects.hash(this.id, this.player1, this.player2, this.date, this.status));
	}

	@Override
	public String toString() {
		return ("Player{" + "id=" + this.id + ", player1=" + this.player1 + ", player2=" + this.player2 + ", date="
				+ this.date + '}');
	}

	public Long getID() {
		return (this.id);
	}

	public void setID(Long id) {
		this.id = id;
	}

	public Player getPlayer1() {
		return (this.player1);
	}

	public void setPlayer1(Player player) {
		this.player1 = player;
	}

	public Player getPlayer2() {
		return (this.player2);
	}

	public void setPlayer2(Player player) {
		this.player2 = player;
	}

	public Timestamp getDate() {
		return (this.date);
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getStatus() {
		return (this.status);
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void shoot(Socket sock) {
		Bullet b = new Bullet(this.tanks.get(sock).getPos().x, this.tanks.get(sock).getPos().y + 1);
		new Thread(b).start();
		this.bullets.add(b);
	}

	private static class Bullet implements Runnable {
		public double x;
		public double y;

		public Bullet(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void run() {
			while (this.y > 0) {
				this.y--;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void left(Socket sock) {
			tanks.get(sock).moveLeft();
		}

		public void right(Socket sock) {
			tanks.get(sock).moveRight();
		}

		public void down(Socket sock) {
			tanks.get(sock).moveDown();
		}

		public void Up(Socket sock) {
			tanks.get(sock).moveUp();
		}

	}

}
