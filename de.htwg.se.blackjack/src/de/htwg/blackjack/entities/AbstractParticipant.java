package de.htwg.blackjack.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractParticipant implements IParticipant {
	
	private List<Card> cardsinhand;
	private int[] handvalue = new int[2];
	private CardsInGame stapel;
	private boolean stand;
	
	public AbstractParticipant(CardsInGame c) {
		this.cardsinhand = new ArrayList<Card>();
		this.handvalue[0] = 0;
		this.handvalue[1] = 0;
		this.stapel = c;
		this.stand = false;
	}
	public int[] getHandValue() {
		return this.handvalue;
	}
	
	public List<Card> getCardsInHand() {
		return this.cardsinhand;
	}
	public void Hit() {
		Card c  = stapel.getCard();
		cardsinhand.add(c);
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