package de.htwg.blackjack.entities.impl;

import org.junit.Test;

import de.htwg.blackjack.entities.impl.GameStatus;

public class GameStatusTest {

	@Test
	public void TestGameStatus() {
		GameStatus.valueOf("AUSWERTUNG");
	}
}