package de.htwg.blackjack.controller.impl;

import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
import static org.junit.Assert.*;

public class ControllerTest {
	int displaybet;
	Controller controller1;
	Controller controller2;
	Player player;

	@Before
	public void setUp() {
		controller1 = new Controller();
		controller2 = new Controller();
		controller1.startnewround();
		controller2.startnewround();
		controller1.createnewgame();
		controller1.setbetforround();

		controller1.addnewPlayer("Testplayer");
		controller1.addnewPlayer("Testplayer1");
		controller1.addnewPlayer("Testplayer2");
		controller1.addnewPlayer("Testplayer3");
		controller1.player = controller1.getPlayerList().get(0);
	}

	@Test
	public void Testcreatenewgame() {
		controller1.status = GameStatus.DURING_BET;
		controller1.setbetforround();
	}

	@Test
	public void Testcheckplayerbudget() {
		controller1.status = GameStatus.DURING_BET;
		controller1.increasebet();
	}

	@Test
	public void Teststartnewround() {
		controller1.startnewround();
		controller2.startnewround();
	}

	@Test
	public void Testplayerbets() {
		controller2.playerbets();
	}

	@Test
	public void Testsetbetforround() {
		controller1.status = GameStatus.DURING_BET;
		System.out.println(controller1.status);
		controller1.setbetforround();
	}

	@Test
	public void TestsetTotalPlayerbet() {
		System.out.println(controller1.player);
		controller1.setTotalPlayerbet(100);

	}

	@Test
	public void TestgetDealer() {
		controller1.getDealer();

	}


}
