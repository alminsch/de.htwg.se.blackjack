package de.htwg.se.blackjack.entities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class CardsInGame {
    private ArrayList<Cards> stapel;
    private Random randomGenerator;

    public CardsInGame() {
        stapel = new ArrayList<Cards>(EnumSet.allOf(Cards.class));
    }

    public Cards getCard() {
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(stapel.size());
        Cards randcard = stapel.get(index);
        stapel.remove(index);
        return randcard;
    }
    
}
