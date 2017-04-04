package de.htwg.blackjack.util.observer;

import de.htwg.blackjack.entities.impl.GameStatus;

public interface IObserver {
    void update(GameStatus e);
}
