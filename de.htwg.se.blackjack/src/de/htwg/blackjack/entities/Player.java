package de.htwg.blackjack.entities;

import java.util.List;

import de.htwg.blackjack.controller.Actions;

public class Player extends AbstractParticipant {
	
	private int budget;
	private List<Actions> possibleplayeractions;
	
	public Player(CardsInGame c) {
		super(c);
	}
	
	private void addtobudget(int money) {
		this.budget += money;
	}
	
	private void removefrombudget(int money) {
		this.budget -= money;
	}
	
	private int getbudget() {
		return this.budget;
	}
}
