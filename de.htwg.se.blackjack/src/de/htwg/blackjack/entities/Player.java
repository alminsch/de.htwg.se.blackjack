package de.htwg.blackjack.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private List<Card> playerhand;
	private int[] handvalue = new int[2];
	private CardsInGame stapel;
	private int budget;
	
	public Player(CardsInGame c) {
		this.playerhand = new ArrayList<Card>();
		this.handvalue[0] = 0;
		this.handvalue[1] = 0;
		this.stapel = c;
		this.budget = 1000;
	}
	
	public int[] getHandValue() {
		return this.handvalue;
	}
	
	public List<Card> getPlayerHand() {
		return this.playerhand;
	}
	
	public void playerHit() {
		Card c  = stapel.getCard();
		playerhand.add(c);
		if(c.name().contains("ASS")) {
			if ((this.handvalue[1] + 11) > 21) {
				this.handvalue[0] = this.handvalue[0] + 1;
				this.handvalue[1] = this.handvalue[0] + 11;
			} else {
				this.handvalue[0] = this.handvalue[0] + 1;
				this.handvalue[1] = this.handvalue[1] + 11;
			}
		} else {
			this.handvalue[0] = this.handvalue[0] + c.getCardValue();
			this.handvalue[1] = this.handvalue[1] + c.getCardValue();
		}
	}
}
