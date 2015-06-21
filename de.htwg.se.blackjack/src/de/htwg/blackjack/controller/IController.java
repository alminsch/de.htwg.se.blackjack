package de.htwg.blackjack.controller;

import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.util.observer.IObservable;

public interface IController extends IObservable {

	boolean increasebet();

	boolean decreasebet();

	void setbetforround();

	void startnewround();

	String getStatus();

	void addnewPlayer(String next);

	void playerhit();

	void stand();

	void insurance();

	void doublebet();

	void createnewgame();

	void playerbets();

	void allgettwocards();

	void spielzug();

	void auswertung();

	boolean deletePlayer(Player player);

	String getCards();

}
