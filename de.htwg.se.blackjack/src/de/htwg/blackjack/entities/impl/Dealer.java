package de.htwg.blackjack.entities.impl;

import de.htwg.blackjack.entities.AbstractParticipant;

public class Dealer extends AbstractParticipant {

    public Dealer() {
        super();
    }

	public void setHandValueNull() {
		this.handvalue[0] = 0;
		this.handvalue[1] = 0;
	}
	public String toString() {
		String newLine = System.getProperty("line.separator");
	    String result = newLine;
	    result = "Dealer Handkarten:";
		for(Card c : getCardsInHand()) {
			result = result + c.toString() + " ";
		}
    	return result;
    }

}
