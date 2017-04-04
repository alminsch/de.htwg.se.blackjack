package de.htwg.blackjack.entities.impl;

import de.htwg.blackjack.entities.AbstractParticipant;

public class Dealer extends AbstractParticipant {

    public Dealer() {
        super();
    }

    public String toString(int sel) {

        String newLine = System.getProperty("line.separator");
        String result = newLine;
        result = "Dealer Handkarten: ";

        if(sel == 0) {
            result = result + getCardsInHand().get(0).toString();
            return result;
        }

        for(Card c : getCardsInHand()) {
            result = result + c.toString() + " ";
        }
        return result + "Wert:" + sel;
    }

}
