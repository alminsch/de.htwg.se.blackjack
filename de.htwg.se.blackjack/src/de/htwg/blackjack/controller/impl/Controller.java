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

import de.htwg.blackjack.Blackjack;
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

    private Queue<Player> playerListPlaying;
    private Queue<Player> playerList;
    private Queue<Player> betPlayerList;
    private int displaybet;
    private String statusLine = "Willkommen zu Blackjack!";
    Player player;
    private SingletonCardsInGame cardStack;
    private Dealer dealer;
    private GameStatus status;

    @Inject
    public Controller() {
        cardStack = SingletonCardsInGame.getInstance();
        cardStack.resetStapel();
        playerListPlaying = new LinkedList<Player>();
        playerList = new LinkedList<Player>();
        dealer = new Dealer();
        displaybet = 100;
        status = GameStatus.NOT_STARTED;
    }

    public void createnewgame() {
        statusLine = "Neues Spiel erstellt";
        notifyObservers(GameStatus.NOT_STARTED);
    }
    
    public void resetgame() {
        cardStack = SingletonCardsInGame.getInstance();
        cardStack.resetStapel();
        playerListPlaying = new LinkedList<Player>();
        playerList = new LinkedList<Player>();
        dealer = new Dealer();
        displaybet = 100;
        status = GameStatus.NOT_STARTED;
        statusLine = "Neues Spiel erstellt";
    }

    public void startnewround() {
    	playerListPlaying = new LinkedList<Player>(playerList);
        betPlayerList = new LinkedList<Player>(playerListPlaying);
        
        if(!playerListPlaying.isEmpty()) {
            cardStack.resetStapel();
            resetHandCards();
            resetPlayerBets();
            playerbets();
        } else {
            statusLine = "Es müssen Spieler beitreten, bevor das Spiel gestartet werden kann";
            notifyObservers();
        }
    }

    public void playerbets() {
        if(!betPlayerList.isEmpty()) {
            player = betPlayerList.poll();
            statusLine = "Spieler " + player.getPlayerName() + ", bitte Wette abgeben." +
                        " Budget: " + player.getbudget() +
                        " \nStartwette beträgt 100";
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
        if(status == GameStatus.DURING_BET && this.displaybet > 100) {
            this.displaybet -= 100;
            notifyObservers(GameStatus.DURING_BET);
            return true;
        }
        return false;
    }

    public void allgettwocards() {
        for(Player p : playerListPlaying) {
            p.actionhit();
            p.actionhit();
            if(p.getHandValue()[0] >= 9 && p.getHandValue()[0] <= 11) {
                //double möglich
            }
        }
        dealer.actionhit();
        this.betPlayerList =  new LinkedList<Player>(playerListPlaying); //reset tmpplayerlist for spielzug
        spielzug();
    }

    public void spielzug() {
        if(!betPlayerList.isEmpty()) {
            player = betPlayerList.poll();
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

        if (finaldealervalue > 21) {
            finaldealervalue = 0;
        }

        for(Player p : playerListPlaying) {
            int totalplayerbet = p.getplayerbet();
            int finalplayervalue;
            if(p.getHandValue()[1] <= 21) {
                finalplayervalue = p.getHandValue()[1];
            } else {
                finalplayervalue = p.getHandValue()[0];
            }

            if (finalplayervalue == finaldealervalue) {
                sb.append("Spieler: " + p.getPlayerName() + ": Unentschieden   ");
                continue;
            } else if(finalplayervalue > 21) {
                p.deletefrombudget(totalplayerbet);
                sb.append("Spieler: " +p.getPlayerName() + ": Verliert -" + totalplayerbet + " chips \t");
                continue;
            } else if (finalplayervalue > finaldealervalue) {
                p.addtobudget(totalplayerbet);
                sb.append("Spieler: " +p.getPlayerName() + ": Gewinnt + " + totalplayerbet + " chips \t");
                continue;
            } else {
                p.deletefrombudget(totalplayerbet);
                sb.append("Spieler: " + p.getPlayerName() + ": Verliert -" + totalplayerbet + " chips \t");
                continue;
            }
        }

        //statusLine = "Auswertung:\n" + sb.toString();
        statusLine = "Runde beendet: " + sb.toString();
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

	public void addnewPlayer(String name) {
		if (playerList.size() >= 3) {
			statusLine = "Maximale Anzahl Spieler erreicht!";
			notifyObservers();
			return;
		} else {
			for(Player p : playerList) {
				if(p.getPlayerName().equals(name)) {
					return;
				}
			}
			playerList.add(new Player(name));
			notifyObservers(GameStatus.NEW_PLAYER);
		}
	}


    public boolean deletePlayer(Player player) {
    	betPlayerList.remove(player);
    	
    	playerListPlaying.remove(player); 	
        return playerList.remove(player);
    }
    
    public void removePlayer(String playername) {
    	for(Player p : playerList) {
    		if (p.getPlayerName().equals(playername)) {
    			System.out.println("removing player"+p.getPlayerName());
    			playerList.remove(p);
    			if(betPlayerList != null) {
    				betPlayerList.remove(p);
    			}
    			playerListPlaying.remove(p);
    		}
    	}
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
        for(Player p : playerList) {
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

    public List<Player> getPlayingPlayerList() {
        return new LinkedList<Player>(playerListPlaying);
    }
    
    public List<Player> getPlayerList() {
        return new LinkedList<Player>(playerList);
    }

    public int getCardValue(AbstractParticipant p) {
        if(p.getHandValue()[1] <= 21) {
            return p.getHandValue()[1];
        } else{
            return p.getHandValue()[0];
        }
    }
    
    private void resetPlayerBets() {
    	for(Player p : playerList) {
    		p.setplayerbet(0);
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
		List<String> playerBet = new ArrayList<String>();
		
		List<List> cardArray = new ArrayList<List>();
		List<List> dealerlist = new ArrayList<List>();
	
		for(Player p : getPlayerList()) {
			playerName.add(p.getPlayerName());
			cardValue.add(p.getHandValue()[0] + "/" + p.getHandValue()[1]);
			budget.add(Integer.toString(p.getbudget()));
			cardArray.add(p.getCardsInHand());
			playerBet.add(Integer.toString(p.getplayerbet()));
		}
		
		// player
		playerlist.add(playerName);
		playerlist.add(cardValue);
		playerlist.add(budget);
		playerlist.add(playerBet);
		players.put("players", playerlist);
		
		// cards
		cards.put("cards", cardArray);
		
		// dealer
		dealerlist.add(getDealerCards());
		dealerlist.add(Arrays.asList(getDealer().getHandValue()[0] + "/" + getDealer().getHandValue()[1]));
		dealer.put("dealer", dealerlist);
		
		// statusLine
		statusLine.put("statusline", Arrays.asList(getStatus(), getGameStatus().toString()));
		
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
    

    public String getJson(String command) {
    	String playername = command.split(":")[1];
    	String com = command.split(":")[0];
		if(!com.equals("null")) {
			if(com.equals("h") || com.equals("s") || com.equals("+") || com.equals("-") || com.equals("sb")) {
				if(status != GameStatus.NOT_STARTED) {
					if(playername.equals(player.getPlayerName())) {
						Blackjack.getInstance().getTUI().userinputselection(com);
					}
				}
			} else if (com.equals("n")) {
				if(status == GameStatus.NOT_STARTED || status == GameStatus.AUSWERTUNG) {
					Blackjack.getInstance().getTUI().userinputselection(com);
				}
			} else {
				Blackjack.getInstance().getTUI().userinputselection(com);
			}
		}
		//if(command.contains("newplayer:")) {
			//String player = command.split(":")[1];
			//Blackjack.getInstance().getController().addnewPlayer(player);
		//}
		
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
		List<String> playerBet = new ArrayList<String>();
		
		List<List> cardArray = new ArrayList<List>();
		List<List> dealerlist = new ArrayList<List>();
	
		for(Player p : getPlayerList()) {
			playerName.add(p.getPlayerName());
			cardValue.add(p.getHandValue()[0] + "/" + p.getHandValue()[1]);
			budget.add(Integer.toString(p.getbudget()));
			cardArray.add(p.getCardsInHand());
			playerBet.add(Integer.toString(p.getplayerbet()));
		}
		
		// player
		playerlist.add(playerName);
		playerlist.add(cardValue);
		playerlist.add(budget);
		playerlist.add(playerBet);
		players.put("players", playerlist);
		
		// cards
		cards.put("cards", cardArray);
		
		// dealer
		dealerlist.add(getDealerCards());
		dealerlist.add(Arrays.asList(getDealer().getHandValue()[0] + "/" + getDealer().getHandValue()[1]));
		dealer.put("dealer", dealerlist);
		
		// statusLine
		statusLine.put("statusline", Arrays.asList(getStatus(), getGameStatus().toString()));
		
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
