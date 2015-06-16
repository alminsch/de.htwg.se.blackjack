package de.htwg.blackjack.view.tui;

import java.util.Scanner;
import java.util.logging.Logger;

import de.htwg.blackjack.util.observer.Event;
import de.htwg.blackjack.util.observer.IObserver;
import de.htwg.blackjack.controller.IController;
//import com.google.inject.Inject;

public class TextUI implements IObserver{

	private IController controller;
	private static final String NEWLINE = System.getProperty("line.separator");
    private Logger logger = Logger.getLogger("de.htwg.blackjack.view.tui");
    private static Scanner scanner;

    //@Inject
    public TextUI(IController controller) {
        this.controller = controller;
        controller.addObserver(this);
    }


	public void update(Event e) {
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
			System.out.println("Bitte Spielername angeben");
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
        logger.info(NEWLINE
                + "Possible commands: q-quit, s-start, n-new game, np-newplayer, h-hit, s-stand, - decreasebet, + increasebet, sb-setbet");
    }
}
