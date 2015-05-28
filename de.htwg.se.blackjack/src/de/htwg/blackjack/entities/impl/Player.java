package de.htwg.blackjack.entities.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.AbstractParticipant;

public class Player extends AbstractParticipant {

    private int budget;
    private String playername;
    private List<PossibleAction> possibleplayeractions;

    public Player(CardsInGame c, String playername) {
        super(c);
        this.playername = playername;
        this.budget = 1500;
        this.possibleplayeractions = new ArrayList<PossibleAction>();
    }

    public String getplayername() {
        return this.playername;
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
    
    public void increasebet() {
    	
    }
    
    public void decreasebet() {
    
    }
}
