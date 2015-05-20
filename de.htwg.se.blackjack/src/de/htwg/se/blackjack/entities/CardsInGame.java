package de.htwg.se.blackjack.entities;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by BSB on 19.05.2015.
 */
public class CardsInGame {
    private ArrayList<Card> stapel;
    private Random randomGenerator;

    public CardsInGame() {
        stapel = new ArrayList<Card>();
        for (Value v : Value.values()) {
        	for(Suit s : Suit.values()) {
        		stapel.add(new Card(v,s));
        	}
        }
    }

    public Card getCard() {
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(stapel.size());
        Card randcard = stapel.get(index);
        stapel.remove(index);
        return randcard;
    }
    
}
