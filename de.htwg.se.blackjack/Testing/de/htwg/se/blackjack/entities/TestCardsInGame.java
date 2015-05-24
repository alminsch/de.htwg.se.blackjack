package de.htwg.se.blackjack.entities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.Card;
import de.htwg.blackjack.entities.CardsInGame;


public class TestCardsInGame {
	
	private CardsInGame cig;
	
	@Before
	public void setUp()  {
		cig = new CardsInGame();
	}
	@Test
	public void testgetCard() {
		Card c = cig.getCard();
		assertFalse(cig.containscard(c));
	}
}
