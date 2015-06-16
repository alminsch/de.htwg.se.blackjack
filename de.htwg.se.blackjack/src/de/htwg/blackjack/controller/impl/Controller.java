package de.htwg.blackjack.controller.impl;

import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Queue;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Dealer;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.entities.impl.SingletonCardsInGame;
import de.htwg.blackjack.util.observer.Event;
import de.htwg.blackjack.util.observer.IObserver;

//@Singleton
public class Controller extends Observable implements IController {

	private Queue<Player> playerlist;
	private Queue<Player> tmpplayerlist;
	private int displaybet;
    private String statusLine = "Welcome to Blackjack!";
    private Player player;
    private SingletonCardsInGame scig;
    private Dealer dealer;

	public Controller() {
		scig = new SingletonCardsInGame();
		playerlist = new ArrayDeque<Player>();
		dealer = new Dealer();
	}

	public void createnewgame() {
		statusLine = "New Blackjack Game created";
	}

	public void startnewround() {
		this.tmpplayerlist = playerlist;
		if(!playerlist.isEmpty()) {
			scig.resetStapel();
			playerbets();

		} else {
			statusLine = "Es müssen Spieler erstellt werden, bevor das Spiel gestartet werden kann";
			notifyObservers();
		}
	}

	private void playerbets() {
		if(!tmpplayerlist.isEmpty()) {
		player = tmpplayerlist.poll();
		statusLine = "Spieler " + player.getplayername() + ", bitte Wette abgeben";
		notifyObservers();
		} else {
			statusLine = "Alle Wetten wurden abgegeben";
			allgettwocards(); //alle Spieler erhalten 2 Karten: vllt passendes Designpattern
		}
	}

	public void setbetforround() {
		player.setbet(this.displaybet);
		statusLine  = "Ihre Wette für diese Runde beträgt " + displaybet;
		notifyObservers();
		playerbets();
	}

	private void allgettwocards() {
		playerbets();
		for(Player player : playerlist) {
			player.actionhit();
			player.actionhit();
		}
		dealer.actionhit();
		dealer.actionhit();

		this.tmpplayerlist = playerlist; //reset tmpplayerlist for spielzug
		spielzug();
	}

	private void spielzug() {
		if(!tmpplayerlist.isEmpty()) {
			player = tmpplayerlist.poll();
			statusLine = "Spieler " + player.getplayername() + ", ist an der Reihe";
			notifyObservers();
			} else {
				auswertung();
			}
	}

	private void auswertung() { //todo: Methode  aufteilen
		StringBuilder sb = new StringBuilder();
		int finaldealervalue;

		while(dealer.getHandValue()[0] < 17) {
			dealer.actionhit();
		}
		finaldealervalue = dealer.getHandValue()[0];

		if (finaldealervalue > 21) {
			dealer.setHandValueNull();
		}

		for(Player player : playerlist) {
			int finalplayervalue;
			if(player.getHandValue()[1] <= 21) {
				finalplayervalue = player.getHandValue()[1];
			} else {
				finalplayervalue = player.getHandValue()[0];
			}
			if(finalplayervalue > 21) {
				sb.append(player.getplayername() + ": Busted");
				continue;
			}
			if (finalplayervalue > finaldealervalue) {
				sb.append(player.getplayername() + ": Gewonnen");
				//todo:add money to budget
				continue;
	        }
			if (finalplayervalue == finaldealervalue) {
				sb.append(player.getplayername() + ": Unentschieden");
				//todo: add money to budget
				continue;
			}
		}
		statusLine = sb.toString();
		notifyObservers();
	}

	public void stand() {
		spielzug();
	}

	public void playerhit() {
		player.actionhit();
		int handvalue = player.getHandValue()[0];
		if(handvalue > 21) {
			statusLine = "Busted :( Kartenwert beträgt: " + handvalue;
			spielzug();
		}
	}

	public void insurance() {

	}

	public void doublebet() {

	}

	public void addnewPlayer(String s) {
		playerlist.add(new Player(s));
		statusLine = "Neuer Spieler hinzugefügt";
	}

	public boolean deletePlayer(Player player) {
		return playerlist.remove(player);
	}

	public String getStatus() {
        return statusLine;
    }

    public boolean increasebet() {
    	if(player.getbudget() >= this.displaybet + 100) {
    		this.displaybet += 100;
    		statusLine = "Wette auf " + displaybet + "erhöht";
    		notifyObservers();
    		return true;
	    } else {
	    	statusLine = "Ihr Budget reicht nicht aus, um die Wette weiter zu erhöhen";
	    	notifyObservers();
	   	return false;
	    }
    }

	public boolean decreasebet() {
		if(this.displaybet > 100) {
			this.displaybet -= 100;
			statusLine = "Wette auf" + displaybet + "erniedrigt";
			notifyObservers();
			return true;
		}
		return false;
	}

	@Override
	public void addObserver(IObserver s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(IObserver s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers(Event e) {
		// TODO Auto-generated method stub
		
	}



	//start nur möglich wenn spieler vorhanden
	//Start bei Player1:
	//Player1 macht bets
	//Player2 macht bets
	//(2 Buttons zum erhöhen und erniedrigen des Einsatzes + ein Button Wetten abgeben)

	//wenn alle Spieler gewettet haben:
	//Deal: alle Spieler und Dealer bekommen 2 Karten
	//
	//
	//SPIELZUG
	//
	//----wenn Dealer Ass hat: PossibleAction.INSURANCE möglich
	//----erste 2 Karten gesamt 9, 10 ,11: PossibleAction.DOUBLE möglich
	//----erste 2 Karte gleich: PossibleAction.SPLIT möglich - einsatz höhe kartenhand1 wird auch kartenhand2 zugeteilt, jede hand erhält neue Karte
			//Kartenhand1 dann Kartenhand2 gespielt
	//PossiblAction.STAND und PossibleAction.HIT möglich --
	// zurück zu SPIELZUG: --->nächster Spieler
	//
	//
	//alle spieler gespielt: Dealer Kartewert unter 17: hit , bei 17 oder höher: stand
	//
	//vergleiche Wert HandDealer und Hand der Player

}
