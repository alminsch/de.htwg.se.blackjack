package de.htwg.blackjack.entities;

import java.util.List;

import de.htwg.blackjack.entities.impl.Card;

public interface IParticipant {

    int[] getHandValue();

    List<Card> getCardsInHand();

    Card actionhit();
    
    String getName();
    
    int getCardValue();

}
