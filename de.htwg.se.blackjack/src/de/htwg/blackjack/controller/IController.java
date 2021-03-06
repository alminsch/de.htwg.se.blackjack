package de.htwg.blackjack.controller;

import java.util.List;

import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.Dealer;
import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.util.observer.IObservable;

public interface IController extends IObservable {

	boolean increasebet();

	boolean decreasebet();

	void setbetforround();

	void startnewround();

	String getStatusLine();

	void addNewPlayer(String next);

	void playerhit();

	void stand();

	void doublebet();

	void createnewgame();

	void playerbets();

	void allgettwocards();

	void round();

	void evaluateRound();

	boolean deletePlayer(Player player);

	void removePlayer(String playername);

	String getCards();

	int getTotalPlayerBet();

	int getDisplayBet();

	List<Card> getDealerCards();

	List<Player> getPlayingPlayerList();

	List<Player> getPlayerList();

	Dealer getDealer();

	GameStatus getGameStatus();

	List<Player> getAllPlayersFromDB();

	String getJson();

	void deleteAllPlayersFromDB();

	Player getPlayerFromDB(String name);

	void deletePlayerFromDB(Player player);

	void endOfRound();
}