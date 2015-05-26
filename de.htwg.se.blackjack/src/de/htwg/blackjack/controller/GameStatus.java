package de.htwg.blackjack.controller;

public class GameStatus {

	private int valueplayer;
	private int valuedealer;

	public GameStatus(int dealer, int player) {
		this.valuedealer = dealer;
		this.valueplayer = player;
	}

	public boolean tie() {
		if (valueplayer == valuedealer) {
			return true;
		}
		return false;
	}

	public boolean dealerbusted() {
		if (valuedealer > 21) {
			return true;
		}
		return false;
	}

	public boolean playerbusted() {
		if (valueplayer > 21) {
			return true;
		}
		return false;
	}

	public boolean playerwin() {
		if (valueplayer > valuedealer) {
			return true;
		}
		return false;
	}
}