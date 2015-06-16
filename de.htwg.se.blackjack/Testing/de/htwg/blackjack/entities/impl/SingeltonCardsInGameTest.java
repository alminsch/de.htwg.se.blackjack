package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.CardsInGame;


public class SingeltonCardsInGameTest {
	
	private CardsInGame cig;
	Card card;
	
	@Before
	public void setUp()  {
		cig = new CardsInGame();
	}
	
	@Test
	public void testGetCard() {
		assertFalse(cig.containscard(cig.getCard()));
	}
}