package de.htwg.blackjack.entities.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.AbstractParticipant;

public class Player extends AbstractParticipant {

    private int budget;
    private String playername;
    private List<PlayerAction> possibleplayeractions;

    public Player(CardsInGame c, String playername) {
        super(c);
        this.playername = playername;
        this.budget = 1500;
        this.possibleplayeractions = new ArrayList<PlayerAction>();
    }

    public String getplayername() {
        return this.playername;
    }

    public void addplayeraction(PlayerAction action) {
        this.possibleplayeractions.add(action);
    }

    public void deleteplayeraction(PlayerAction action) {
        this.possibleplayeractions.remove(action);
    }

    public List<PlayerAction> getpossibleplayeractions() {
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
}
