package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import java.util.EnumSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestDealer {
	CardsInGame stapel;
	@Before
	public void setUp() {
		stapel = new CardsInGame();
		new Dealer(stapel);
	}
}