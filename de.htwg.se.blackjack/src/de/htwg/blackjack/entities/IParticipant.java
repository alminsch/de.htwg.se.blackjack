package de.htwg.blackjack.entities;

import java.util.List;

public interface IParticipant {
	
	int[] getHandValue();
	
	List<Card> getCardsInHand();
	
	void Hit();
	
}
