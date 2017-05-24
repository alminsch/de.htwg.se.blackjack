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
import de.htwg.blackjack.persistence.IPlayersDAO;
import de.htwg.blackjack.util.observer.Observable;
import com.google.gson.*;

@Singleton
public class Controller extends Observable implements IController {

	private Queue<Player> playerListPlaying;
	private Queue<Player> playerList;
	private Queue<Player> betPlayerList;
	private int displaybet;
	private String statusLine = "Welcome to Blackjack!";
	private Player player;
	private SingletonCardsInGame cardStack;
	private Dealer dealer;
	private GameStatus status;
	private IPlayersDAO playersDAO;

	@Inject
	public Controller(IPlayersDAO playersDAO) {
		cardStack = SingletonCardsInGame.getInstance();
		cardStack.resetStapel();
		playerListPlaying = new LinkedList<Player>();
		playerList = new LinkedList<Player>();
		dealer = new Dealer();
		displaybet = 100;
		status = GameStatus.NOT_STARTED;
		this.playersDAO = playersDAO;
	}

	public void createnewgame() {
		statusLine = "New Blackjack Game created";
		notifyObservers(GameStatus.NOT_STARTED);
	}

	public void startnewround() {
		playerListPlaying = new LinkedList<Player>(playerList);
		betPlayerList = new LinkedList<Player>(playerListPlaying);

		if (!playerListPlaying.isEmpty()) {
			resetGame();
			playerbets();
		} else {
			statusLine = "Before starting the game ensure that players have joined";
			status = GameStatus.NOT_STARTED;
			notifyObservers();
		}
	}

	public void playerbets() {
		if (!betPlayerList.isEmpty()) {
			player = betPlayerList.poll();
			statusLine = player.getPlayerName() + ", pls make a bet" + " Budget: " + player.getBudget()
					+ " \nstarting bet is 100";
			status = GameStatus.DURING_BET;
			notifyObservers(GameStatus.DURING_BET);
		} else {
			statusLine = "All bets are done";
			notifyObservers();
			allgettwocards();
		}
	}

	public void setbetforround() {
		if (status == GameStatus.DURING_BET) {
			setTotalPlayerbet(this.displaybet);
			statusLine = "Your bet is " + getTotalPlayerBet() + " this round!";
			displaybet = 100;
			notifyObservers(GameStatus.DURING_BET);
			playerbets();
		}
	}

	public boolean increasebet() {
		if (status == GameStatus.DURING_BET) {
			if (player.getBudget() >= this.displaybet + 100) {
				this.displaybet += 100;
				notifyObservers(GameStatus.DURING_BET);
				return true;
			} else {
				statusLine = "Not enough money to increase bet!";
				notifyObservers(GameStatus.DURING_BET);
			}
		}
		return false;
	}

	public boolean decreasebet() {
		if (status == GameStatus.DURING_BET && this.displaybet > 100) {
			this.displaybet -= 100;
			notifyObservers(GameStatus.DURING_BET);
			return true;
		}
		return false;
	}

	public void allgettwocards() {
		for (Player p : playerListPlaying) {
			p.actionhit();
			p.actionhit();
			if (p.getHandValue()[0] >= 9 && p.getHandValue()[0] <= 11) {
				// double mˆglich
			}
		}
		dealer.actionhit();
		this.betPlayerList = new LinkedList<Player>(playerListPlaying);
		round();
	}

	public void round() {
		if (!betPlayerList.isEmpty()) {
			player = betPlayerList.poll();
			statusLine = player.getPlayerName() + ", it is your turn!";
			status = GameStatus.DURING_TURN;
			notifyObservers(GameStatus.DURING_TURN);
		} else {
			evaluateRound();
		}
	}

	public void evaluateRound() {
		StringBuilder sb = new StringBuilder();
		int finaldealervalue;

		while (dealer.getHandValue()[0] < 17 && dealer.getHandValue()[1] < 17) {
			dealer.actionhit();
		}

		finaldealervalue = getCardValue(dealer);

		if (finaldealervalue > 21) {
			finaldealervalue = 0;
		}

		for (Player p : playerListPlaying) {
			int totalplayerbet = p.getplayerbet();
			int finalplayervalue;
			if (p.getHandValue()[1] <= 21) {
				finalplayervalue = p.getHandValue()[1];
			} else {
				finalplayervalue = p.getHandValue()[0];
			}

			if (finalplayervalue == finaldealervalue) {
				sb.append("Spieler: " + p.getPlayerName() + ": no change   ");
				continue;
			} else if (finalplayervalue > 21) {
				p.deletefrombudget(totalplayerbet);
				sb.append("Spieler: " + p.getPlayerName() + ": lost -" + totalplayerbet + "\t");
				continue;
			} else if (finalplayervalue > finaldealervalue) {
				p.addtobudget(totalplayerbet);
				sb.append("Spieler: " + p.getPlayerName() + ": win + " + totalplayerbet + "\t");
				continue;
			} else {
				p.deletefrombudget(totalplayerbet);
				sb.append("Spieler: " + p.getPlayerName() + ": lost -" + totalplayerbet + "\t");
				continue;
			}
		}
		statusLine = "round ended: " + sb.toString();
		resetGame();
		updateAllPlayersInDB();
		status = GameStatus.AUSWERTUNG;
		notifyObservers(GameStatus.AUSWERTUNG);
	}
	
