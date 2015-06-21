package de.htwg.blackjack.controller.logwrapper;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;


import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.util.observer.Observable;


@Singleton
public class Controller extends Observable implements IController {

	  private Logger logger = Logger
	            .getLogger("de.htwg.blackjack.controller.logwrapper");
	    private IController realController;
	    private long startTime;
	@Inject
	public Controller() {
		realController = new de.htwg.blackjack.controller.impl.Controller();
	}

	private void pre() {
        logger.debug("Controller method " + getMethodName(1) + " was called.");
        startTime = System.nanoTime();
    }

    private void post() {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        logger.debug("Controller method " + getMethodName(1)
                + " was finished in " + duration + " nanoSeconds.");
    }

    private static String getMethodName(final int depth) {
        final StackTraceElement[] stack = Thread.currentThread()
                .getStackTrace();
        return stack[2 + depth].getMethodName();
    }

	public void createnewgame() {
		pre();
        realController.createnewgame();
        post();
	}

	public void startnewround() {
		pre();
        realController.startnewround();
        post();
	}

	public void playerbets() {
		pre();
        realController.playerbets();
        post();
	}

	public void setbetforround() {
		pre();
        realController.setbetforround();
        post();
	}

	public void allgettwocards() {
		pre();
        realController.allgettwocards();
        post();
	}

	public void spielzug() {
		pre();
        realController.spielzug();
        post();
	}

	public void auswertung() { //todo: Methode  aufteilen
		pre();
        realController.auswertung();
        post();
	}

	public void stand() {
		pre();
        realController.stand();
        post();
	}

	public void playerhit() {
		pre();
        realController.playerhit();
        post();
	}

	public void insurance() {
		pre();
        realController.insurance();
        post();
	}

	public void doublebet() {
		pre();
        realController.doublebet();
        post();
	}

	public void addnewPlayer(String s) {
		pre();
        realController.addnewPlayer(s);
        post();
	}

	public boolean deletePlayer(Player player) {
		pre();
        boolean b = realController.deletePlayer(player);
        post();
        return b;
	}

	public String getStatus() {
		pre();
        String s = realController.getStatus();
        post();
		return s;
    }

    public boolean increasebet() {
    	pre();
        boolean b = realController.increasebet();
        post();
        return b;
    }

	public boolean decreasebet() {
		pre();
        boolean b = realController.decreasebet();
        post();
        return b;
	}
}
