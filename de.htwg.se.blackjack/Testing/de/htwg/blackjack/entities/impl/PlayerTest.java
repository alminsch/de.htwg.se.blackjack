package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PlayerTest {

    Player player1;
    Card c;

	@Before
	public void setUp() {
		player1 = new Player("Hans Wurst");
		player1.addplayeraction(PossibleAction.STAND);
		player1.addplayeraction(PossibleAction.HIT);
		player1.cardsinhand.add(Card.HERZACHT);
		player1.setplayerbet(100);
	}

	@Test
	public void Testgetplayername() {
		assertEquals("Hans Wurst", player1.getPlayerName());
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
	public void Testgetplayerbet() {
		assertEquals(100, player1.getplayerbet());
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

	@Test
	public void Testgetinsurance() {
		player1.setinsurancetrue();
		assertEquals(true, player1.getinsurance());
		player1.setinsurancefalse();
		assertEquals(false, player1.getinsurance());
	}

	@Test
	public void Testactionhit() {
		player1.actionhit();
	}

	@Test
	public void hitsetvalue() {
		player1.hitsetvalue(Card.HERZASS);
		player1.handvalue[1] = 11;
		player1.hitsetvalue(Card.HERZASS);
	}

	@Test
	public void TesttoString() {
		assertEquals("Hans Wurst Handkarten: HERZACHT Wert:0", player1.toString());
	}
}