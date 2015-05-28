package de.htwg.blackjack.view.tui;

import de.htwg.blackjack.controller.Controller;

public class TextUI {
	Controller controller;
	public TextUI(Controller controller) {
        this.controller = controller;
    }
	public boolean userinputselection(String next) {
		boolean go = true;
		switch (next) {
		case"q":
			go = false;
			break;
		case"s":
			//Controller
			break;
		case"b":
			//Controller

		}

		return go;
	}
}
