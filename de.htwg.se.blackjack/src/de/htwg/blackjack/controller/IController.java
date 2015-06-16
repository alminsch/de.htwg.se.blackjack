package de.htwg.blackjack.controller;

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

}
