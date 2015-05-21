package de.htwg.se.blackjack.entities;

import java.util.ArrayList;

public class Player {
	
	private ArrayList<Card> playerhand;
	private int[] handvalue = new int[2];
	private CardsInGame stapel;
	//private int budget;
	
	public Player(CardsInGame c) {
		this.playerhand = new ArrayList<Card>();
		this.handvalue[0] = 0;
		this.handvalue[1] = 0;
		this.stapel = c;
	}
	
	public int[] getHandValue() {
		return this.handvalue;
	}
	
	public ArrayList<Card> getPlayerHand() {
		return this.playerhand;
	}
	
	public void playerHit() {
		Card c  = stapel.getCard();
		playerhand.add(c);
		
		switch (c.getValue()) {
		case Ace:
			this.handvalue[0] = this.handvalue[0]+1;
			this.handvalue[1] = this.handvalue[1]+11;
			break;
		case Two:
			this.handvalue[0] = this.handvalue[0]+2;
			this.handvalue[1] = this.handvalue[1]+2;
			break;
		case Three:
			this.handvalue[0] = this.handvalue[0]+3;
			this.handvalue[1] = this.handvalue[1]+3;
			break;
		case Four:
			this.handvalue[0] = this.handvalue[0]+4;
			this.handvalue[1] = this.handvalue[1]+4;
			break;
		case Five:
			this.handvalue[0] = this.handvalue[0]+5;
			this.handvalue[1] = this.handvalue[1]+5;
			break;
		case Six:
			this.handvalue[0] = this.handvalue[0]+6;
			this.handvalue[1] = this.handvalue[1]+6;
			break;
		case Seven:
			this.handvalue[0] = this.handvalue[0]+7;
			this.handvalue[1] = this.handvalue[1]+7;
			break;
		case Eight:
			this.handvalue[0] = this.handvalue[0]+8;
			this.handvalue[1] = this.handvalue[1]+8;
			break;
		case Nine:
			this.handvalue[0] = this.handvalue[0]+9;
			this.handvalue[1] = this.handvalue[1]+9;
			break;
		case Ten:
			this.handvalue[0] = this.handvalue[0]+10;
			this.handvalue[1] = this.handvalue[1]+10;
			break;
		case Jack:
			this.handvalue[0] = this.handvalue[0]+10;
			this.handvalue[1] = this.handvalue[1]+10;	
			break;
		case Queen:
			this.handvalue[0] = this.handvalue[0]+10;
			this.handvalue[1] = this.handvalue[1]+10;	
			break;
		case King:
			this.handvalue[0] = this.handvalue[0]+10;
			this.handvalue[1] = this.handvalue[1]+10;	
			break;
		}
		
	}
}
