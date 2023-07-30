package edu.school21.tank.models;

import java.util.Objects;

public class Player {

	private Long id;
	private Long shots = 0L;
	private Long hits = 0L;
	private Long misses = 0L;
	private int health = 100;
	private Pos position;

	public class Pos {
		public double x;
		public double y;
	}

	public Player(Long id, Long shots, Long hits, Long misses) {
		this.id = id;
		this.shots = shots;
		this.hits = hits;
		this.misses = misses;
	}

	public int getHealth() {
		return this.health;
	}

	public Pos getPos() {
		return this.position;
	}

	public void getDmg() {
		this.health -= 5;
	}

	public void moveLeft() {
		this.position.x -= 5;
	}

	public void moveRight() {
		this.position.x += 5;
	}

	public void moveUp() {
		this.position.y -= 5;
	}

	public void moveDown() {
		this.position.y += 5;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return (true);
		}

		if (obj == null || getClass() != obj.getClass()) {
			return (false);
		}

		Player tmp = (Player) obj;

		if (this.id == tmp.getID() && this.shots.equals(tmp.getShots()) && this.hits.equals(tmp.getHits())
				&& this.misses.equals(tmp.getMisses())) {
			return (true);
		}
		return (false);
	}

	@Override
	public int hashCode() {
		return (Objects.hash(this.id, this.shots, this.hits, this.misses));
	}

	@Override
	public String toString() {
		return ("Player{" + "id=" + this.id + ", shots=" + this.shots + ", hits=" + this.hits + ", misses="
				+ this.misses + '}');
	}

	public Long getID() {
		return (this.id);
	}

	public void setID(Long id) {
		this.id = id;
	}

	public Long getShots() {
		return (this.shots);
	}

	public void setShots(Long shots) {
		this.shots = shots;
	}

	public Long getHits() {
		return (this.hits);
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Long getMisses() {
		return (this.misses);
	}

	public void setMisses(Long misses) {
		this.misses = misses;
	}
}
