package de.htwg.se.blackjack.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.Card;

public class TestCard{
	Card card;
	
	@Before
	public void setUp() {
		card = Card.HERZACHT;
	}
	
	@Test
	public void testGetCardValue() {
		assertEquals(8, card.getCardValue());
	}
}
