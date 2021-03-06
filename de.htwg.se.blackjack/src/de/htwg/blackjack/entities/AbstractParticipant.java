package de.htwg.blackjack.entities;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.SingletonCardsInGame;

public abstract class AbstractParticipant implements IParticipant {

    private List<Card> cardsinhand;
    private int[] handvalue;
    private SingletonCardsInGame scig;


    public AbstractParticipant() {
        this.cardsinhand = new ArrayList<Card>();
        this.handvalue = new int[2];
        resetCardsInHand();
        this.scig = SingletonCardsInGame.getInstance();
    }

    public int[] getHandValue() {
        return this.handvalue;
    }

    public void resetCardsInHand() {
        this.cardsinhand.clear();
        this.handvalue[0] = 0;
        this.handvalue[1] = 0;
    }

    public List<Card> getCardsInHand() {
        return this.cardsinhand;
    }

    public void addtoCards(Card c) {
        cardsinhand.add(c);
    }

    public Card actionhit() {
        Card c = scig.getCard();
        return hitsetvalue(c);
    }

    public Card hitsetvalue(Card c) {
        cardsinhand.add(c);
        if (c.name().contains("ASS")) {
            if ((this.handvalue[1] + 11) > 21) {
                this.handvalue[0] = this.handvalue[0] + 1;
                this.handvalue[1] = this.handvalue[0] + 11 -1;
            } else {
                this.handvalue[0] = this.handvalue[0] + 1;
                this.handvalue[1] = this.handvalue[1] + 11;
            }
        } else {
            this.handvalue[0] = this.handvalue[0] + c.getCardValue();
            this.handvalue[1] = this.handvalue[1] + c.getCardValue();
        }
        return c;
    }
    
    public int getCardValue() {
		if (this.getHandValue()[1] <= 21) {
			return this.getHandValue()[1];
		} else {
			return this.getHandValue()[0];
		}
	}
}
