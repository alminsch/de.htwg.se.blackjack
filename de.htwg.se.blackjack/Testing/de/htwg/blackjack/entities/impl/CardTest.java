package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.impl.Card;

public class CardTest{
	Card c;

	@Before
	public void setUp() {
		c = Card.HERZACHT;
	}

	@Test
	public void testGetCardValue() {
		assertEquals(8, c.getCardValue());
	}
}