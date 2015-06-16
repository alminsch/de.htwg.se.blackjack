package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.AbstractParticipant;
import junit.framework.TestCase;

public class PlayerTest {

    Player player1;

	@Before
	public void setUp() {
		player1 = new Player("Hans Wurst");
		player1.addplayeraction(PossibleAction.STAND);
		player1.addplayeraction(PossibleAction.HIT);
	}

	@Test
	public void Testgetplayername() {
		assertEquals("Hans Wurst", player1.getplayername());
    }

	@Test
    public void Testaddplayeraction() {
		player1.deleteplayeraction(PossibleAction.HIT);
    }

	@Test
    public void deleteplayeraction() {
		player1.addplayeraction(PossibleAction.STAND);
    }

	@Test
    public void Testgetpossibleplayeractions() {;
		assertTrue(player1.getpossibleplayeractions().contains(PossibleAction.STAND));
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