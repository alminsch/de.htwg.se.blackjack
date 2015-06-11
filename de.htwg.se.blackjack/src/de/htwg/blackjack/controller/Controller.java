package de.htwg.blackjack.controller;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.entities.impl.SingletonCardsInGame;

public class Controller {

	List<Player> playerlist;
	private int displaybet;
    private String statusLine = "Welcome to Blackjack!";
    private Player player;
    private SingletonCardsInGame scig;


	public Controller() {
		scig = new SingletonCardsInGame();
		playerlist = new ArrayList<Player>();
	}

	public void createnewgame() {
		statusLine = "New Blackjack Game created";
	}

	public boolean startnewround() {
		if(!playerlist.isEmpty()) {
			scig.resetStapel();
			round();
			return true;
		} else {
			return false;
		}
	}

	public void round() {
		playerbeds();
		//alle spieler bekommen 2 Karten
		//...

	}

	public void playerbeds() {
		//durch spielerliste gehen
	}

	public void addnewPlayer(String s) {
		playerlist.add(new Player(s));
	}

	public boolean deletePlayer(Player player) {
		return playerlist.remove(player);
	}

	public String getStatus() {
        return statusLine;
    }

	public void setbetforround() {
		player.setbet(this.displaybet);
	}

    public boolean increasebet() {
    	if(player.getbudget() >= this.displaybet + 100) {
    		this.displaybet += 100;
    		return true;
	    }
	   	return false;
    }

	public boolean decreasebet() {
		if(this.displaybet > 100) {
			this.displaybet -= 100;
			return true;
		}
		return false;
	}

	public int getdisplaybet() {
		return displaybet;
	}

	//start nur m�glich wenn spieler vorhanden
	//Start bei Player1:
	//Player1 macht bets
	//Player2 macht bets
	//(2 Buttons zum erh�hen und erniedrigen des Einsatzes + ein Button Wetten abgeben)

	//wenn alle Spieler gewettet haben:
	//Deal: alle Spieler und Dealer bekommen 2 Karten
	//check ob ein Spieler blackjack hat ->wenn ja auszahlung doppelter Einsatz
	//
	//
	//SPIELZUG
	//
	//----wenn Dealer Ass hat: PossibleAction.INSURANCE m�glich
	//----erste 2 Karten gesamt 9, 10 ,11: PossibleAction.DOUBLE m�glich
	//----erste 2 Karte gleich: PossibleAction.SPLIT m�glich - einsatz h�he kartenhand1 wird auch kartenhand2 zugeteilt, jede hand erh�lt neue Karte
			//Kartenhand1 dann Kartenhand2 gespielt
	//PossiblAction.STAND und PossibleAction.HIT m�glich --
	// zur�ck zu SPIELZUG: --->n�chster Spieler
	//
	//
	//alle spieler gespielt: Dealer Kartewert unter 17: hit , bei 17 oder h�her: stand
	//
	//vergleiche Wert HandDealer und Hand der Player

}
