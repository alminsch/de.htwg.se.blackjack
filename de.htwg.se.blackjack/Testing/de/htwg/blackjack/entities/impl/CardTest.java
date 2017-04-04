package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;
import org.junit.Test;
import de.htwg.blackjack.entities.impl.Card;

public class CardTest{
	Card c;


	@Test
	public void testGetCardValue() {
		assertEquals(2, Card.HERZZWEI.getCardValue());
	}
	
	@Test
	public void testenumCard() {
		Card.valueOf("HERZZWEI");
	}
}
