package de.htwg.blackjack.controller.impl;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;



import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
public class ControllerTest {
	Queue<Player> playerlist = new LinkedList<Player>();
	
	Player a = new Player("Test");
	Controller c;
	
	@Before
	public void setUp() {
		c = new Controller();
		c.addnewPlayer("Alex");
		c.addnewPlayer("Benny");
		c.getPlayerList().get(0).deletefrombudget(1500);
		c.createnewgame();
		c.startnewround();
		c.increasebet();
		c.decreasebet();
		c.decreasebet();
		c.setbetforround();
		c.allgettwocards();
		c.getDealer().handvalue[0] = 16;
		c.getDealer().handvalue[1] = 16;
		c.auswertung();
		c.getDealerCards();
	}
	
	@Test
	public void testStuff() {
		Controller d = new Controller();
		d.createnewgame();
		d.startnewround();
	}

	@Test
	public void teststartnewround() {
		Controller d = new Controller();
		d.addnewPlayer("Alex");
		d.getPlayerList().get(0).deletefrombudget(1400);
		d.createnewgame();
		d.startnewround();
		d.increasebet();
	}
	
	@Test
	public void testplayerbets() {
		Controller d = new Controller();
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.setGameStatus(GameStatus.AUSWERTUNG);
		d.setbetforround();
	}
	
	@Test
	public void testauswertung1() {
		Controller d = new Controller();
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getDealer().handvalue[0] = 18;
		d.getDealer().handvalue[1] = 18;
		d.getPlayerList().get(0).handvalue[0] = 18;
		d.getPlayerList().get(0).handvalue[1] = 18;
		d.auswertung();
		d.setGameStatus(GameStatus.DURING_TURN);
		d.stand();
		d.setGameStatus(GameStatus.DURING_TURN);
		d.playerhit();
	}
	
	@Test
	public void testauswertung2() {
		Controller d = new Controller();
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getDealer().handvalue[0] = 16;
		d.getDealer().handvalue[1] = 18;
		d.getPlayerList().get(0).handvalue[0] = 19;
		d.getPlayerList().get(0).handvalue[1] = 19;
		d.auswertung();
	}
	
	@Test
	public void testauswertung3() {
		Controller d = new Controller();
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getDealer().handvalue[0] = 18;
		d.getDealer().handvalue[1] = 16;
		d.getPlayerList().get(0).handvalue[0] = 15;
		d.getPlayerList().get(0).handvalue[1] = 15;
		d.auswertung();
	}
	
	@Test
	public void testplayerhit1() {
		Controller d = new Controller();
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getPlayerList().get(0).handvalue[0] = 1;
		d.getPlayerList().get(0).handvalue[1] = 1;
		d.setGameStatus(GameStatus.DURING_TURN);
		d.playerhit();
	}
	
	@Test
	public void testplayerhit2() {
		Controller d = new Controller();
		d.createnewgame();
		d.addnewPlayer("Alex");
		d.startnewround();
		d.getPlayerList().get(0).handvalue[0] = 20;
		d.getPlayerList().get(0).handvalue[1] = 20;
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
		c.auswertung();
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
	public void testgetPlayerList() {
		c.getPlayerList();
	}
	
	@Test
	public void testclose() {
	}
	
}
