package de.htwg.se.blackjack.entities;

import junit.framework.TestCase;


public class TestCard extends TestCase {
	
	
	public void testConstructor() {
		
		Card d = new Card(Value.Two,Suit.Clubs);
		
		assertEquals(Suit.Clubs,d.getSuit());
		assertEquals(Value.Two,d.getValue());
	}
	
	public void testgetValue() {
		Card d = new Card(Value.Two,Suit.Clubs);
		
		assertEquals(Value.Two,d.getValue());
	}
	
	public void testgetSuit() {
		Card d = new Card(Value.Two,Suit.Clubs);
		
		assertEquals(Suit.Clubs,d.getSuit());
	}
	
	public void testtoString() {
		Card d = new Card(Value.Two,Suit.Clubs);
		
		assertEquals("Clubs Two",d.toString());
	}
	
}
