package de.htwg.blackjack.controller.impl;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;



import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.persistence.IPlayersDAO;
import de.htwg.blackjack.persistence.db4o.db4oPlayersDAO;
public class ControllerTest {
	Queue<Player> playerlist = new LinkedList<Player>();

	Player a = new Player("Test");
	Controller c;
	IPlayersDAO playersdao = new db4oPlayersDAO();

	@Before
	public void setUp() {
		c = new Controller(playersdao);
		c.addnewPlayer("Alex");
		c.addnewPlayer("Benny");
		c.getPlayingPlayerList().get(0).deletefrombudget(1500);
		c.createnewgame();
		c.startnewround();
		c.increasebet();
		c.decreasebet();
		c.decreasebet();
		c.setbetforround();
		c.allgettwocards();
		c.getDealer().getHandValue()[0] = 16;
		c.getDealer().getHandValue()[1] = 16;
		c.evaluateRound();
		c.getDealerCards();
	}

	@Test
	public void testStuff() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.startnewround();
		d.playerbets();
		d.setGameStatus(GameStatus.DURING_BET);
		d.addnewPlayer("Test");
		d.setGameStatus(GameStatus.DURING_TURN);
		d.addnewPlayer("Test");
	}

	@Test
	public void teststartnewround() {
		Controller d = new Controller(playersdao);
		d.addnewPlayer("Alex");
		d.getPlayingPlayerList().get(0).deletefrombudget(1400);
		d.createnewgame();
		d.startnewround();
		d.increasebet();
	}

	@Test
	public void testplayerbets() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.increasebet();
		d.decreasebet();
		d.decreasebet();
		d.setGameStatus(GameStatus.AUSWERTUNG);
		d.setbetforround();
	}

	@Test
	public void testauswertung1() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getDealer().getHandValue()[0] = 18;
		d.getDealer().getHandValue()[1] = 18;
		d.getPlayingPlayerList().get(0).getHandValue()[0] = 18;
		d.getPlayingPlayerList().get(0).getHandValue()[1] = 18;
		d.evaluateRound();
		d.setGameStatus(GameStatus.DURING_TURN);
		d.stand();
		d.setGameStatus(GameStatus.DURING_TURN);
		d.playerhit();
	}

	@Test
	public void testauswertung2() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getDealer().getHandValue()[0] = 16;
		d.getDealer().getHandValue()[1] = 18;
		d.getPlayingPlayerList().get(0).getHandValue()[0] = 19;
		d.getPlayingPlayerList().get(0).getHandValue()[1] = 19;
		d.evaluateRound();
	}

	@Test
	public void testauswertung3() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getDealer().getHandValue()[0] = 18;
		d.getDealer().getHandValue()[1] = 16;
		d.getPlayingPlayerList().get(0).getHandValue()[0] = 15;
		d.getPlayingPlayerList().get(0).getHandValue()[1] = 15;
		d.evaluateRound();
	}

	@Test
	public void testplayerhit1() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getPlayingPlayerList().get(0).getHandValue()[0] = 1;
		d.getPlayingPlayerList().get(0).getHandValue()[1] = 1;
		d.setGameStatus(GameStatus.DURING_TURN);
		d.playerhit();
	}

	@Test
	public void testplayerhit2() {
		Controller d = new Controller(playersdao);
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getPlayingPlayerList().get(0).getHandValue()[0] = 20;
		d.getPlayingPlayerList().get(0).getHandValue()[1] = 20;
		d.setGameStatus(GameStatus.DURING_TURN);
		d.playerhit();
	}

	@Test
	public void testsetbetforround() {
		c.setbetforround();
	}

	@Test
	public void testincreasebet() {
		c.increasebet();
	}

	@Test
	public void testdecreasebet() {
		c.decreasebet();
	}

	@Test
	public void testspielzug() {
		c.spielzug();
	}

	@Test
	public void testauswertung() {
		c.evaluateRound();
	}

	@Test
	public void testgetCards() {
		c.getCards();
	}

	@Test
	public void teststand() {
		c.stand();
	}

	@Test
	public void testplayerhit() {
		c.playerhit();
	}

	@Test
	public void testdoublebet() {
		c.doublebet();
	}

	@Test
	public void testgetDisplayBet() {
		c.getDisplayBet();
	}

	@Test
	public void testaddnewplayer() {
		c.addnewPlayer("Alex");
		c.addnewPlayer("Ale");
		c.addnewPlayer("Al");
		c.addnewPlayer("A");
	}

	@Test
	public void testdeletePlayer() {
		c.deletePlayer(a);
	}

	@Test
	public void testgetStatus() {
		c.getStatus();
	}

	@Test
	public void testgetGameStatus() {
		c.getGameStatus();
	}


	@Test
	public void testgetPlayerPlayingList() {
		c.getPlayingPlayerList();
	}

}