	private void resetGame() {
		cardStack.resetStapel();
		resetHandCards();
		resetPlayerBets();
	}

	public String getCards() {
		return player.toString() + "\n" + dealer.toString(0);

	}

	public void stand() {
		if (status == GameStatus.DURING_TURN) {
			statusLine = player.getPlayerName() + "  STAND";
			notifyObservers();
			round();
		}
	}

	public void playerhit() {
		if (status == GameStatus.DURING_TURN) {
			player.actionhit();
			int handvalue = player.getHandValue()[0];
			if (handvalue > 21) {
				statusLine = player.getPlayerName() + "  BUSTED!";
				notifyObservers(status);
				round();
			} else {
				statusLine = player.getPlayerName() + "  HIT";
				notifyObservers(status);
			}
		}
	}

	public void doublebet() {
		// not implemented
	}

	public void addNewPlayer(String name) {
		if (playerList.size() >= 3) {
			statusLine = "Reached max. amount of players!";
			notifyObservers();
			return;
		} else if (isPlayerInPlayerList(name)) {
			statusLine = "The Player with the name " + name + " is already in the game!";
			notifyObservers();
			return;
		} else {
			Player existingPlayer = getPlayerFromDB(name);
			if (existingPlayer == null) {
				existingPlayer = new Player(name);
				savePlayerToDB(existingPlayer);
			}
			playerList.add(existingPlayer);
			notifyObservers(GameStatus.NEW_PLAYER);
		}
	}

	public boolean deletePlayer(Player player) {
		betPlayerList.remove(player);
		playerListPlaying.remove(player);
		return playerList.remove(player);
	}

	public void removePlayer(String playername) {
		for (Player p : playerList) {
			if (p.getPlayerName().equals(playername)) {
				playerList.remove(p);
				if (betPlayerList != null) {
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

	public void setGameStatus(GameStatus e) {
		status = e;

	}

	private void resetHandCards() {
		dealer.resetCardsInHand();
		for (Player p : playerList) {
			p.resetCardsInHand();
		}
	}

	public int getTotalPlayerBet() {
		if (player != null) {
			return player.getplayerbet();
		} else {
			return 0;
		}
	}

	private boolean isPlayerInPlayerList(String playerName) {
		for(Player player : playerList) {
			if (player.getPlayerName().equals(playerName)) {
				return true;
			}
		}
		return false;
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
		if (p.getHandValue()[1] <= 21) {
			return p.getHandValue()[1];
		} else {
			return p.getHandValue()[0];
		}
	}

	private void resetPlayerBets() {
		for (Player p : playerList) {
			p.setplayerbet(0);
		}
	}

	public Dealer getDealer() {
		return dealer;
	}

	private void savePlayerToDB(Player player) {
		playersDAO.savePlayer(player);
	}
	
	public Player getPlayerFromDB(String name) {
		return playersDAO.getPlayer(name);
	}

	public List<Player> getAllPlayersFromDB() {
		return playersDAO.getAllPlayers();
	}

	public void deletePlayerFromDB(Player player) {
		playersDAO.deletePlayer(player);
	}

	public void deleteAllPlayersFromDB() {
		for (Player player : getAllPlayersFromDB()) {
			deletePlayerFromDB(player);
		}
	}

	private void updateAllPlayersInDB() {
		// player gets added several times, no object update
		for (Player player : playerList) {
			savePlayerToDB(player);
		}
	}
	
	public String getJson() {
		
// TODO: auﬂerhalb Aufrufen nicht im getJson
//		String[] stringCommand = command.split(":");
//		String com = stringCommand[0];
//		String playername = stringCommand[1];

//		if (!com.equals("null")) {
//			if (com.equals("h") || com.equals("s") || com.equals("+") || com.equals("-") || com.equals("sb")) {
//				if (status != GameStatus.NOT_STARTED && playername.equals(player.getPlayerName())) {
//					Blackjack.getInstance().getTUI().userinputselection(com);
//					
//				}
//			} else {
//				Blackjack.getInstance().getTUI().userinputselection(com);
//			}
//		}

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

		for (Player p : getPlayerList()) {
			playerName.add(p.getPlayerName());
			cardValue.add(p.getHandValue()[0] + "/" + p.getHandValue()[1]);
			budget.add(Integer.toString(p.getBudget()));
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