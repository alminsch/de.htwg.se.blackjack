package de.htwg.blackjack.controller;

import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.CardsInGame;
import de.htwg.blackjack.entities.impl.Player;

import java.util.List;

public class NewRound {
    CardsInGame cig;
    private int bet;

    public NewRound() {
        cig = new CardsInGame();
        bet = 10;
    }

    public boolean increasebet(Player player) {
    	if(player.getbudget() >= this.bet + 10) {
    		this.bet += 10;
    		return true;
    	}
    	return false;
    }

    public boolean decreasebet() {
    	if(this.bet > 10) {
    		this.bet -= 10;
    		return true;
    	}
    	return false;
    }

}
