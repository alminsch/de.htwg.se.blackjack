package de.htwg.blackjack.controller.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import com.google.gson.*;



@Singleton
public class Controller extends Observable implements IController {

    private Queue<Player> playerlist;
    private Queue<Player> tmpplayerlist;
    private int displaybet;
    private String statusLine = "Welcome to Blackjack!";
    Player player;
    private SingletonCardsInGame scig;
    private Dealer dealer;
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

    public void startnewround() {
        this.tmpplayerlist = new LinkedList<Player>(playerlist);
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
                        " Budget: " + player.getbudget() +
                        " \nStartwette betr�gt 100";
            status = GameStatus.DURING_BET;
            notifyObservers(GameStatus.DURING_BET);
        } else {
            statusLine = "Alle Wetten wurden abgegeben";
            notifyObservers();
            allgettwocards();
        }
    }

    public void setbetforround() {
        if(status == GameStatus.DURING_BET) {
            setTotalPlayerbet(this.displaybet);
            statusLine  = "Ihre Wette f�r diese Runde betr�gt " + getTotalPlayerBet();
            displaybet = 100;
            notifyObservers(GameStatus.DURING_BET);
            playerbets();
        }
    }

    public boolean increasebet() {
        if(status == GameStatus.DURING_BET) {
            if(player.getbudget() >= this.displaybet + 100) {
                this.displaybet += 100;
                statusLine = "Wette:  " + displaybet;
                notifyObservers(GameStatus.DURING_BET);
                return true;
            } else {
                statusLine = "Ihr Budget reicht nicht aus, um die Wette weiter zu erh�hen";
                notifyObservers(GameStatus.DURING_BET);
            }
        }
        return false;
    }

    public boolean decreasebet() {
        if(status == GameStatus.DURING_BET && this.displaybet > 100) {
            this.displaybet -= 100;
            statusLine = "Wette:  " + displaybet;
            notifyObservers(GameStatus.DURING_BET);
            return true;
        }
        return false;
    }

    public void allgettwocards() {
        for(Player p : playerlist) {
            p.actionhit();
            p.actionhit();
            if(p.getHandValue()[0] >= 9 && p.getHandValue()[0] <= 11) {
                //double m�glich
            }
        }
        dealer.actionhit();
        this.tmpplayerlist =  new LinkedList<Player>(playerlist); //reset tmpplayerlist for spielzug
        spielzug();
    }

    public void spielzug() {
        if(!tmpplayerlist.isEmpty()) {
            player = tmpplayerlist.poll();
            statusLine = "Spieler " + player.getPlayerName() + ", ist an der Reihe";
            status = GameStatus.DURING_TURN;
            notifyObservers(GameStatus.DURING_TURN);
        } else {
            auswertung();
        }
    }

    public void auswertung() {
        StringBuilder sb = new StringBuilder();
        int finaldealervalue;

        while(dealer.getHandValue()[0] < 17 && dealer.getHandValue()[1] < 17) {
            dealer.actionhit();
        }

        finaldealervalue = getCardValue(dealer);

        sb.append(dealer.toString(finaldealervalue) + "\n\n");

        if (finaldealervalue > 21) {
            finaldealervalue = 0;
        }

        for(Player p : playerlist) {
            sb.append(p.toString() + "\n");

            int totalplayerbet = p.getplayerbet();
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
            } else {
                p.deletefrombudget(totalplayerbet);
                sb.append(p.getPlayerName() + ": Verloren    neues Budget:" + p.getbudget() + "\n");
                continue;
            }
        }

        statusLine = "Auswertung:\n" + sb.toString();
        status = GameStatus.AUSWERTUNG;
        notifyObservers(GameStatus.AUSWERTUNG);
    }
    public String getCards() {
        return player.toString() + "\n" + dealer.toString(0);

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
                status = GameStatus.DURING_TURN;
                notifyObservers(GameStatus.DURING_TURN);
                spielzug();
            } else {
                statusLine = player.getPlayerName() + "  HIT";
                status = GameStatus.DURING_TURN;
                notifyObservers(GameStatus.DURING_TURN);
            }
        }
    }

    public void doublebet() {
        //not implemented
    }

    public void addnewPlayer(String s) {
        if(status == GameStatus.AUSWERTUNG || status == GameStatus.NOT_STARTED || status == GameStatus.NEW_PLAYER) {
            if (playerlist.size() == 3) {
                statusLine = "Maximale Anzahl Spieler erreicht";
                notifyObservers();
                return;
            }
            playerlist.add(new Player(s));
            statusLine = "Neuer Spieler hinzugef�gt";
            notifyObservers(GameStatus.NEW_PLAYER);
        } else {
            statusLine = "Spieler k�nnen nur zu Beginn einer neuen Runde erstellt werden";
            notifyObservers(GameStatus.NP_NOPERMISSION);
        }
    }


    public boolean deletePlayer(Player player) {
        return playerlist.remove(player);
    }


    public String getStatus() {
        return statusLine;
    }

    public GameStatus getGameStatus() {
        return status;
    }

    public void setGameStatus(GameStatus e)  {
        status = e;

    }

    private void resetHandCards() {
        dealer.resetCardsInHand();
        for(Player p : playerlist) {
            p.resetCardsInHand();
        }
    }
    public int getTotalPlayerBet() {
    	if(player != null) {
    		return player.getplayerbet();
    	}
    	else {
    		return 0;
    	}
    }

    public void setTotalPlayerbet(int bet) {
        player.setplayerbet(bet);
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
    
    public String json() {
    	List<Map> array = new ArrayList<Map>();

		Map<String, Object> players = new HashMap<String, Object>();
		Map<String, Object> cards = new HashMap<String, Object>();
		Map<String, Object> dealer = new HashMap<String, Object>();
		Map<String, Object> statusLine = new HashMap<String, Object>();
		Map<String, Object> betDisplay = new HashMap<String, Object>();
		

		List<List> playerlist = new ArrayList<List>();
		List<String> playerName = new ArrayList<String>();
		List<String> cardValue = new ArrayList<String>();
		List<String> budget = new ArrayList<String>();
		
		List<List> cardArray = new ArrayList<List>();
		List<List> dealerlist = new ArrayList<List>();
	
		for(Player p : getPlayerList()) {
			playerName.add(p.getPlayerName());
			cardValue.add(p.getHandValue()[0] + "/" + p.getHandValue()[1]);
			budget.add(Integer.toString(p.getbudget()));
			
			cardArray.add(p.getCardsInHand());
		}
		
		// player
		playerlist.add(playerName);
		playerlist.add(cardValue);
		playerlist.add(budget);
		players.put("players", playerlist);
		
		// cards
		cards.put("cards", cardArray);
		
		// dealer
		dealerlist.add(getDealerCards());
		dealerlist.add(Arrays.asList(getDealer().getHandValue()[0] + "/" + getDealer().getHandValue()[1]));
		dealer.put("dealer", dealerlist);
		
		// statusLine
		statusLine.put("statusline", getStatus());
		
		// Bet
		betDisplay.put("bet", Arrays.asList(getDisplayBet(), getTotalPlayerBet()));
		
		array.add(players);
		array.add(cards);
		array.add(dealer);
		array.add(statusLine);
		array.add(betDisplay);
		
		Gson gson = new Gson();

		String json = gson.toJson(array);
		
		return json;
    }
}
