package de.htwg.se.blackjack.entities;

public class Card {
	
	private Value value;
	private Suit suit;
	
	public Card (Value v, Suit s) {
		this.value = v;
		this.suit = s;
	}
	
	public Value getValue() {
		return this.value;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.suit + " " + this.value).toString();
	}

}
