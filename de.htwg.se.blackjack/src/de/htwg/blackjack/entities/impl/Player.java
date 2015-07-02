
package de.htwg.blackjack.entities.impl;

import de.htwg.blackjack.entities.AbstractParticipant;

public class Player extends AbstractParticipant {

    private int budget;
    private String playername;
    public int playerbet;
    boolean insurance;

    public Player(String playername) {
        super();
        this.playername = playername;
        this.budget = 1500;
        this.insurance = false;
        this.playerbet = 0;
    }

    public void setinsurancetrue() {
        insurance = true;
    }

    public void setinsurancefalse() {
        insurance = false;
    }

    public boolean getinsurance() {
        return insurance;
    }

    public void setplayerbet(int bet) {
        this.playerbet = bet;
    }

    public int getplayerbet() {
        return this.playerbet;
    }

    public void addtobudget(int money) {
        this.budget += money;
    }

    public void deletefrombudget(int money) {
        this.budget -= money;
    }

    public int getbudget() {
        return this.budget;
    }

    public String getPlayerName() {
        return this.playername;
    }

    @Override
    public String toString() {
        String newLine = System.getProperty("line.separator");
        String result = newLine;
        result = playername + " Handkarten: ";
        for(Card c : getCardsInHand()) {
            result = result + c.toString() + " ";
        }
        result = result + "Wert:" + this.getHandValue()[0];
        return result;// + newLine;
    }
}
