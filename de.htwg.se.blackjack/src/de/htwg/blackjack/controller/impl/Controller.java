package de.htwg.blackjack.controller.impl;

import java.util.ArrayDeque;
import java.util.Queue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Dealer;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.entities.impl.SingletonCardsInGame;
import de.htwg.blackjack.util.observer.Observable;

@Singleton
public class Controller extends Observable implements IController {

	private Queue<Player> playerlist;
	private Queue<Player> tmpplayerlist;
	private int displaybet;
    private String statusLine = "Welcome to Blackjack!";
    private Player player;
    private SingletonCardsInGame scig;
    private Dealer dealer;
    private boolean showCards = false;
    private int playerbet;

    @Inject
	public Controller() {
    	scig = SingletonCardsInGame.getInstance();
    	scig.resetStapel();
		playerlist = new ArrayDeque<Player>();
		dealer = new Dealer();
		displaybet = 100;
	}

	public void createnewgame() {
		statusLine = "New Blackjack Game created";
		notifyObservers();
	}

	public void startnewround() {
		this.tmpplayerlist = new ArrayDeque<Player>(playerlist);
		if(!playerlist.isEmpty()) {
			scig.resetStapel();
			resetHandCards();
			playerbets();

		} else {
			statusLine = "Es m�ssen Spieler erstellt werden, bevor das Spiel gestartet werden kann";
			notifyObservers();
		}
	}

	public void playerbets() {
		if(!tmpplayerlist.isEmpty()) {
			player = tmpplayerlist.poll();
			statusLine = "Spieler " + player.getPlayerName() + ", bitte Wette abgeben." +
						" Budget: " + player.getbudget();
			notifyObservers();
		} else {
			statusLine = "Alle Wetten wurden abgegeben";
			notifyObservers();
			allgettwocards();
		}
	}

	public void setbetforround() {
		this.playerbet = this.displaybet;
		statusLine  = "Ihre Wette f�r diese Runde betr�gt " + displaybet;
		displaybet = 100;
		notifyObservers();
		playerbets();
	}

	public boolean increasebet() {
    	if(player.getbudget() >= this.displaybet + 100) {
    		this.displaybet += 100;
    		statusLine = "Wette auf " + displaybet + " erh�ht";
    		notifyObservers();
    		return true;
	    } else {
	    	statusLine = "Ihr Budget reicht nicht aus, um die Wette weiter zu erh�hen";
	    	notifyObservers();
	   	return false;
	    }
    }

	public boolean decreasebet() {
		if(this.displaybet > 100) {
			this.displaybet -= 100;
			statusLine = "Wette auf " + displaybet + " erniedrigt";
			notifyObservers();
			return true;
		}
		return false;
	}
	public void allgettwocards() {
		for(Player player : playerlist) {
			player.actionhit();
			player.actionhit();
		}
		dealer.actionhit();
		dealer.actionhit();

		this.tmpplayerlist =  new ArrayDeque<Player>(playerlist); //reset tmpplayerlist for spielzug
		spielzug();
	}

	public void spielzug() {
		showCards = true;
		if(!tmpplayerlist.isEmpty()) {
			player = tmpplayerlist.poll();
			statusLine = "Spieler " + player.getPlayerName() + ", ist an der Reihe";
			notifyObservers();
		} else {
			auswertung();
		}
	}

	public void auswertung() {
		showCards = false;
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
				player.deletefrombudget(playerbet);
				sb.append(player.getPlayerName() + ": Busted    neues Budget:" + player.getbudget() + "\n");
				continue;
			}
			if (finalplayervalue > finaldealervalue) {
				player.addtobudget(playerbet);
				sb.append(player.getPlayerName() + ": Gewonnen    neues Budget:" + player.getbudget() + "\n");
				continue;
	        }
			if (finalplayervalue == finaldealervalue) {
				sb.append(player.getPlayerName() + ": Unentschieden    neues Budget:" + player.getbudget()  + "\n");
				continue;
			}
		}
		statusLine = "Auswertung: " + sb.toString() ;

		notifyObservers();
	}

	public String getCards() {
		if(showCards == true) {
			return player.toString() + dealer.toString();
		} else {
			return " ";
		}
	}

	public void stand() {
		spielzug();
	}

	public void playerhit() {
		showCards = true;
		player.actionhit();
		int handvalue = player.getHandValue()[0];
		if(handvalue > 21) {
			statusLine = "Busted!";
			notifyObservers();
			spielzug();
		} else {
			statusLine = "Hit";
			notifyObservers();
		}
	}

	public void insurance() {
	}

	public void doublebet() {

	}

	public void addnewPlayer(String s) {
		playerlist.add(new Player(s));
		statusLine = "Neuer Spieler hinzugef�gt";
		notifyObservers();
	}

	public boolean deletePlayer(Player player) {
		return playerlist.remove(player);
	}

	public String getStatus() {
        return statusLine;
    }

	//todo:
	private void resetHandCards() {

	}
}