package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;


public class TestDealer {
	Dealer dealer;
	CardsInGame cig;

	@Before
	public void setUp() {
		cig = new CardsInGame();
		dealer = new Dealer(cig);
	}
}
