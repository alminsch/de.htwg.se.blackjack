package de.htwg.blackjack.view.tui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import de.htwg.blackjack.controller.impl.Controller;

public class TextUITest {

	private Controller controller1;
	private TextUI tui1;

	static Logger logger = Logger.getLogger(TextUITest.class);

	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		tui1 = new TextUI(controller1);
		tui1.userinputselection("n");
		assertEquals("Es müssen Spieler erstellt werden, bevor das Spiel gestartet werden kann",controller1.getStatus());
		controller1.addnewPlayer("Hans");
		controller1.startnewround();
	}

	@Test
	public void teststatusq() {
		tui1.userinputselection("007");
		assertFalse(tui1.userinputselection("q"));
	}

	@Test
	public void test() {

		tui1.userinputselection("+");
		assertEquals("Wette:  200",controller1.getStatus());

		tui1.userinputselection("-");
		assertEquals("Wette:  100",controller1.getStatus());

		tui1.userinputselection("sb");
		assertEquals("Spieler Hans, ist an der Reihe",controller1.getStatus());

		tui1.userinputselection("np");
		System.out.println(controller1.getStatus());
		assertEquals("Spieler können nur zu Beginn einer neuen Runde erstellt werden",controller1.getStatus());

		tui1.userinputselection("h");
		assertEquals("Hans  HIT",controller1.getStatus());

		tui1.userinputselection("s");
	}
}