package de.htwg.blackjack.util.observer;

import java.util.ArrayList;
import java.util.List;

import de.htwg.blackjack.entities.impl.GameStatus;

public class Observable implements IObservable {

    private List<IObserver> subscribers = new ArrayList<IObserver>(2);

    @Override
    public void addObserver(IObserver s) {
        subscribers.add(s);
    }

    @Override
    public void removeObserver(IObserver s) {
        subscribers.remove(s);
    }

    @Override
    public void removeAllObservers() {
        subscribers.clear();
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(GameStatus e) {
        for (IObserver current: subscribers) {
            current.update(e);
        }
    }
}
