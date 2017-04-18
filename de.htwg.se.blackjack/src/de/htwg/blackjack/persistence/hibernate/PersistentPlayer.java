package de.htwg.blackjack.persistence.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class PersistentPlayer {
	
	@Id
	@Column(name = "name")
	private String playername;
	
	@Column(name = "budget")
	private int budget;

	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	public void setPlayerName(String playername) {
		this.playername = playername;
	}

	public int getBudget() {
		return this.budget;
	}

	public String getPlayerName() {
		return this.playername;
	}
}
