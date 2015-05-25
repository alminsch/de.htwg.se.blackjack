package de.htwg.blackjack.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestCardsInGame {
	
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
