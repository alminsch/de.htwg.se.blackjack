package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.impl.Card;


public class SingeltonCardsInGameTest {

	private SingletonCardsInGame cig;
	Card card;

	@Before
	public void setUp()  {
		cig = SingletonCardsInGame.getInstance();
		cig.resetStapel();
	}

	@Test
	public void testGetCard() {
		card = cig.getCard();
		assertFalse(cig.containscard(card));
	}
}
