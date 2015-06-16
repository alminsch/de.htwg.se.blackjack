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
}
