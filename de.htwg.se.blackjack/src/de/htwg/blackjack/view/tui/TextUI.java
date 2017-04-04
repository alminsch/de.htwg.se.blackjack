package de.htwg.blackjack.view.tui;

import java.util.Scanner;

import org.apache.log4j.Logger;


import de.htwg.blackjack.util.observer.IObserver;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.GameStatus;

import com.google.inject.Inject;

public class TextUI implements IObserver{

    private IController controller;
    private static final String NEWLINE = System.getProperty("line.separator");
    private Logger logger = Logger.getLogger("de.htwg.blackjack.view.tui");
    private static Scanner scanner;

    @Inject
    public TextUI(IController controller) {
        this.controller = controller;
        controller.addObserver(this);
    }

    public void update(GameStatus e) {
        printTUI();
    }

    public boolean userinputselection(String next) {
        boolean go = true;
        switch (next) {
        case"q":
            go = false;
            break;
        case"n":
            controller.startnewround();
            break;
        case"np":
            scanner = new Scanner(System.in);
             logger.info("Bitte Spielername angeben");
            controller.addnewPlayer(scanner.next());
            break;
        case"h":
            controller.playerhit();
            break;
        case"s":
            controller.stand();
            break;
        case"+":
            controller.increasebet();
            break;
        case"-":
            controller.decreasebet();
            break;
        case"sb":
            controller.setbetforround();
            break;
        }

        return go;
    }

    public void printTUI() {
        logger.info(NEWLINE + controller.getStatus());
        if(controller.getGameStatus() == GameStatus.DURING_TURN) {
             logger.info(NEWLINE + controller.getCards());
        }
        logger.info(NEWLINE
                + "Possible commands: q-quit, n-new game, np-newplayer, h-hit, s-stand, - decreasebet, + increasebet, sb-setbet");
    }
    
    public String getTUI() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(NEWLINE + controller.getStatus());
    	if(controller.getGameStatus() == GameStatus.DURING_TURN) {
    		sb.append(NEWLINE + controller.getCards());
    	}
    	sb.append(NEWLINE + "Possible commands: q-quit, n-new game, np-newplayer, h-hit, s-stand, - decreasebet, + increasebet, sb-setbet");
    	return (sb.toString());
    }
    
}
