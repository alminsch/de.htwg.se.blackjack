package de.htwg.blackjack.controller;

public class GameStatus {
	
	private int valueplayer;
	private int valuedealer;
	public GameStatus(int dealer, int player) {
		this.valuedealer = dealer;
		this.valueplayer = player;
	}
	
	public boolean tie() {
		if(valueplayer == valuedealer) {
			return true;
		}
		return false;	
	}
	
	public boolean bust() {
		if(valueplayer < valuedealer) {
			return true;
		}
		return false;
	}
	
	public boolean win() {
		if(valueplayer > valuedealer) {
			return true;
		}
		return false;
	}
}
