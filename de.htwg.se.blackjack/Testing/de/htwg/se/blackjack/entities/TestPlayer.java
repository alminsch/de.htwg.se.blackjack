package de.htwg.se.blackjack.entities;

import java.util.ArrayList;

import junit.framework.TestCase;

public class TestPlayer extends TestCase {
	
	private CardsInGame stapel = new CardsInGame();
	
	public void testConstructor() {
		
		Player d = new Player(stapel);
		
		assertEquals(new ArrayList<Card>(),d.playerhand);
		assertEquals(0,d.handvalue[0]);
		assertEquals(0,d.handvalue[1]);
		assertEquals(stapel,d.stapel);
	}
	
	public void testgetHandValue() {
		
		Player d = new Player(stapel);
		
		assertEquals(d.handvalue,d.getHandValue());
	}
	
	public void testgetPlayerHand() {
		Player d = new Player(stapel);
		
		assertEquals(d.playerhand,d.getPlayerHand());
	}
	
	public void testPlayerHit() {
		
		
	}

}
