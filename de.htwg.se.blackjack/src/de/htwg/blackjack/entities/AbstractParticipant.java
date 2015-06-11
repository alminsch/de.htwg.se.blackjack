package de.htwg.blackjack.entities;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.SingletonCardsInGame;

public abstract class AbstractParticipant implements IParticipant {

    private List<Card> cardsinhand;
    private int[] handvalue;
    private SingletonCardsInGame scig;
    private boolean stand;

    public AbstractParticipant() {
        this.cardsinhand = new ArrayList<Card>();
        this.handvalue = new int[2];
        this.handvalue[0] = 0;
        this.handvalue[1] = 0;
        this.scig = SingletonCardsInGame.getInstance();
        this.stand = false;
    }

    public int[] getHandValue() {
        return this.handvalue;
    }

    public boolean getstand() {
        return stand;
    }

    public void setstand(boolean stand) {
        this.stand = stand;
    }

    public List<Card> getCardsInHand() {
        return this.cardsinhand;
    }

    public void actionhit() {
        Card c = scig.getCard();
        cardsinhand.add(c);
        if (c.name().contains("ASS")) {
            if ((this.handvalue[1] + 11) > 21) {
                this.handvalue[0] = this.handvalue[0] + 1;
                this.handvalue[1] = this.handvalue[0] + 11;
            } else {
                this.handvalue[0] = this.handvalue[0] + 1;
                this.handvalue[1] = this.handvalue[1] + 11;
            }
        } else {
            this.handvalue[0] = this.handvalue[0] + c.getCardValue();
            this.handvalue[1] = this.handvalue[1] + c.getCardValue();
        }
    }

    public void actionstand() {
    	setstand(true);
    }
}
