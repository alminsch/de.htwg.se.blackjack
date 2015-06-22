
package de.htwg.blackjack.entities.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.AbstractParticipant;

public class Player extends AbstractParticipant {

    private int budget;
    private List<PossibleAction> possibleplayeractions;
    private String playername;
    public int playerbet;

    public Player(String playername) {
        super();
        this.playername = playername;
        this.budget = 1500;
        this.possibleplayeractions = new ArrayList<PossibleAction>();
    }
    public void setplayerbet(int bet) {
    	this.playerbet = bet;
    }

    public int getplayerbet() {
    	return this.playerbet;
    }

    public void addplayeraction(PossibleAction action) {
        this.possibleplayeractions.add(action);
    }

    public void deleteplayeraction(PossibleAction action) {
        this.possibleplayeractions.remove(action);
    }

    public List<PossibleAction> getpossibleplayeractions() {
        return this.possibleplayeractions;
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

    public void actioninsurance() {

    }
    public String getPlayerName() {
    	return this.playername;
    }

    @Override
    public String toString() {
		String newLine = System.getProperty("line.separator");
	    String result = newLine;
	    result = playername + " Handkarten:";
		for(Card c : getCardsInHand()) {
			result = result + c.toString() + " ";
		}
		result = result + "Wert:" + this.getHandValue()[0];
    	return result + newLine;
    }
}