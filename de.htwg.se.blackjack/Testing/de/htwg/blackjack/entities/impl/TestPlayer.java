package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.AbstractParticipant;
import junit.framework.TestCase;

public class TestPlayer {

	private List<Card> cardsinhand;
    private int[] handvalue;
    private CardsInGame stapel;
    private boolean stand;
    private String playername;
    Player player1;

	@Before
	public void setUp() {
		player1 = new Player(stapel, "Hans Wurst");
		player1.addplayeraction(PlayerAction.STAND);
		player1.addplayeraction(PlayerAction.HIT);
	}

	@Test
	public void Testgetplayername() {
		assertEquals("Hans Wurst", player1.getplayername());
    }

	@Test
    public void Testaddplayeraction() {
		player1.deleteplayeraction(PlayerAction.HIT);
    }

	@Test
    public void deleteplayeraction() {
		player1.addplayeraction(PlayerAction.STAND);
    }

	@Test
    public void Testgetpossibleplayeractions() {;
		assertTrue(player1.getpossibleplayeractions().contains(PlayerAction.STAND));
    }

	@Test
    public void Testaddtobudget() {
    }

	@Test
    public void Testdeletefrombudget() {
    }

	@Test
    public void Testgetbudget() {
		player1.addtobudget(10);
		player1.deletefrombudget(10);
		assertEquals(1500, player1.getbudget());
    }
}