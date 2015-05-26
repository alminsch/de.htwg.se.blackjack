package de.htwg.blackjack.entities.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class CardsInGame {
	private List<Card> stapel;
	private Random randomGenerator;

	public CardsInGame() {
		stapel = new ArrayList<Card>(EnumSet.allOf(Card.class));
	}

	public boolean containscard(Card c) {
		return stapel.contains(c);
	}

	public Card getCard() {
		randomGenerator = new Random();
		int index = randomGenerator.nextInt(stapel.size());
		Card randcard = stapel.get(index);
		stapel.remove(index);
		return randcard;
	}
}