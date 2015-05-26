package de.htwg.blackjack.entities.impl;

import java.util.List;

import de.htwg.blackjack.controller.PlayerAction;
import de.htwg.blackjack.entities.AbstractParticipant;

public class Player extends AbstractParticipant {
	
	private int budget;
	private String playername;
	private List<PlayerAction> possibleplayeractions;
	
	public Player(CardsInGame c, String playername) {
		super(c);
		this.playername = playername;
	}
	
	private void addplayeraction(PlayerAction action) {
		this.possibleplayeractions.add(action);
	}
	
	private void deleteplayeraction(PlayerAction action) {
		this.possibleplayeractions.remove(action);
	}
	
	private List<PlayerAction> getpossibleplayeractions() {
		return this.possibleplayeractions;
	}
	
	private void addtobudget(int money) {
		this.budget += money;
	}
	
	private void deletefrombudget(int money) {
		this.budget -= money;
	}
	
	private int getbudget() {
		return this.budget;
	}
}
