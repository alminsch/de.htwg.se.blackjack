package de.htwg.blackjack.util.observer;

import de.htwg.blackjack.entities.impl.GameStatus;

public interface IObservable {

    void addObserver(IObserver s);

    void removeObserver(IObserver s);

    void removeAllObservers();

    void notifyObservers();

    void notifyObservers(GameStatus e);
}
