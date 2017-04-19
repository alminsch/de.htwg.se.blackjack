package de.htwg.blackjack.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class PersistentPlayer extends CouchDbDocument {

	private static final long serialVersionUID = 1420942051690621270L;

	@TypeDiscriminator
	private String playername;
	
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