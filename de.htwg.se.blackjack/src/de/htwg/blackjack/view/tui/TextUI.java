package de.htwg.blackjack.view.tui;

import java.util.logging.Logger;

import de.htwg.blackjack.controller.Controller;

public class TextUI {
	private Controller controller;
	private static final String NEWLINE = System.getProperty("line.separator");
    private Logger logger = Logger.getLogger("de.htwg.blackjack.view.tui");


	public TextUI(Controller controller) {
        this.controller = controller;
    }
	public boolean userinputselection(String next) {
		boolean go = true;
		switch (next) {
		case"q":
			go = false;
			break;
		case"n":
			//Controller
			break;
		case"next":
			break;
		case"+":
			controller.increasebet();
			break;
		case"-":
			controller.decreasebet();
			break;
		case"s":
			controller.startnewround();
			break;

		}

		return go;
	}

	public void printTUI() {
        logger.info(NEWLINE + controller.getStatus());
        logger.info(NEWLINE
                + "Possible commands: q-quit, s-start, n-new, p-newplayer, next, - bet,+ bet");
    }
}
