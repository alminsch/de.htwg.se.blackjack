package de.htwg.blackjack.entities.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class SingletonCardsInGame {
    private static SingletonCardsInGame scig = new SingletonCardsInGame();
    List<Card> stapel = new ArrayList<Card>(EnumSet.allOf(Card.class));
    private Random randomGenerator;

    public SingletonCardsInGame() {}

    public boolean containscard(Card c) {
        return stapel.contains(c);
    }
    public static SingletonCardsInGame getInstance() {
        return scig;
    }

    public void resetStapel() {
        stapel = new ArrayList<Card>(EnumSet.allOf(Card.class));
    }

    public Card getCard() {
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(stapel.size());
        Card randcard = stapel.get(index);
        stapel.remove(index);
        return randcard;
    }
}
