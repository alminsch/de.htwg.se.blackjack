package de.htwg.blackjack.controller.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.AbstractParticipant;
import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.Dealer;
import de.htwg.blackjack.entities.impl.GameStatus;
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
    private boolean dealerinsurance = false;
    private GameStatus status;

    @Inject
	public Controller() {
    	scig = SingletonCardsInGame.getInstance();
    	scig.resetStapel();
		playerlist = new LinkedList<Player>();
		dealer = new Dealer();
		displaybet = 100;
		status = GameStatus.NOT_STARTED;
	}

	public void createnewgame() {
		statusLine = "New Blackjack Game created";
		notifyObservers(GameStatus.NOT_STARTED);
	}

	public void checkplayerbudget() {
		for(Player player : playerlist) {
			if(player.getbudget() <= 0) {
				deletePlayer(player);
			}
		}
	}

	public void startnewround() {
		checkplayerbudget();
		this.tmpplayerlist = new LinkedList<Player>(playerlist);
		if(!playerlist.isEmpty()) {
			scig.resetStapel();
			resetHandCards();
			dealerinsurance = false;
			playerbets();
		} else {
			statusLine = "Es müssen Spieler erstellt werden, bevor das Spiel gestartet werden kann";
			notifyObservers();
		}
	}

	public void playerbets() {
		if(!tmpplayerlist.isEmpty()) {
			player = tmpplayerlist.poll();
			statusLine = "Spieler " + player.getPlayerName() + ", bitte Wette abgeben." +
						" Budget: " + player.getbudget();
			notifyObservers(status = GameStatus.DURING_BET);
		} else {
			statusLine = "Alle Wetten wurden abgegeben";
			notifyObservers();
			allgettwocards();
		}
	}

	public void setbetforround() {
		if(status == GameStatus.DURING_BET) {
			setTotalPlayerbet(this.displaybet);
			statusLine  = "Ihre Wette für diese Runde beträgt " + getTotalPlayerBet();
			displaybet = 100;
			notifyObservers(GameStatus.DURING_BET);
			playerbets();
		}
	}

	public boolean increasebet() {
		if(status == GameStatus.DURING_BET) {
	    	if(player.getbudget() >= this.displaybet + 100) {
	    		this.displaybet += 100;
	    		statusLine = "Wette auf " + displaybet + " erhöht";
	    		notifyObservers(GameStatus.DURING_BET);
	    		return true;
		    } else {
		    	statusLine = "Ihr Budget reicht nicht aus, um die Wette weiter zu erhöhen";
		    	notifyObservers(GameStatus.DURING_BET);
		    }
		}
		return false;
    }

	public boolean decreasebet() {
		if(status == GameStatus.DURING_BET) {
			if(this.displaybet > 100) {
				this.displaybet -= 100;
				statusLine = "Wette auf " + displaybet + " erniedrigt";
				notifyObservers(GameStatus.DURING_BET);
				return true;
			}
		}
		return false;
	}

	public void allgettwocards() {
		for(Player player : playerlist) {
			player.actionhit();
			player.actionhit();
			if(player.getHandValue()[0] >= 9 && player.getHandValue()[0] <= 11) {
				//double möglich
			}
		}
		dealer.actionhit();
		dealer.actionhit();
		if(dealer.getHandValue()[1] == 21) {
			dealerinsurance = true;
		}
		this.tmpplayerlist =  new LinkedList<Player>(playerlist); //reset tmpplayerlist for spielzug
		spielzug();
	}

	public void spielzug() {
		if(!tmpplayerlist.isEmpty()) {
			player = tmpplayerlist.poll();
			statusLine = "Spieler " + player.getPlayerName() + ", ist an der Reihe";
			notifyObservers(status = GameStatus.DURING_TURN);
		} else {
			auswertung();
		}
	}

	public void auswertung() {
		StringBuilder sb = new StringBuilder();
		int finaldealervalue;

		while(dealer.getHandValue()[0] < 17 && dealer.getHandValue()[1] != 21) {
			dealer.actionhit();
		}

		finaldealervalue = getCardValue(dealer);

		sb.append(dealer.toString(finaldealervalue) + "\n");
		if (finaldealervalue > 21) {
			finaldealervalue = 0;
		}

		for(Player p : playerlist) {
			System.out.println(p.playerbet);
			checkinsurance();
			int totalplayerbet = p.playerbet;
			int finalplayervalue;
			if(p.getHandValue()[1] <= 21) {
				finalplayervalue = p.getHandValue()[1];
			} else {
				finalplayervalue = p.getHandValue()[0];
			}

			if (finalplayervalue == finaldealervalue) {
				sb.append(p.getPlayerName() + ": Unentschieden    neues Budget:" + p.getbudget()  + "\n");
				continue;
			} else if(finalplayervalue > 21) {
				p.deletefrombudget(totalplayerbet);
				sb.append(p.getPlayerName() + ": Busted    neues Budget:" + p.getbudget() + "\n");
				continue;
			} else if (finalplayervalue > finaldealervalue) {
				p.addtobudget(totalplayerbet);
				sb.append(p.getPlayerName() + ": Gewonnen    neues Budget:" + p.getbudget() + "\n");
				continue;
			} else if (finalplayervalue < finaldealervalue) {
				p.deletefrombudget(totalplayerbet);
				sb.append(p.getPlayerName() + ": Verloren    neues Budget:" + p.getbudget() + "\n");
				continue;
			}
		}

		statusLine = "Auswertung:\n" + sb.toString() ;
		notifyObservers(status = GameStatus.AUSWERTUNG);
	}



	private void checkinsurance() {
		if (player.getinsurance() == true && dealerinsurance == true) {
			player.addtobudget(getTotalPlayerBet() / 2);
		} else if(player.getinsurance() == true && dealerinsurance == false) {
			player.deletefrombudget(getTotalPlayerBet() / 2);
		}
	}

	public String getCards() {
		if(status == GameStatus.DURING_TURN) {
			return player.toString() + dealer.toString(0);
		} else {
			return " ";
		}
	}

	public void stand() {
		if(status == GameStatus.DURING_TURN) {
			statusLine = player.getPlayerName() + "  STAND";
			notifyObservers();
			spielzug();
		}
	}

	public void playerhit() {
		if(status == GameStatus.DURING_TURN) {
			player.actionhit();
			int handvalue = player.getHandValue()[0];
			if(handvalue > 21) {
				statusLine = player.getPlayerName() + "  BUSTED!";
				notifyObservers(status = GameStatus.DURING_TURN);
				spielzug();
			} else {
				statusLine = player.getPlayerName() + "  HIT";
				notifyObservers(status = GameStatus.DURING_TURN);
			}
		}
	}

	public void insurance() {
		if(dealer.getCardsInHand().get(0).name().contains("ASS")) {
			player.setinsurancetrue();
		}
	}

	public void doublebet() {

	}

	public void addnewPlayer(String s) {
		if (playerlist.size() == 3) {
			statusLine = "Maximale Anzahl Spieler erreicht";
			notifyObservers();
			return;
		}
		playerlist.add(new Player(s));
		statusLine = "Neuer Spieler hinzugefügt";
		status = GameStatus.READY_TO_START;
		notifyObservers(GameStatus.NEW_PLAYER);
	}

	public boolean deletePlayer(String name) {
		for(Player player : playerlist) {
			if(name == player.getPlayerName());
			playerlist.remove(player);
			return true;
		}
		return false;
	}
	public boolean deletePlayer(Player player) {
		return playerlist.remove(player);
	}

	public String getStatus() {
        return statusLine;
    }

	private void resetHandCards() {
		dealer.resetCardsInHand();
		for(Player player : playerlist) {
			player.resetCardsInHand();
		}
	}

	public void exit() {
        System.exit(0);
    }

	public int getTotalPlayerBet() {
		return player.playerbet;
	}

	public void setTotalPlayerbet(int bet) {
		player.playerbet = bet;
	}

	public int getDisplayBet() {
		return displaybet;
	}

	public List<Card> getDealerCards() {
		return dealer.getCardsInHand();
	}

	public List<Player> getPlayerList() {
		return new LinkedList<Player>(playerlist);
	}

	public int getCardValue(AbstractParticipant p) {
		if(p.getHandValue()[1] <= 21) {
			return p.getHandValue()[1];
		} else{
			return p.getHandValue()[0];
		}
	}

	public Dealer getDealer() {
		return dealer;
	}
}