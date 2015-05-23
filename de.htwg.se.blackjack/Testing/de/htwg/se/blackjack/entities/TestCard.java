package de.htwg.se.blackjack.entities;

import org.junit.Test;
import junit.framework.TestCase;


public class TestCard extends TestCase {
	
	@Test
	public void testgetCardValue() {
		assertEquals(Card.HERZZWEI.getCardValue(), 2);
	}
}
