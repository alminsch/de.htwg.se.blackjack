package de.htwg.blackjack.controller;

import java.util.List;

import de.htwg.blackjack.entities.AbstractParticipant;
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

    String getStatus();

    void addnewPlayer(String next);

    void playerhit();

    void stand();

    void doublebet();

    void createnewgame();
    
    void resetgame();

    void playerbets();

    void allgettwocards();

    void spielzug();

    void auswertung();

    boolean deletePlayer(Player player);
    
    void removePlayer(String playername);

    String getCards();

    int getTotalPlayerBet();

    int getDisplayBet();

    List<Card> getDealerCards();

    List<Player> getPlayingPlayerList();
    
    List<Player> getPlayerList();

    int getCardValue(AbstractParticipant p);

    Dealer getDealer();

    GameStatus getGameStatus();
    
    String json();
    
    String getJson(String command);

}